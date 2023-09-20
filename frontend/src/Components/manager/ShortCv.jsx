import React from "react";
import JsPDF from 'jspdf';
import CvFile from '../../model/CvFile'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowUpRightFromSquare, faCheck, faX} from '@fortawesome/free-solid-svg-icons';
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";

const ShortCv = ({cv, index}) => {
    const [isDecline, setIsDecline] = React.useState(false);
    const [formData, setFormData] = React.useState({
        refusalReason: '',
    });

    const handleAccept = (e) => {
        e.preventDefault();

        cv.cvState = 'ACCEPTED';
        updateCv(cv, cv.cvState, formData.refusalReason)
    }

    const handleDecline = (e) => {
        e.preventDefault();
        setIsDecline(true);
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

        cv.cvState = 'REFUSED';

        updateCv(cv, cv.cvState, formData.refusalReason)

        setIsDecline(false)
    }

    const cancelDecline = (e) => {
        e.preventDefault();
        setIsDecline(false);
    }

    const updateCv = (cv, state, reason) => {
        axiosInstance
            .put(`/manager/cv/update/${cv.id}`, {
                params: {
                    newCvState: state,
                    reason: reason
                }
            }, )
            .then((response) => {
                    toast.success("CV est mis à jour avec succès, state: " + state)
                })
                    .catch((error) => {
                        toast.error("Erreur lors de la mis à jour du CV: " + error.message)
                    })
    }

    const OpenCv = () => {
        if (cv.fileData) {
            const pdf = new JsPDF();
            pdf.text(CvFile.readBytes(cv.fileData), 10, 10);
            const pdfBlob = pdf.output('blob');
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
                        <div className="mx-auto">
                        <div className={`border rounded px-2 ${cv.cvState === 'ACCEPTED' ? 'border-success text-success':
                            cv.cvState === 'SUBMITTED' ? 'border-secondary text-secondary': 'border-danger text-danger'}`}>{cv.cvState}</div>
                        </div>
                        <FontAwesomeIcon icon={faArrowUpRightFromSquare} className="me-2 fa-lg arrow-btn" data-bs-toggle="modal" data-bs-target={"#fullViewModal" + index}/>
                        <div id={"fullViewModal" + index} className="modal modal-lg" aria-hidden="true">
                            <div className="modal-dialog">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h3 className="modal-title">Autorisation du CV</h3>
                                    </div>
                                    <div className="modal-body">
                                        <h3 className="text-dark fw-light mb-3">{cv.fileName}</h3>
                                    </div>
                                    <div className="modal-footer">
                                        {isDecline ? (
                                                <form id="refusalForm" className="form col-10 mx-auto">
                                                    <p>Êtes-vous sûr de vouloir refuser cette offre?</p>
                                                    <input id="refusalReason" name="refusalReason" className="form-control form-text" type="text" onChange={validateReason} placeholder="Raison du refus" required/>
                                                    <input value="Confirmer" type="submit" onClick={confirmDecline} className="btn btn-primary m-2" data-bs-dismiss="modal"/>
                                                    <button type="button" onClick={cancelDecline} className="btn btn-outline-secondary ms-2" data-bs-dismiss="modal">Annuler</button>
                                                </form>) :
                                            (<div>
                                                <button type="button" onClick={handleAccept} className="btn btn-success mx-2" data-bs-dismiss="modal"><FontAwesomeIcon icon={faCheck}/></button>
                                                <button type="button" onClick={handleDecline} className="btn btn-danger"><FontAwesomeIcon icon={faX}/></button>
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