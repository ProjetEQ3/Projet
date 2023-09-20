import {useState} from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faX } from '@fortawesome/free-solid-svg-icons';
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
const FullJobOffer = ({ jobOffer, updateJobOfferList }) => {
    const [isDecline, setIsDecline] = useState(false);
    const [formData, setFormData] = useState({
        refusalReason: '',
    });
    const handleAccept = (e) => {
        e.preventDefault();
        axiosInstance.put(`/manager/jobOffer/accept/${jobOffer.id}`)
            .then((res) => {
                toast.success("Offer accepted")
                updateJobOfferList(jobOffer);
            })
            .catch((error) => {
                toast.error("Failed to accept offer: " + error.message)
            })
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
            toast.error("Please fill in the refusal reason")
            return;
        }

        axiosInstance.put(`/manager/jobOffer/refuse/${jobOffer.id}`, formData.refusalReason)
            .then((res) => {
                toast.success("Offer declined")
                updateJobOfferList(jobOffer);
            })
            .catch((error) => {
                toast.error("Failed to decline offer: " + error.message)
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
                    <h3 className="modal-title">Autorisation de l'offre</h3>
                </div>
                <div className="modal-body">
                    <h3 className="text-dark fw-light mb-3">{jobOffer.title}</h3>
                    <p className="text-dark fw-light mb-3">{jobOffer.department}</p>
                    <p className="text-dark fw-light mb-3">{jobOffer.location}</p>
                    <p className="text-dark fw-light mb-3">Date de début: {jobOffer.startDate}</p>
                    <p className="text-dark fw-light mb-3">Durée: {jobOffer.duration}</p>
                    <p className="text-dark fw-light mb-3">Date d'expiration: {jobOffer.expireDate}</p>
                    <p className="text-dark fw-light mb-3">{jobOffer.salary}$/h</p>
                    <p className="text-dark fw-light mb-3">{jobOffer.hourPerWeek}h/semaine</p>
                    <p className="text-dark fw-light mb-3">{jobOffer.description}</p>
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
    );
}

export default FullJobOffer;