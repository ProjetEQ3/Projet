import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import React, {useEffect, useState} from "react"
import {useTranslation} from "react-i18next"
import { Modal, Button } from 'react-bootstrap'

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
                                <button
                                    className="btn btn-outline-ose"
                                    onClick={() => applyForJobOffer(jobOffer.id)}
                                >
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


            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>{t('apply')}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
          <textarea
              value={coverLetter}
              onChange={handleCoverLetterChange}
              placeholder={t('coverLetterPlaceholder')}
              className="form-control mb-2"
          />
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="light" className={"btn-outline-ose"} onClick={() => setShowModal(false)}>
                        {t('close')}
                    </Button>
                    <Button variant="light" className={"btn-outline-ose"} onClick={() => applyForJobOffer(jobOffer.id)}>
                        {t('apply')}
                    </Button>
                </Modal.Footer>
            </Modal>

        </div>
    )
}

export default FullJobOffer;