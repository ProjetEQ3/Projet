import React from "react";
import CvFile from '../../model/CvFile'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowUpRightFromSquare, faCheck, faX} from '@fortawesome/free-solid-svg-icons';
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";

const ShortCv = ({cv, index, updateCvList}) => {
    const [isDecline, setIsDecline] = React.useState(false);
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
        if (cv.fileData) {
            const pdfBlob = new Blob([CvFile.readBytes(cv.fileData)], {type: 'application/pdf'});
            const pdfUrl = URL.createObjectURL(pdfBlob);
            const pdfIframe = document.createElement('iframe');
            pdfIframe.setAttribute('style', 'position:absolute;right:0; top:0; bottom:0; height:100%; z-index:1000');
            pdfIframe.setAttribute('src', pdfUrl);
            pdfIframe.setAttribute('id', 'pdfIframe');
            pdfIframe.setAttribute('class', 'col-6');
            document.body.appendChild(pdfIframe);
            const pdfCloseButton = document.createElement('button');
            pdfCloseButton.setAttribute('style', 'position:absolute;right:3em; top:3em; bottom:0; height: 3em; z-index:1001;');
            pdfCloseButton.setAttribute('class', 'btn btn-danger')
            pdfCloseButton.setAttribute('id', 'pdfCloseButton');
            pdfCloseButton.innerHTML = 'Close';
            const closePdfIframe = () => {
                document.body.removeChild(pdfIframe);
                document.body.removeChild(pdfCloseButton);
            }
            pdfCloseButton.addEventListener('click', closePdfIframe);
            document.body.appendChild(pdfCloseButton);
        }
    }

    return (
        <div className="row m-2">
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-8">
                        <h4 className="text-dark fw-light m-0 p-3">Nom du fichier : <a onClick={OpenCv} className="link-dark">{cv.fileName}</a></h4>
                    </div>
                    <div className="col-4 my-auto d-flex justify-content-between">
                        <div className="my-auto mx-auto">
                            {
                                cv.cvState === 'ACCEPTED' ?
                                    <div className="border rounded px-2 border-success text-success">
                                        Accepté
                                    </div>
                                    :
                                    cv.cvState === 'REFUSED' ?
                                    <div className="border rounded px-2 border-danger text-danger">
                                        Refusé
                                    </div>
                                    :
                                    cv.cvState === 'SUBMITTED' ?
                                    <div className="border rounded px-2 border-secondary text-secondary">
                                        Attente d'approbation
                                    </div>
                                    :
                                    ""
                            }
                        </div>
                        <div className="btn btn-outline-ose" data-bs-toggle="modal" data-bs-target={"#fullViewModal" + index}>Détails</div>
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
                                                <button type="button" onClick={handleAccept} className="btn btn-success mx-2" data-bs-dismiss="modal">Accepter</button>
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
    );
}
export default ShortCv;