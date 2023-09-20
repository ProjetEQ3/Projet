import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons';
const FullJobOffer = ({ jobOffer }) => {

    return (
        <div className="row my-2">
            <div className="col-12 bg-white rounded">
                <div className="row justify-content-between">
                    <div className="col-9">
                        <h2 className="text-dark fw-light pt-1">{jobOffer.title}</h2>
                    </div>
                    <div className="col-3 my-auto text-center">
                            <div className={`border rounded px-2 
                            ${jobOffer.jobOfferState === 'OPEN' ? 'border-success text-success':
                                jobOffer.jobOfferState === 'PENDING' ? 'border-warning text-warning':
                                    jobOffer.jobOfferState === 'SUBMITTED' ? 'border-secondary text-secondary' :
                                        jobOffer.jobOfferState === 'TAKEN' ? 'border-primary text-primary': 'border-danger text-danger'} `}>
                                {jobOffer.jobOfferState}
                            </div>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <h5 className="text-dark fw-light mb-3">{jobOffer.department}</h5>
                        <h5 className="text-dark fw-light mb-3">{jobOffer.location}</h5>
                        <h6 className="text-dark fw-light mb-3">Date de début: {jobOffer.startDate}</h6>
                        <h6 className="text-dark fw-light mb-3">Durée: {jobOffer.duration}</h6>
                        <h6 className="text-dark fw-light mb-3">Date d'expiration: {jobOffer.expireDate}</h6>
                        <h6 className="text-dark fw-light mb-3">{jobOffer.salary}$/h</h6>
                        <h6 className="text-dark fw-light mb-3">{jobOffer.hourPerWeek}h/semaine</h6>
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