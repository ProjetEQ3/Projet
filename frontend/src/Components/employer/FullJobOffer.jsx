import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons';
const FullJobOffer = ({ jobOffer }) => {

    return (
        <div className="row my-2">
            <div className="col-12 bg-white rounded">
                <div className="row justify-content-between">
                    <div className="col-6 col-md-8">
                        <h3 className="text-dark fw-light pt-1">{jobOffer.title}</h3>
                    </div>
                    <div className="col-6 col-md-4 my-auto text-center pt-2">
                        {
                            jobOffer.jobOfferState === 'OPEN' ?
                                <div className="border rounded px-2 border-success text-success">
                                    Applicable
                                </div>
                                :
                                jobOffer.jobOfferState === 'SUBMITTED' ?
                                    <div className="border rounded px-2 border-secondary text-secondary">
                                        Attente d'approbation
                                    </div>
                                    :
                                    jobOffer.jobOfferState === 'REFUSED' ?
                                        <div className="border rounded px-2 border-danger text-danger">
                                            Refusé
                                        </div>
                                        :
                                        jobOffer.jobOfferState === 'TAKEN' ?
                                            <div className="border rounded px-2 border-primary text-primary">
                                                Pris
                                            </div>
                                            :

                                            jobOffer.jobOfferState === 'PENDING' ?
                                                <div className="border rounded px-2 border-warning text-warning">
                                                    En attente
                                                </div>
                                                :
                                                jobOffer.jobOfferState === 'EXPIRED' ?
                                                    <div className="border rounded px-2 border-danger text-danger">
                                                        Expiré
                                                    </div>
                                                    :
                                                    ""
                        }
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <h5 className="text-dark fw-light mb-3">{jobOffer.department}</h5>
                        <h5 className="text-dark fw-light mb-3">{jobOffer.location}</h5>
                        { jobOffer.startDate !== null &&
                            (<h6 className="text-dark fw-light mb-3">Date de début: {jobOffer.startDate.split('T')[0]}</h6>)
                        }
                        <h6 className="text-dark fw-light mb-3">Durée: {jobOffer.duration} semaine(s)</h6>
                        { jobOffer.expirationDate !== null &&
                            (<h6 className="text-dark fw-light mb-3">Date d'expiration: {jobOffer.expirationDate.split('T')[0]}</h6>)
                        }
                        <h6 className="text-dark fw-light mb-3">{jobOffer.salary}$/h</h6>
                        <h6 className="text-dark fw-light mb-3">{jobOffer.hoursPerWeek}h/semaine</h6>
                        <p className="text-dark fw-light mb-3">{jobOffer.description}</p>
                    </div>
                </div>
                <div className="text-end mb-2">
                    <FontAwesomeIcon icon={faPenToSquare} className="icon-btn fa-lg m-2" data-bs-toggle="modal" data-bs-target="#editModal"/>
                </div>
                <div id="editModal" className="modal">
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h3 className="modal-title">Modifier</h3>
                            </div>
                            <div className="modal-body">
                                <h4>{jobOffer.title}</h4>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                <button type="button" className="btn btn-success" data-bs-dismiss="modal">Modifier</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default FullJobOffer;