import React from "react";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";

const ShortStudentInfo = ({ student }) => {
    const {t} = useTranslation();

    const handleAccept = (e) => {
        e.preventDefault();

    }

    const handleDecline = (e) => {
        e.preventDefault();

    }

    return (
        <div className="m-2 p-2 bg-white border rounded border-ose d-flex">
            <div className="col-12 col-lg-6">
                <h3 className="text-dark fw-light">{student.firstName + " " + student.lastName + " - " + student.email}</h3>
            </div>
            <div className="col-12 col-lg-6">
                <button type="button" onClick={handleAccept} className="btn btn-success mx-2">{t('accept')}</button>
                <button type="button" onClick={handleDecline} className="btn btn-danger">{t('refuse')}</button>
            </div>
        </div>
    )
}
export default ShortStudentInfo;