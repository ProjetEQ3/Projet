import React, {useEffect, useState} from "react";
import CvFile from '../../model/CvFile'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faX} from '@fortawesome/free-solid-svg-icons';
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import State from "./State";
import PDFPreview from "../util/PDF/PDFPreview";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";

const ShortCv = ({cv, index, updateCvList, getAllCvs }) => {
    const {t} = useTranslation();
    const [isDecline, setIsDecline] = useState(false);
    const [isDisplay, setIsDisplay] = useState(false);
    const [formData, setFormData] = useState({
        refusalReason: '',
    });
    const [isHovered, setHovered] = useState(false);

    const { darkMode } = useDarkMode();

    const handleMouseEnter = () => {
        if (isDisplay) return;
        setHovered(true);
    };

    const handleMouseLeave = () => {
        if (isDisplay) return;
        setHovered(false);
    };

    const handleAccept = (e) => {
        e.preventDefault();
        updateCv(cv, 'ACCEPTED',null)
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

        updateCv(cv, 'REFUSED', formData.refusalReason)

        setIsDecline(false)
    }

    const cancelDecline = (e) => {
        e.preventDefault();
        setIsDecline(false);
    }


    const updateCv = (cv,cvState, reason) => {
        cv.cvState = cvState
        axiosInstance
            .put(`/manager/cv/update/${cv.id}?newCvState=${cvState}&reason=${reason}`,{
                headers: { 'Content-Type': 'text/plain' }
            })
            .then((response) => {
                toast.success(t('updatedCV') + t(cvState))
                updateCvList(cv)
                getAllCvs();
            })
            .catch((error) => {
                toast.error(t('errorUpdateCV') + t(error.response?.data.message))
            })
    }

    const OpenCv = () => {
        setIsDisplay(!isDisplay)
    }

    return (
        <>
            <div className={`row clickable ${!isHovered? 'm-2':'m-1 shadow'}`} onClick={OpenCv} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave} >
                <div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded`}>
                    <div className="row">
                        <div className="col-6">
                            <h3 data-testid="title" className={`${darkMode ? 'text-light' : 'text-dark'} ${cv.cvState === 'SUBMITTED' ? '' : 'fw-light'} m-0 p-3`}>{cv.fileName}</h3>
                        </div>
                        <div className="col-6 my-auto d-block d-md-flex justify-content-end justify-content-md-between">
                            <div className="my-auto col-6 text-center d-block">
                                <State state={cv.cvState}/>
                            </div>
                            <button
                                data-testid="modalButton"
                                className={`btn btn-outline-ose my-auto ${cv.cvState !== 'SUBMITTED' ? 'd-none' : ''}`}
                                data-bs-toggle={cv.cvState === 'SUBMITTED' ? 'modal' : ''}
                                data-bs-target={`#fullViewModal${index}`}
                                onClick={cv.cvState !== 'SUBMITTED' ? (e) => e.preventDefault() : undefined}
                            >
                                {t('probation')}
                            </button>
                            <div id={"fullViewModal" + index} className="modal modal-lg" aria-hidden="true">
                                <div className="modal-dialog">
                                    <div className={`modal-content ${darkMode ? 'bg-dark' : ''}`}>
                                        <div className="modal-header">
                                            <h3 data-testid="headerTitle" className={`modal-title ${darkMode ? 'text-light' : 'text-dark'}`}>{t('cvAuthorisation')}</h3>
                                            <FontAwesomeIcon data-testid="headerX" icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2" onClick={handleClose}/>
                                        </div>
                                        <div className="modal-body">
                                            <h3 data-testid="bodyTitle" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3 fw-semibold`}>{cv.fileName}</h3>
                                        </div>
                                        <div className="modal-footer">
                                            {isDecline ? (
                                                    <form id="refusalForm" className="form col-10 mx-auto">
                                                        <label data-testid="footerLabel">{t('confirmRefusal')}</label>
                                                        <input data-testid="refusalInput" id="refusalReason" name="refusalReason" className={`form-control form-text ${darkMode ? "dark-input" : ""}`} type="text" onChange={validateReason} placeholder={t('refusalReason')} required/>
                                                        <button data-testid="confirmButton" type="submit" onClick={confirmDecline} className="btn btn-primary m-2" data-bs-dismiss="modal">{t('confirm')}</button>
                                                        <button data-testid="cancelButton" type="button" onClick={cancelDecline} className="btn btn-outline-secondary ms-2" data-bs-dismiss="modal">{t('cancel')}</button>
                                                    </form>) :
                                                (<div>
                                                    <button data-testid="acceptButton" type="button" onClick={handleAccept} className="btn btn-success mx-2" data-bs-dismiss="modal">{t('accept')}</button>
                                                    <button data-testid="refuseButton" type="button" onClick={handleDecline} className="btn btn-danger" >{t('refuse')}</button>
                                                </div>)}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {isDisplay ? (
                <PDFPreview file={CvFile.readBytes(cv.fileData)} setIsDisplay={setIsDisplay} contractComplete={true}/>
            ) : null}
        </>
    );
}
export default ShortCv;