import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowUpRightFromSquare} from "@fortawesome/free-solid-svg-icons";

import {useState} from "react";
import FullJobOffer from "./FullJobOffer";
const ShortJobOffer = ({ jobOffer, updateJobOfferList, index }) => {
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
                    <div className="col-4 my-auto d-flex justify-content-end justify-content-lg-between">

                        <div className="mx-auto my-auto d-none d-lg-block">
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
                        <div className="btn btn-outline-ose my-auto" data-bs-toggle="modal" data-bs-target={"#fullViewModal" + index}>Détails</div>
                        <div id={"fullViewModal" + index} className="modal modal-lg">
                            <FullJobOffer jobOffer={jobOffer} updateJobOfferList={updateJobOfferList}/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ShortJobOffer;