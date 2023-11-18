import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash, faX} from '@fortawesome/free-solid-svg-icons';

import {useState} from "react";
import State from "../util/State";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";

const ShortJobOffer = ({ jobOffer, deleteOffer, isBold}) => {
    const [t, i18n] = useTranslation();
    const [isHovered, setIsHovered] = useState(false);

    const { darkMode } = useDarkMode();

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    return (
        <div className={`row clickable ${!isHovered? 'm-2':'m-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded`}>
                <div className="row">
                    <div className="col-10 col-sm-6">
                        <h5 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light pt-1 ${isBold ? 'fw-bold' : ''}`} data-testid="job-title">{jobOffer.title}</h5>
                        <p className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`} data-testid="job-department">{t(jobOffer.department)}</p>
                        {
                            jobOffer.applications?.length !== null && jobOffer.applications?.length > 0 &&
                            <p className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('nbOfStudentPostulations')} <strong>{jobOffer.applications?.length}</strong></p>
                        }
                        <p className={`${darkMode ? 'text-light' : 'text-dark'} fw-light float-sm-end`}>{t('nbOfCandidates')} <span><strong>{jobOffer.nbOfCandidates}</strong></span></p>
                    </div>
                    <div className="col-4 col-sm-4 my-auto text-center d-none d-sm-block">
                        <State state={jobOffer.jobOfferState}/>
                    </div>
                    <div className="col-2 col-sm-2 my-auto text-end">
                        <FontAwesomeIcon
                            icon={faTrash}
                            className="me-2 fa-lg icon-btn danger-hover"
                            data-bs-toggle="modal"
                            data-bs-target={`#delete${jobOffer.id}`}
                            data-testid="trash-icon"
                        />
                    </div>
                    <div id={`delete${jobOffer.id}`} className="modal">
                        <div className="modal-dialog">
                            <div className={`modal-content ${darkMode ? 'bg-dark' : ''}`}>
                                <div className="modal-header">
                                    <h3 className="modal-title">{t('delete')}</h3>
                                    <FontAwesomeIcon icon={faX} className="fa-lg danger-hover pe-2" data-bs-dismiss="modal"/>
                                </div>
                                <div className="modal-body">
                                    <p className={`${darkMode ? 'text-light' : 'text-dark'} fw-light pt-1`}>{t('confirmationOfferDelete')}</p>
                                    <h4 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.title}</h4>
                                    <p className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t(jobOffer.department)}</p>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">{t('cancel')}</button>
                                    <button
                                        type="button"
                                        className="btn btn-danger"
                                        data-bs-dismiss="modal"
                                        onClick={deleteOffer}
                                        aria-label="trash"
                                        data-testid="delete-modal-button"
                                    >
                                        {t('delete')}
                                    </button>
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
