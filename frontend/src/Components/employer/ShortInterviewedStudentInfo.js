import React, {useState} from "react";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";
import PDFPreview from "../util/PDF/PDFPreview";
import CvFile from "../../model/CvFile";
import {axiosInstance} from "../../App";

const ShortInterviewedStudentInfo = ({ student, jobOfferTitle }) => {
    const {t} = useTranslation();
    const [isDisplay, setIsDisplay] = useState(false);

    const handleHire = (e) => {
        e.preventDefault();
        axiosInstance.put('/employer/offer/accept/' + student.jobApplications[0])
            .then((response) => {
                toast.success(t('hireStudentSuccess'));
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
            })
            .catch((error) => {
                toast.error(t('declineStudentError') + t(error?.response?.data?.message));
            })
    }
    const handlePreview = (e) => {
        e.preventDefault();
        setIsDisplay(!isDisplay);
    }
    return (
        <>
            <div className="m-2 p-2 bg-white border rounded border-ose d-lg-flex" data-testid="short-student-info">
                <div className="col-12 col-lg-6">
                    <h4 className="text-dark fw-light">{student.firstName + " " + student.lastName + " - " + student.email}</h4>
                    <h5 className="text-dark fw-light">{t('jobOffer') + ": " + jobOfferTitle}</h5>
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
                        <PDFPreview file={CvFile.readBytes(student.cvFile.fileData)}/>
                    </div> : null
            }
        </>
    )
}
export default ShortInterviewedStudentInfo;