import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";
import PDFPreview from "../util/PDF/PDFPreview";
import CvFile from "../../model/CvFile";
import {axiosInstance} from "../../App";
import Appointment from "../../model/Appointment";
import {useDarkMode} from "../../context/DarkModeContext";

const ShortInterviewedStudentInfo = ({ student, fetchStudentList }) => {
    const {t} = useTranslation();
    const [isDisplay, setIsDisplay] = useState(false);
    const [appointment, setAppointment] = useState(new Appointment());
    const [jobTitle, setJobTitle] = useState("");

    const { darkMode } = useDarkMode();

    useEffect(() => {
        axiosInstance.get('/employer/offer/appointment/' + student.jobApplications[0])
            .then((response) => {
                let apt = new Appointment();
                apt.init(response.data)
                setAppointment(apt)
            })
            .catch((error) => {
                toast.error(t('errorFetchingAppointments'));
            }
        );

        // Désolé de la monstruosité, mais c'est la seule solution que j'ai trouvé pour garder le titre du jobOffer
        // 🧐🧐🧐🧐🧐
        if (student.jobTitle !== undefined)
            setJobTitle(student.jobTitle)
    }, []);

    const handleHire = (e) => {
        e.preventDefault();
        axiosInstance.put('/employer/offer/accept/' + student.jobApplications[0])
            .then((response) => {
                toast.success(t('hireStudentSuccess'));
                fetchStudentList(true);
            })
            .catch((error) => {
                toast.error(t('hireStudentError') + t(error?.response?.data?.message));
            })
    }
    const handleDecline = (e) => {
        e.preventDefault();
        axiosInstance.put('/employer/offer/refuse/' + student.jobApplications[0])
            .then((response) => {
                toast.success(t('declineStudentSuccess'));
                fetchStudentList(true);
            })
            .catch((error) => {
                toast.error(t('declineStudentError') + t(error?.response?.data?.message));
            })
    }
    const handlePreview = (e) => {
        e.preventDefault();
        setIsDisplay(!isDisplay);
    }

    function dateTimeToShortString(appointment) {
        const date = new Date(appointment);

        const timeZoneOffset = date.getTimezoneOffset();

        date.setMinutes(date.getMinutes() - timeZoneOffset);

        return  t(date.toDateString().split(' ')[0]) + " " +
            t('the') + " " +
            date.toDateString().split(' ')[2] + " " +
            t(date.toDateString().split(' ')[1]) + " " +
            t('at') + " " +
            date.getHours() + "h" + date.getMinutes().toString().padStart(2, '0');
    }

    return (
        <>
            <div className={`m-2 p-2 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded d-lg-flex`} data-testid="short-student-info">
                <div className="col-12 col-lg-6">
                    <h3 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light d-inline-block m-0 p-0`}>{student.firstName + " " + student.lastName + " - " + student.email}</h3>
                    <h4 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light d-inline-block m-0 p-0`}>{t('chosenAppointment')} {dateTimeToShortString(appointment.appointmentDate)}</h4>
                    <h5 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light`}>{t('jobOffer') + ": " + jobTitle}</h5>
                </div>
                <div className="col-12 col-lg-6 text-end">
                    <button type="button" onClick={handlePreview} className="btn btn-outline-ose">{t('preview')}</button>
                    <button type="button" onClick={handleHire} className="btn btn-success mx-2">{t('hire')}</button>
                    <button type="button" onClick={handleDecline} className="btn btn-danger">{t('refuse')}</button>
                </div>
            </div>
            {
                isDisplay ?
                    <div className="col-12" data-testid="pdf-preview-mock-element">
                        <PDFPreview file={CvFile.readBytes(student.cvFile.fileData)} contractComplete={true}/>
                    </div> : null
            }
        </>
    )
}
export default ShortInterviewedStudentInfo;