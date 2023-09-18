import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faTrash } from '@fortawesome/free-solid-svg-icons';
//TODO: ces imports ne fonctionnent pas ou je sais pas comment les ajouter

import {useState} from "react";
const ShortJobOffer = ({ jobOffer }) => {
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
                        <h4 className="text-dark fw-light pt-1">{jobOffer.title}</h4>
                        <p className="text-dark fw-light mb-3">{jobOffer.department}</p>
                    </div>
                    <div className="col-4 my-auto d-flex justify-content-between">
                        <div className="mx-auto">
                            <div className={`border rounded px-2 
                            ${jobOffer.state === 'Open' ? 'border-success text-success':
                                jobOffer.state === 'Pending' ? 'border-warning text-warning':
                                    jobOffer.state === 'Expired' ? 'border-danger text-danger' :
                                        jobOffer.state === 'Taken' ? 'border-primary text-primary': 'border-secondary text-secondary'} `}>
                                {jobOffer.state}
                            </div>
                        </div>
                        <FontAwesomeIcon icon={faTrash} className="me-2 fa-lg trash-btn" data-bs-toggle="modal" data-bs-target="#deleteModal"/>
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
                                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">Supprimer</button>
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