import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons';
import State from "../util/State";
import {useTranslation} from "react-i18next";
import {t} from "i18next";
const FullJobOffer = ({ jobOffer }) => {

    return (
        <div className="row my-2">
            <div className="col-12 bg-white rounded">
                <div className="row justify-content-between">
                    <div className="col-6 col-md-8">
                        <h3 className="text-dark fw-light pt-1">{jobOffer.title}</h3>
                    </div>
                    <div className="col-6 col-md-4 my-auto text-center pt-2">
                        <State state={jobOffer.jobOfferState}/>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <h5 className="text-dark fw-light mb-3">{jobOffer.department}</h5>
                        <h5 className="text-dark fw-light mb-3">{jobOffer.location}</h5>
                        { jobOffer.startDate !== null &&
                            (<h6 className="text-dark fw-light mb-3">Date de début: {jobOffer.startDate}</h6>)
                        }
                        <h6 className="text-dark fw-light mb-3">{t('duration') + jobOffer.duration + t('week')}</h6>
                        { jobOffer.expirationDate !== null &&
                            (<h6 className="text-dark fw-light mb-3">Date d'expiration: {jobOffer.expirationDate}</h6>)
                        }
                        <h6 className="text-dark fw-light mb-3">{jobOffer.salary}$/h</h6>
                        <h6 className="text-dark fw-light mb-3">{jobOffer.hoursPerWeek}h/{t('week')}</h6>
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
                                <h3 className="modal-title">{t('update')}</h3>
                            </div>
                            <div className="modal-body">
                                <h4>{jobOffer.title}</h4>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">{t('cancel')}</button>
                                <button type="button" className="btn btn-success" data-bs-dismiss="modal">{t('update')}</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default FullJobOffer;