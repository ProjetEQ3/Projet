import React, {useEffect, useState} from "react";
import CvFile from '../../model/CvFile'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faX} from '@fortawesome/free-solid-svg-icons';
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import State from "../util/State";
import PDFPreview from "../util/PDF/PDFPreview";
import {useTranslation} from "react-i18next";

const ShortCv = ({cv, index, updateCvList, getAllCvs }) => {
    const {t} = useTranslation();
    const [isDecline, setIsDecline] = useState(false);
    const [isDisplay, setIsDisplay] = useState(false);
    const [formData, setFormData] = useState({
        refusalReason: '',
    });

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
            .put(`/manager/cv/update/${cv.id}?newCvState=${cvState}&reason=${reason}`,)
            .then((response) => {
                toast.success(t('updatedCV') + t(cvState))
                updateCvList(cv)
                getAllCvs();
            })
            .catch((error) => {
                console.log(error)
                toast.error(t('errorUpdateCV') + t(error.response?.data.message))
            })
    }

    const OpenCv = () => {
        isDisplay ? setIsDisplay(false) : setIsDisplay(true)
    }

    return (
        <>
            <div className="row m-2">
                <div className="col-12 bg-white rounded">
                    <div className="row">
                        <div className="col-6">
                            <h4 data-testid="title" className="text-dark fw-light m-0 p-3"><a onClick={OpenCv} className="link-dark">{cv.fileName}</a></h4>
                        </div>
                        <div className="col-6 my-auto d-block d-md-flex justify-content-end justify-content-md-between">
                            <div className="my-auto col-6 text-center d-block">
                                <State state={cv.cvState}/>
                            </div>
                            <div
                                data-testid="modalButton"
                                className={`btn btn-outline-ose my-auto ${cv.cvState !== 'SUBMITTED' ? 'disabled' : ''}`}
                                data-bs-toggle={cv.cvState === 'SUBMITTED' ? 'modal' : ''}
                                data-bs-target={`#fullViewModal${index}`}
                                onClick={cv.cvState !== 'SUBMITTED' ? (e) => e.preventDefault() : undefined}
                            >
                                {t('probation')}
                            </div>
                            <div id={"fullViewModal" + index} className="modal modal-lg" aria-hidden="true">
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h3 data-testid="headerTitle" className="modal-title">{t('cvAuthorisation')}</h3>
                                            <FontAwesomeIcon data-testid="headerX" icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2" onClick={handleClose}/>
                                        </div>
                                        <div className="modal-body">
                                            <h3 data-testid="bodyTitle" className="text-dark fw-light mb-3"><a onClick={OpenCv} className="link-dark">{cv.fileName}</a></h3>
                                            {isDisplay ? (
                                                <PDFPreview file={CvFile.readBytes(cv.fileData)} setIsDisplay={setIsDisplay}/>
                                            ) : null}
                                        </div>
                                        <div className="modal-footer">
                                            {isDecline ? (
                                                    <form id="refusalForm" className="form col-10 mx-auto">
                                                        <label data-testid="footerLabel">{t('confirmRefusal')}</label>
                                                        <input data-testid="refusalInput" id="refusalReason" name="refusalReason" className="form-control form-text" type="text" onChange={validateReason} placeholder={t('refusalReason')} required/>
                                                        <button data-testid="confirmButton" type="submit" onClick={confirmDecline} className="btn btn-primary m-2" data-bs-dismiss="modal">{t('confirm')}</button>
                                                        <button data-testid="cancelButton" type="button" onClick={cancelDecline} className="btn btn-outline-secondary ms-2" data-bs-dismiss="modal">{t('cancel')}</button>
                                                    </form>) :
                                                (<div>
                                                    <button data-testid="acceptButton" type="button" onClick={handleAccept} className="btn btn-success mx-2" data-bs-dismiss="modal">{t('accept')}</button>
                                                    <button data-testid="refuseButton" type="button" onClick={handleDecline} className="btn btn-danger">{t('refuse')}</button>
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
                <PDFPreview file={CvFile.readBytes(cv.fileData)} setIsDisplay={setIsDisplay}/>
            ) : null}
        </>

    );
}
export default ShortCv;