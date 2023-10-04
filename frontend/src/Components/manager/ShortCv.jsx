import React from "react";
import CvFile from '../../model/CvFile'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowUpRightFromSquare, faCheck, faX} from '@fortawesome/free-solid-svg-icons';
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import State from "../util/State";
import PDFPreview from "../util/PDFPreview";

const ShortCv = ({cv, index, updateCvList}) => {
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
                toast.success("CV est bien mis à jour avec l'état: " + cvState)
                updateCvList(cv)
            })
            .catch((error) => {
                toast.error("Erreur lors de la mis à jour du CV: " + error.message)
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
                        <div className="col-8">
                            <h4 className="text-dark fw-light m-0 p-3"><a onClick={OpenCv} className="link-dark">{cv.fileName}</a></h4>
                        </div>
                        <div className="col-4 my-auto d-flex justify-content-end justify-content-lg-between">
                            <div className="my-auto mx-auto d-none d-lg-block">
                                <State state={cv.cvState}/>
                            </div>
                            <div className="btn btn-outline-ose my-auto" data-bs-toggle="modal" data-bs-target={"#fullViewModal" + index}>Approbation</div>
                            <div id={"fullViewModal" + index} className="modal modal-lg" aria-hidden="true">
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h3 className="modal-title">Autorisation du CV</h3>
                                            <FontAwesomeIcon icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2" onClick={handleClose}/>
                                        </div>
                                        <div className="modal-body">
                                            <h3 className="text-dark fw-light mb-3">{cv.fileName}</h3>
                                        </div>
                                        <div className="modal-footer">
                                            {isDecline ? (
                                                    <form id="refusalForm" className="form col-10 mx-auto">
                                                        <p>Êtes-vous sûr de vouloir refuser ce CV ?</p>
                                                        <input id="refusalReason" name="refusalReason" className="form-control form-text" type="text" onChange={validateReason} placeholder="Raison du refus" required/>
                                                        <input value="Confirmer" type="submit" onClick={confirmDecline} className="btn btn-primary m-2" data-bs-dismiss="modal"/>
                                                        <button type="button" onClick={cancelDecline} className="btn btn-outline-secondary ms-2" data-bs-dismiss="modal">Annuler</button>
                                                    </form>) :
                                                (<div>
                                                    <button type="button" onClick={handleAccept} className="btn btn-success mx-2" data-bs-dismiss="modal">Approuver</button>
                                                    <button type="button" onClick={handleDecline} className="btn btn-danger">Refuser</button>
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