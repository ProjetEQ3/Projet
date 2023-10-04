import React, {useState} from "react";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";
import PDFPreview from "../util/PDFPreview";
import CvFile from "../../model/CvFile";
import {axiosInstance} from "../../App";

const ShortStudentInfo = ({ student }) => {
    const {t} = useTranslation();
    const [isDisplay, setIsDisplay] = useState(false);

    const handleAccept = (e) => {
        e.preventDefault();
        axiosInstance.put('/employer/offer/accept/{jobApplicationId}', student.jobApplications[0].id)
            .then((response) => {
                toast.success(t('acceptStudentSuccess'));
            })
            .catch((error) => {
                toast.error(t('acceptStudentError') + error.message);
            })
    }

    const handleDecline = (e) => {
        e.preventDefault();
        axiosInstance.put('/employer/offer/decline/{jobApplicationId}', student.jobApplications[0].id)
            .then((response) => {
                toast.success(t('declineStudentSuccess'));
            })
            .catch((error) => {
                toast.error(t('declineStudentError') + error.message);
            })
    }

    const handlePreview = (e) => {
        e.preventDefault();
        setIsDisplay(!isDisplay);
    }


    return (
        <>
            <div onClick={handlePreview} className="m-2 p-2 bg-white border rounded border-ose d-flex">
                <div className="col-12 col-lg-6">
                    <h3 className="text-dark fw-light">{student.firstName + " " + student.lastName + " - " + student.email}</h3>
                </div>
                <div className="col-12 col-lg-6">
                    <button type="button" onClick={handleAccept} className="btn btn-success mx-2">{t('accept')}</button>
                    <button type="button" onClick={handleDecline} className="btn btn-danger">{t('refuse')}</button>
                </div>
            </div>
            {
                isDisplay ?
                    <div className="col-12">
                        <PDFPreview file={CvFile.readBytes(student.cvFile.fileData)}/>
                    </div> : null
            }
        </>
    )
}
export default ShortStudentInfo;