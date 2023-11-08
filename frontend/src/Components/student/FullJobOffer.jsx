import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import React, {useEffect, useState} from "react"
import {useTranslation} from "react-i18next"
import { Modal, Button } from 'react-bootstrap'
import './FullJobOffer.css'
import {faTimes, faX} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const FullJobOffer = ({user, jobOffer, updatedOffer}) => {
    const {t} = useTranslation()
    const [isApplying, setIsApplying] = useState(false)
    const [showModal, setShowModal] = useState(false)
    const [coverLetter, setCoverLetter] = useState("")

    useEffect(() => {
        setCoverLetter("")
        setIsApplying(false)
    }, [jobOffer])

    const applyForJobOffer = (jobOfferID) => {
        if (!user?.id) {
            setShowModal(false)
            return
        }
        axiosInstance
            .post(`/student/applyJobOffer/${user.id}/${jobOfferID}`, {coverLetter: coverLetter})
            .then((response) => {
                updatedOffer(response.data)
                toast.success(t('appliedJobOffer'))
                setIsApplying(false)
                setShowModal(false)
                }
            )
            .catch((error) => {
                toast.error(t('pushingError') + t(error.response?.data.message))
                setShowModal(false)
            })
    }

    const handleApplyClick = () => {
        if (!user?.id) {
            toast.error(t('loginRequired'))
            return
        }
        setShowModal(true)
    }

    const handleCoverLetterChange = (e) => {
        setCoverLetter(e.target.value)
    }

    return (
        <div className="row my-2">
            <div className="col-12 bg-white rounded py-3">
                <div className="row justify-content-between">
                    <div className="col-9">
                        <h2 className="text-dark fw-light pt-1">{jobOffer.title}</h2>
                    </div>
                    <div className="col-3 my-auto text-center">
                        {!isApplying ? (
                            <button data-testid="apply-button" className={"btn btn-outline-ose"}
                                    onClick={handleApplyClick}>
                                {t('apply')}
                            </button>
                        ) : (
                            <div>
                                <textarea
                                    value={coverLetter}
                                    onChange={handleCoverLetterChange}
                                    placeholder={t('coverLetterPlaceholder')}
                                    className="form-control mb-2"
                                />
                                <button className="btn btn-outline-ose"
                                    onClick={() => applyForJobOffer(jobOffer.id)}>
                                    {t('apply')}
                                </button>
                            </div>
                        )}
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <h5 className="text-dark fw-light mb-3">{t(jobOffer.department)}</h5>
                        <h5 className="text-dark fw-light mb-3">{jobOffer.location}</h5>
                        <h6 className="text-dark fw-light mb-3">{t('startDate') + jobOffer.startDate}</h6>
                        <h6 className="text-dark fw-light mb-3">{t('duration') + jobOffer.duration + t('week')}</h6>
                        <p className="fst-italic fw-light text-dark">
                            {t('estimateEndDate')} {new Date(
                            new Date(jobOffer.startDate).getTime() + jobOffer.duration * 7 * 24 * 60 * 60 * 1000
                        ).toISOString().split('T')[0]}
                        </p>
                        <h6 className="text-dark fw-light mb-3">{jobOffer.salary}$/h</h6>
                        <h6 className="text-dark fw-light mb-3">{jobOffer.hoursPerWeek}h/{t('week')}</h6>
                        <p className="text-dark fw-light mb-3">{jobOffer.description}</p>
                    </div>
                </div>
            </div>

            <div className={`modal ${showModal ? 'show' : ''}`} tabIndex="-1" role="dialog">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h3 className="modal-title">{t('appointments')}</h3>
                            <FontAwesomeIcon icon={faX} data-bs-dismiss="modal"
                                             className="danger-hover fa-lg pe-2"/>
                        </div>
                        <div className="modal-body">
                            <form onSubmit={() => applyForJobOffer(jobOffer.id)}>
                                <textarea></textarea>
                                <button className="btn btn-success me-3 my-2" type="submit" data-dismiss="modal">
                                    {t('confirm')}
                                </button>
                                <button className="btn btn-danger my-2" type="reset" data-dismiss="modal">
                                    {t('cancel')}
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            {/*{*/}
            {/*    showModal &&*/}
            {/*    <div className={`custom-modal show`}>*/}
            {/*        <div className="custom-modal-content">*/}
            {/*            <div className="custom-modal-header">*/}
            {/*                <h5>{t('apply')}</h5>*/}
            {/*                <button className="btn-close" onClick={() => setShowModal(false)}></button>*/}
            {/*            </div>*/}
            {/*            <div className="custom-modal-body">*/}
            {/*            <textarea*/}
            {/*                value={coverLetter}*/}
            {/*                onChange={handleCoverLetterChange}*/}
            {/*                placeholder={t('coverLetterPlaceholder')}*/}
            {/*                className="form-control mb-2"*/}
            {/*                style={{height: "500px"}}*/}
            {/*            />*/}
            {/*            </div>*/}
            {/*            <div className="custom-modal-footer">*/}
            {/*                <button className="btn btn-danger" onClick={() => setShowModal(false)}>*/}
            {/*                    {t('close')}*/}
            {/*                </button>*/}
            {/*                <button className="btn btn-outline-ose" onClick={applyForJobOffer}>*/}
            {/*                    {t('apply')}*/}
            {/*                </button>*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*    </div>*/}
            {/*}*/}
        </div>
    )
}

export default FullJobOffer;