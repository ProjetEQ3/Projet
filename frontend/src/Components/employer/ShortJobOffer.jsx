import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash, faX} from '@fortawesome/free-solid-svg-icons';

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
                    <div className="col-8 col-sm-6">
                        <h5 className="text-dark fw-light pt-1">{jobOffer.title}</h5>
                        <p className="text-dark fw-light mb-3">{jobOffer.department}</p>
                    </div>
                    <div className="col-4 my-auto text-center d-none d-sm-block">
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
                    <div className="col-4 col-sm-2 my-auto text-end">
                        <FontAwesomeIcon icon={faTrash} className="me-2 fa-lg icon-btn dark-hover" data-bs-toggle="modal" data-bs-target={`#delete${jobOffer.id}`}/>
                    </div>
                    <div id={`delete${jobOffer.id}`} className="modal">
                        <div className="modal-dialog">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h3 className="modal-title">Supprimer</h3>
                                    <FontAwesomeIcon icon={faX} className="fa-lg danger-hover pe-2" data-bs-dismiss="modal"/>
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
    );
}

export default ShortJobOffer;