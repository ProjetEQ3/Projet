import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import React, {useEffect, useState} from "react"
import {useTranslation} from "react-i18next";
import JobOffer from "../../model/JobOffer";
import {faTimes} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useDarkMode} from "../../context/DarkModeContext";

const FullJobOffer = ({user, jobOffer, updatedOffer}) => {
    const {t} = useTranslation()
    const [coverLetter, setCoverLetter] = useState("")

    const { darkMode } = useDarkMode();

    useEffect(() => {
        setCoverLetter("")
    }, [jobOffer])

    const applyForJobOffer = (jobOfferId) => {
        axiosInstance
            .post(`/student/applyJobOffer/${user.id}/${jobOfferId}`, coverLetter === "" ? "EMPTY_COVER_LETTER" : coverLetter, {
                headers: { 'Content-Type': 'text/plain' }
            }).then((response) => {
                let updatedJobOffer = new JobOffer()
                updatedJobOffer.init(response.data)
                updatedJobOffer.hasApplied = true

                updatedOffer(updatedJobOffer)
                toast.success(t('appliedJobOffer'))
            }).catch((error) => {
                toast.error(t('pushingError') + t(error.response?.data.message))
            })
    }

    const handleCoverLetterChange = (e) => {
        setCoverLetter(e.target.value)
    }

    return (
        <>
            <div className="row my-2">
                <div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded py-3`}>
                    <div className="row justify-content-between">
                        <div className="col-9">
                            <h2 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light pt-1`}>{jobOffer.title}</h2>
                        </div>
                        <div className="col-3 my-auto text-center">
                            {jobOffer.hasApplied ?
                                <button data-testid="apply-button" className={`btn btn-ose btn-success`} disabled>{t('applied')}</button> :
                                <button data-testid="apply-button" className={"btn btn-outline-ose"}
                                        data-bs-toggle="modal" data-bs-target="#apply">{t('apply')}</button>

                            }
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-12">
                            <h5 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t(jobOffer.department)}</h5>
                            <h5 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.location}</h5>
                            <h6 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('startDate') + jobOffer.startDate}</h6>
                            <h6 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('duration') + jobOffer.duration + t('week')}</h6>
                            <p className={`fst-italic fw-light ${darkMode ? 'text-light' : 'text-dark'}`}>
                                {t('estimateEndDate')} {new Date(
                                new Date(jobOffer.startDate).getTime() + jobOffer.duration * 7 * 24 * 60 * 60 * 1000
                            ).toISOString().split('T')[0]}
                            </p>
                            <h6 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.salary}$/h</h6>
                            <h6 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.hoursPerWeek}h/{t('week')}</h6>
                            <p className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.description}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div id="apply" className={"modal modal-lg"}>
                <div className="modal-dialog modal-dialog-centered">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h3 className="modal-title">{t('apply')}</h3>
                            <FontAwesomeIcon icon={faTimes} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2"/>
                        </div>
                        <div className="modal-body">
                            <form>
                                <textarea rows="15" className={"form-control col-12"} value={coverLetter} onChange={handleCoverLetterChange} placeholder={t('coverLetterPlaceholder')}></textarea>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-danger" type="reset" data-bs-dismiss="modal">{t('cancel')}</button>
                            <button className="btn btn-outline-ose" data-bs-dismiss="modal" onClick={() => applyForJobOffer(jobOffer.id)} type="submit">{t('apply')}</button>
                        </div>
                    </div>
                </div>
            </div>
        </>

    )
}

export default FullJobOffer;