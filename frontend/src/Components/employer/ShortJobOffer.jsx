import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash, faX} from '@fortawesome/free-solid-svg-icons';

import {useState} from "react";
import State from "../util/State";
import {useTranslation} from "react-i18next";
const ShortJobOffer = ({ jobOffer, deleteOffer}) => {
    const [t, i18n] = useTranslation();
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
                        <p className="text-dark fw-light mb-3">{t(jobOffer.department)}</p>
                        <p className="text-dark fw-light float-end">{t('nbOfCandidates')} <span><strong>{jobOffer.nbOfCandidates}</strong></span></p>
                    </div>
                    <div className="col-4 my-auto text-center d-none d-sm-block">
                        <State state={jobOffer.jobOfferState}/>
                    </div>
                    <div className="col-4 col-sm-2 my-auto text-end">
                        <FontAwesomeIcon icon={faTrash} className="me-2 fa-lg icon-btn dark-hover" data-bs-toggle="modal" data-bs-target={`#delete${jobOffer.id}`}/>
                    </div>
                    <div id={`delete${jobOffer.id}`} className="modal">
                        <div className="modal-dialog">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h3 className="modal-title">{t('delete')}</h3>
                                    <FontAwesomeIcon icon={faX} className="fa-lg danger-hover pe-2" data-bs-dismiss="modal"/>
                                </div>
                                <div className="modal-body">
                                    <p className="text-dark fw-light pt-1">{t('confirmationOfferDelete')}</p>
                                    <h4 className="text-dark fw-light mb-3">{jobOffer.title}</h4>
                                    <p className="text-dark fw-light mb-3">{t(jobOffer.department)}</p>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">{t('cancel')}</button>
                                    <button type="button" className="btn btn-danger" data-bs-dismiss="modal" onClick={deleteOffer}>{t('delete')}</button>
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