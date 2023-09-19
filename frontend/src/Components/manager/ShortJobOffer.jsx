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
                    <div className="col-4 my-auto d-flex justify-content-between">
                        <div className="mx-auto">
                            <div className={`border rounded px-2 
                            ${jobOffer.jobOfferState === 'Open' ? 'border-success text-success':
                                jobOffer.jobOfferState === 'Pending' ? 'border-warning text-warning':
                                    jobOffer.jobOfferState === 'Expired' ? 'border-danger text-danger' :
                                        jobOffer.jobOfferState === 'Taken' ? 'border-primary text-primary': 'border-secondary text-secondary'} `}>
                                {jobOffer.jobOfferState}
                            </div>
                        </div>
                        <FontAwesomeIcon icon={faArrowUpRightFromSquare} className="me-2 fa-lg arrow-btn" data-bs-toggle="modal" data-bs-target={"#fullViewModal" + index}/>
                        <div id={"fullViewModal" + index} className="modal modal-lg" aria-hidden="true">
                            <FullJobOffer jobOffer={jobOffer} updateJobOfferList={updateJobOfferList}/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ShortJobOffer;