import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faTrash } from '@fortawesome/free-solid-svg-icons';
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
                        <button type="button" className="btn" data-bs-toggle="modal" data-bs-target="#deleteModal">
                            <FontAwesomeIcon icon={faTrash} className="me-2 fa-lg"/>
                        </button>
                        <div id="deleteModal" className="modal">
                            <div className="modal-dialog">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h3 className="modal-title">Êtes vous sûr de vouloir supprimer l'offre de stage ci-dessous:</h3>
                                    </div>
                                    <div className="modal-body">
                                        <h4 className="text-dark fw-light pt-1">{jobOffer.title}</h4>
                                        <p className="text-dark fw-light mb-3">{jobOffer.department}</p>
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