import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faTrash } from '@fortawesome/free-solid-svg-icons';

import {useState} from "react";
const ShortJobOffer = ({ jobOffer, deleteOffer}) => {
    const [isHovered, setIsHovered] = useState(false);

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    return (
        <div className={`row ${!isHovered? 'm-2':'m-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-8">
                        <h5 className="text-dark fw-light pt-1">{jobOffer.title}</h5>
                        <p className="text-dark fw-light mb-3">{jobOffer.department}</p>
                    </div>
                    <div className="col-4 my-auto d-flex justify-content-between">
                        <div className="mx-auto">
                            <div className={`border rounded px-2 
                            ${jobOffer.jobOfferState === 'OPEN' ? 'border-success text-success':
                                jobOffer.jobOfferState === 'PENDING' ? 'border-warning text-warning':
                                    jobOffer.jobOfferState === 'EXPIRED' ? 'border-danger text-danger' :
                                        jobOffer.jobOfferState === 'TAKEN' ? 'border-primary text-primary': 'border-secondary text-secondary'} `}>
                                {jobOffer.jobOfferState}
                            </div>
                        </div>
                        <FontAwesomeIcon icon={faTrash} className="me-2 fa-lg icon-btn" data-bs-toggle="modal" data-bs-target="#deleteModal"/>
                        <div id="deleteModal" className="modal">
                            <div className="modal-dialog">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h3 className="modal-title">Supprimer</h3>
                                    </div>
                                    <div className="modal-body">
                                        <p className="text-dark fw-light pt-1">Êtes-vous sûr de vouloir supprimer l'offre de stage ci-dessous:</p>
                                        <h4 className="text-dark fw-light mb-3">{jobOffer.title}</h4>
                                        <p className="text-dark fw-light mb-3">{jobOffer.department}</p>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal" onClick={deleteOffer}>Supprimer</button>
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

export default ShortJobOffer;