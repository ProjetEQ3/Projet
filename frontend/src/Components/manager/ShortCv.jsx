import React from "react";
import CvFile from '../../model/CvFile'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowUpRightFromSquare, faCheck, faX} from '@fortawesome/free-solid-svg-icons';
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import State from "../util/State";
import PDFPreview from "../util/PDF/PDFPreview";
import {useTranslation} from "react-i18next";

const ShortCv = ({cv, index, updateCvList}) => {
    const {t} = useTranslation();
    const [isDecline, setIsDecline] = React.useState(false);
    const [isDisplay, setIsDisplay] = React.useState(false);
    const [formData, setFormData] = React.useState({
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
                toast.success(t('updatedCV') + cvState)
                updateCvList(cv)
            })
            .catch((error) => {
                toast.error(t('errorUpdateCV') + error.message)
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
                            <h4 className="text-dark fw-light m-0 p-3"><a onClick={OpenCv} className="link-dark">{cv.fileName}</a></h4>
                        </div>
                        <div className="col-6 my-auto d-block d-md-flex justify-content-end justify-content-md-between">
                            <div className="my-auto col-6 text-center d-block">
                                <State state={cv.cvState}/>
                            </div>
                            <div className="btn btn-outline-ose my-auto" data-bs-toggle="modal" data-bs-target={"#fullViewModal" + index}>{t('probation')}</div>
                            <div id={"fullViewModal" + index} className="modal modal-lg" aria-hidden="true">
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h3 className="modal-title">{t('cvAuthorisation')}</h3>
                                            <FontAwesomeIcon icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2" onClick={handleClose}/>
                                        </div>
                                        <div className="modal-body">
                                            <h3 className="text-dark fw-light mb-3"><a onClick={OpenCv} className="link-dark">{cv.fileName}</a></h3>
                                            {isDisplay ? (
                                                <PDFPreview file={CvFile.readBytes(cv.fileData)} setIsDisplay={setIsDisplay}/>
                                            ) : null}
                                        </div>
                                        <div className="modal-footer">
                                            {isDecline ? (
                                                    <form id="refusalForm" className="form col-10 mx-auto">
                                                        <p>{t('confirmRefusal')}</p>
                                                        <input id="refusalReason" name="refusalReason" className="form-control form-text" type="text" onChange={validateReason} placeholder={t('refusalReason')} required/>
                                                        <input value={t('confirm')} type="submit" onClick={confirmDecline} className="btn btn-primary m-2" data-bs-dismiss="modal"/>
                                                        <button type="button" onClick={cancelDecline} className="btn btn-outline-secondary ms-2" data-bs-dismiss="modal">{t('cancel')}</button>
                                                    </form>) :
                                                (<div>
                                                    <button type="button" onClick={handleAccept} className="btn btn-success mx-2" data-bs-dismiss="modal">{t('accept')}</button>
                                                    <button type="button" onClick={handleDecline} className="btn btn-danger">{t('refuse')}</button>
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