import React, {useState} from "react";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";
import PDFPreview from "../util/PDF/PDFPreview";
import CvFile from "../../model/CvFile";
import {axiosInstance} from "../../App";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMinus, faPlus} from "@fortawesome/free-solid-svg-icons";
import {now} from "moment";

const ShortStudentInfo = ({ student, filterStudentList }) => {
    const {t} = useTranslation();
    const [isDisplay, setIsDisplay] = useState(false);
    const [isConvoque, setConvoque] = useState(false);
    const [dates, setDates] = useState([new Date(now())]);

    const todayDate = new Date();
    const minDate = todayDate.toISOString().slice(0, 16);

    const handleConvoke = (e) => {
        e.preventDefault();
        for (let date of dates) {
            if (date < todayDate) {
                toast.error(t('convokeError') + t('convokeErrorMin'));
                return;
            }
        }
        axiosInstance.put('/employer/offer/appointment/' + student.jobApplications[0], dates)
            .then((response) => {
                toast.success(t('convokeSuccess'));
                filterStudentList(student.jobApplications[0]);
            })
            .catch((error) => {
                toast.error(t('convokeError') + t(error.response?.data?.message));
            })
    }
    const handleDecline = (e) => {
        e.preventDefault();
        axiosInstance.put('/employer/offer/refuse/' + student.jobApplications[0])
            .then((response) => {
                toast.success(t('declineStudentSuccess'));
                filterStudentList(student.jobApplications[0]);
            })
            .catch((error) => {
                toast.error(t('declineStudentError') + t(error?.response?.data?.message));
            })

    }
    const modifyDates = (nb) => {
        if (nb > 0) {
            setDates([...dates, new Date(now())]);
        } else {
            const newDates = [...dates];
            newDates.pop();
            setDates(newDates);
        }
    }
    const handleShowConvokeForm = (e) => {
        e.preventDefault();
        setConvoque(!isConvoque);
        setIsDisplay(false);
    }
    const handlePreview = (e) => {
        e.preventDefault();
        setIsDisplay(!isDisplay);
        setConvoque(false);
    }

    const handleDateUpdate = (e, index) => {
        const newDates = [...dates];
        newDates[index] = new Date(e.target.value);
        setDates(newDates);
    }

    return (
        <>
            <div className="m-2 p-2 bg-white border rounded border-ose d-lg-flex" data-testid="short-student-info">
                <div className="col-12 col-lg-6">
                    <h4 className="text-dark fw-light">{student?.firstName || 'N/A'} {student?.lastName || 'N/A'} - {student?.email || 'N/A'}</h4>
                </div>
                <div className="col-12 col-lg-6 text-end">
                    <button type="button" onClick={handlePreview} className="btn btn-outline-ose">{t('preview')}</button>
                    <button type="button" onClick={handleShowConvokeForm} className="btn btn-success mx-2">{t('convoke')}</button>
                    <button type="button" onClick={handleDecline} className="btn btn-danger">{t('refuse')}</button>
                </div>
            </div>
            {
                isDisplay ?
                    <div className="col-12" data-testid="pdf-preview-mock-element">
                        <PDFPreview file={CvFile.readBytes(student.cvFile.fileData)} contractComplete={true}/>
                    </div> : null
            }
            {
                isConvoque ?
                    <div className="col-12 text-center" data-testid="convoke-mock-element">
                        <div className="m-2 p-2 bg-white border rounded border-ose">
                            <div className="col-12 text-center">
                                <h5 className="text-dark fw-light">{t('convokeSentence') + student.firstName + " " + student.lastName}</h5>
                            </div>
                            <div className="col-12 text-center">
                                <h6 className="text-dark fw-light">{t('convokeDates')}</h6>
                                {
                                    dates.map((date, index) => {
                                        return (
                                            <div className="col-10 text-center my-2 mx-auto" key={index}>
                                                <input data-testid={"convokeInput"+index} min={minDate} type="datetime-local" className="form-control" onChange={(e) => handleDateUpdate(e, index)}/>
                                            </div>
                                        )
                                    })
                                }
                            </div>

                            <div className="col-12 mb-2">
                                <button data-testid={'add-convoke-date-button'} type="button" disabled={dates.length >= 5} onClick={() => modifyDates(1)} className="btn btn-sm btn-outline-ose m-1"><FontAwesomeIcon icon={faPlus} /></button>
                                <button data-testid={'sub-convoke-date-button'} type="button" disabled={dates.length <= 1} onClick={() => modifyDates(-1)} className="btn btn-sm btn-outline-ose m-1"><FontAwesomeIcon icon={faMinus} /></button>
                            </div>
                            <div className="col-12 text-center">
                                <button type="button" onClick={handleConvoke} className="btn btn-success">{t('convoke')}</button>
                            </div>
                        </div>
                    </div> : null
            }
        </>
    )
}
export default ShortStudentInfo;