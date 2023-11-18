import React, {useState} from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faX } from '@fortawesome/free-solid-svg-icons';
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import State from "./State";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";

const FullJobOffer = ({ jobOffer, updateJobOfferListAfterApprovalOrRefusal }) => {
    const {t} = useTranslation();
    const [isDecline, setIsDecline] = useState(false);
    const [formData, setFormData] = useState({
        refusalReason: '',
    });

    const { darkMode } = useDarkMode();

    let estimateEndDate = '';
    if(jobOffer.startDate && jobOffer.duration) {
        estimateEndDate = (new Date(new Date(jobOffer.startDate).getTime() + jobOffer.duration * 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0]);
    }
    const handleAccept = (e) => {
        e.preventDefault();
        axiosInstance.put(`/manager/jobOffer/accept/${jobOffer.id}`)
            .then((res) => {
                toast.success(t('acceptedOffer'));
                updateJobOfferListAfterApprovalOrRefusal("OPEN", jobOffer);
            })
            .catch((error) => {
                toast.error(t('pushingError') + t(error.response?.data.message))
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

        axiosInstance.put(`/manager/jobOffer/refuse/${jobOffer.id}`, formData.refusalReason, {
            headers: { 'Content-Type': 'text/plain' }
        })
            .then((res) => {
                toast.success(t('refusedOffer'));
                updateJobOfferListAfterApprovalOrRefusal("REFUSED", jobOffer);
            })
            .catch((error) => {
                toast.error(t('pushingError') + t(error.response?.data.message))
            })
        setIsDecline(false)
    }

    const cancelDecline = (e) => {
        e.preventDefault();
        setIsDecline(false);
    }

    return (
        <div className="modal-dialog">
            <div className={`modal-content ${darkMode ? 'bg-dark' : ''}`}>
                <div className="modal-header">
                    <h3 className={`modal-title ${darkMode ? 'text-light' : 'text-dark'}`}>{t('offerDetails')}</h3>
                    <FontAwesomeIcon data-testid="faX" icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2" onClick={handleClose}/>
                </div>
                <div className="modal-body">
                    <h3 data-testid="fullTitle" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.title}</h3>
                    <div className="d-flex m-2 text-center">
                        <State state={jobOffer.jobOfferState}/>
                    </div>
                    <p data-testid="fullCandidate" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('nbOfCandidates') + jobOffer.nbOfCandidates}</p>
                    <p data-testid="fulldepartment" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t(jobOffer.department)}</p>
                    <p data-testid="fullLocation" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.location}</p>
                    <p data-testid="fullStartDate" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('startDate') + jobOffer.startDate}</p>
                    <p data-testid="fullDuration" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('duration') + jobOffer.duration + t('week')}</p>
                    <p data-testid="fullEstimate" className={`fst-italic fw-light ${darkMode ? 'text-light' : 'text-dark'}`}>{t('estimateEndDate')} {estimateEndDate}</p>
                    <p data-testid="fullExpiration" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('expirationDate') + jobOffer.expirationDate}</p>
                    <p data-testid="fullSalary" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.salary}$/h</p>
                    <p data-testid="fullHoursPerWeek" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.hoursPerWeek}h/{t('week')}</p>
                    <p data-testid="fullDescription" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.description}</p>
                </div>
                <div className="modal-footer">
                    {isDecline ? (
                        <form id="refusalForm" className="form col-10 mx-auto">
                            <label data-testid="footerLabel">{t('confirmRefusal')}</label>
                            <input data-testid="refusalInput" id="refusalReason" name="refusalReason" className={`form-control form-text ${darkMode ? "dark-input" : ""}`} type="text" onChange={validateReason} placeholder={t('refusalReason')} required/>
                            <button data-testid="confirmButton" type="submit" onClick={confirmDecline} className="btn btn-primary m-2" data-bs-dismiss="modal">{t('confirm')}</button>
                            <button data-testid="cancelButton" type="button" onClick={cancelDecline} className="btn btn-outline-secondary ms-2" data-bs-dismiss="modal">{t('cancel')}</button>
                        </form>) :
                        jobOffer.jobOfferState === 'SUBMITTED' ?
                        (
                            <div>
                                <button data-testid="acceptButton" type="button" onClick={handleAccept} className="btn btn-success mx-2" data-bs-dismiss="modal">{t('accept')}</button>
                                <button data-testid="refuseButton" type="button" onClick={handleDecline} className="btn btn-danger" >{t('refuse')}</button>
                            </div>
                        ):
                        (
                            <button data-testid="closeButton" type="button" onClick={handleClose} className="btn btn-outline-danger" data-bs-dismiss="modal">{t('close')}</button>
                        )
                    }
                </div>
             </div>
        </div>
    );
}

export default FullJobOffer;