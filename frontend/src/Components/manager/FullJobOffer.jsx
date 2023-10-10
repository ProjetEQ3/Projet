import React, {useState} from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faX } from '@fortawesome/free-solid-svg-icons';
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import State from "../util/State";
import {useTranslation} from "react-i18next";

const FullJobOffer = ({ jobOffer, updateJobOfferList }) => {
    const {t} = useTranslation();
    const [isDecline, setIsDecline] = useState(false);
    const [formData, setFormData] = useState({
        refusalReason: '',
    });

    let estimateEndDate = '';
    if(jobOffer.startDate && jobOffer.duration) {
        estimateEndDate = (new Date(new Date(jobOffer.startDate).getTime() + jobOffer.duration * 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0]);
    }
    const handleAccept = (e) => {
        e.preventDefault();
        axiosInstance.put(`/manager/jobOffer/accept/${jobOffer.id}`)
            .then((res) => {
                toast.success(t('acceptedOffer'))
                updateJobOfferList(jobOffer);
            })
            .catch((error) => {
                toast.error(t('pushingError') + error.message)
            })
    }

    const handleDecline = (e) => {
        e.preventDefault();
        setIsDecline(true);
    }

    const handleClose = (e) => {
        e.preventDefault();
        setIsDecline(false);
    }

    const validateReason = (e) => {
        e.preventDefault();

        setFormData({...formData, refusalReason: e.target.value});
    }

    const confirmDecline = (e) => {
        e.preventDefault();

        if (document.getElementById('refusalForm').checkValidity() === false) {
            e.stopPropagation();
            document.getElementById('refusalForm').classList.add('was-validated');
            toast.error(t('addRefusalReason'))
            return;
        }

        axiosInstance.put(`/manager/jobOffer/refuse/${jobOffer.id}`, formData.refusalReason)
            .then((res) => {
                toast.success(t('refusedOffer'))
                updateJobOfferList(jobOffer);
            })
            .catch((error) => {
                toast.error(t('pushingError') + error.message)
            })
        setIsDecline(false)
    }

    const cancelDecline = (e) => {
        e.preventDefault();
        setIsDecline(false);
    }

    return (
        <div className="modal-dialog">
            <div className="modal-content">
                <div className="modal-header">
                    <h3 className="modal-title">{t('authorizeOffer')}</h3>
                    <FontAwesomeIcon icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2" onClick={handleClose}/>
                </div>
                <div className="modal-body">
                    <h3 className="text-dark fw-light mb-3">{jobOffer.title}</h3>
                    <div className="d-flex m-2 text-center">
                        <State state={jobOffer.jobOfferState}/>
                    </div>
                    <p className="text-dark fw-light mb-3">{t(jobOffer.department)}</p>
                    <p className="text-dark fw-light mb-3">{jobOffer.location}</p>
                    <p className="text-dark fw-light mb-3">{t('startDate') + jobOffer.startDate}</p>
                    <p className="text-dark fw-light mb-3">{t('duration') + jobOffer.duration + t('week')}</p>
                    <p className="fst-italic fw-light text-dark">{t('estimateEndDate')} {estimateEndDate}</p>
                    <p className="text-dark fw-light mb-3">{t('expirationDate') + jobOffer.expirationDate}</p>
                    <p className="text-dark fw-light mb-3">{jobOffer.salary}$/h</p>
                    <p className="text-dark fw-light mb-3">{jobOffer.hoursPerWeek}h/{t('week')}</p>
                    <p className="text-dark fw-light mb-3">{jobOffer.description}</p>
                </div>
                <div className="modal-footer">
                    {isDecline ? (
                        <form id="refusalForm" className="form col-10 mx-auto">
                            <p>{t('confirmRefusal')}</p>
                            <input id="refusalReason" name="refusalReason" className="form-control form-text" type="text" onChange={validateReason} placeholder={t('refusalReason')} required/>
                            <button type="submit" onClick={confirmDecline} className="btn btn-primary m-2" data-bs-dismiss="modal">{t('confirm')}</button>
                            <button type="button" onClick={cancelDecline} className="btn btn-outline-secondary ms-2" data-bs-dismiss="modal">{t('cancel')}</button>
                        </form>) :
                        (<div>
                            <button type="button" onClick={handleAccept} className="btn btn-success mx-2" data-bs-dismiss="modal">{t('accept')}</button>
                            <button type="button" onClick={handleDecline} className="btn btn-danger">{t('refuse')}</button>
                        </div>)}
                </div>
             </div>
        </div>
    );
}

export default FullJobOffer;