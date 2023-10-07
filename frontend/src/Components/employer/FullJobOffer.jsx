import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPenToSquare, faX} from '@fortawesome/free-solid-svg-icons';
import React, {useRef, useState} from "react";
import Loading from "../util/Loading";
import State from "../util/State";
import {useTranslation} from "react-i18next";
const FullJobOffer = ({ jobOffer, updateOffer}) => {
    const {t} = useTranslation();
    const formRef = useRef(null)
    const [isLoading, setIsLoading] = useState(false);
    const [isModified, setIsModified] = useState(false);
    const [newOffer, setNewOffer] = useState({
        jobOfferState: 'SUBMITTED',
        id: jobOffer.id,
        refusReason: jobOffer.refusReason,
        title: jobOffer.title,
        department: jobOffer.department,
        location: jobOffer.location,
        description: jobOffer.description,
        salary: jobOffer.salary,
        hoursPerWeek: jobOffer.hoursPerWeek,
        startDate: jobOffer.startDate,
        duration: jobOffer.duration,
        expirationDate: jobOffer.expirationDate,
        nbOfCandidates: jobOffer.nbOfCandidates,
    })

    const [warnings, setWarnings] = useState({
        title: '',
        department: '',
        location: '',
        description: '',
        salary: '',
        hoursPerWeek: '',
        startDate: '',
        duration: '',
        expirationDate: '',
        nbOfCandidates: ''
    })

    const handleChange = (e) => {
        e.preventDefault();
        const {name, value} = e.target;
        if(value.trim() !== '') {
            setNewOffer({...newOffer, [name]: value.trim()});
        }
        else {
            setNewOffer({...newOffer, [name]: jobOffer[name]});
        }
    }

    const handleClose = (e) => {
        e.preventDefault();
        setNewOffer(
            {
                jobOfferState: jobOffer.jobOfferState,
                refusReason: jobOffer.refusReason,
                id: jobOffer.id,
                title: jobOffer.title,
                department: jobOffer.department,
                location: jobOffer.location,
                description: jobOffer.description,
                salary: jobOffer.salary,
                hoursPerWeek: jobOffer.hoursPerWeek,
                startDate: jobOffer.startDate,
                duration: jobOffer.duration,
                expirationDate: jobOffer.expirationDate,
                nbOfCandidates: jobOffer.nbOfCandidates,
            }
        )
        formRef.current.reset();
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        newOffer.startDate = newOffer.startDate.split('T')[0];
        newOffer.expirationDate = newOffer.expirationDate.split('T')[0];

        const validationErrors = {};

        if (newOffer.salary <= 0) {
            validationErrors.salary = t('salaryMustBePositive');
        }

        if (newOffer.hoursPerWeek <= 0) {
            validationErrors.hoursPerWeek = t('hoursPerWeekMustBePositive')
        }

        if (newOffer.startDate && !/^\d{4}-\d{2}-\d{2}$/.test(newOffer.startDate)) {
            validationErrors.startDate = t('startDateFormat');
        }

        if (newOffer.nbOfCandidates === '') {
            validationErrors.nbOfCandidates = t('nbOfCandidatesRequired');
        }
        else if (newOffer.nbOfCandidates < 1) {
            validationErrors.nbOfCandidates = t('minimumNbOfCandidates');
        }

        else if (newOffer.startDate && new Date(newOffer.startDate) < new Date()) {
            validationErrors.startDate = t('startDateNotPassed');
        }

        if (newOffer.duration && newOffer.duration < 1) {
            validationErrors.duration = t('minimumDuration');
        }

        if (newOffer.expirationDate && !/^\d{4}-\d{2}-\d{2}$/.test(newOffer.expirationDate)) {
            validationErrors.expirationDate = t('endDateFormat');
        }
        else if (newOffer.expirationDate && new Date(newOffer.expirationDate) < new Date()) {
            validationErrors.expirationDate = t('endDateNotPassed');
        }

        setWarnings(validationErrors);

        if (Object.keys(validationErrors).length === 0) {
            updateOffer(newOffer);
            setIsModified(true);
        }
    }

    return (
        <div className="row my-2">
            <div className="col-12 bg-white rounded">
                {isLoading ? (
                    <Loading/>
                ) : (
                    <>
                        <div className="row justify-content-between">
                            <div className="col-9">
                                <h2 className="text-dark fw-light pt-1">{jobOffer.title}</h2>
                            </div>
                            <div className="col-3 my-auto text-center pt-2">
                                    <State state={jobOffer.jobOfferState}/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-12">
                                <h5 className="text-dark fw-light mb-3">{t(jobOffer.department)}</h5>
                                <h5 className="text-dark fw-light mb-3">{jobOffer.location}</h5>
                                { jobOffer.startDate !== null &&
                                    (<h6 className="text-dark fw-light mb-3">{t('startDate') + jobOffer.startDate}</h6>)
                                }
                                <h6 className="text-dark fw-light mb-3">{t('duration') + jobOffer.duration + t('week')}</h6>
                                { jobOffer.expirationDate !== null &&
                                    (<h6 className="text-dark fw-light mb-3">{t('expirationDate')} {jobOffer.expirationDate}</h6>)
                                }
                                <h6 className="text-dark fw-light mb-3">{jobOffer.salary}$/h</h6>
                                <h6 className="text-dark fw-light mb-3">{jobOffer.hoursPerWeek}h/{t('week')}</h6>
                                <p className="text-dark fw-light mb-3">{jobOffer.description}</p>
                            </div>
                        </div>
                        <div className="text-end mb-2" data-bs-toggle="modal" data-bs-target="#editModal">
                            <button className="btn btn-outline-ose my-auto" onClick={() => setIsModified(false)}>
                                {t('edit')}
                                <FontAwesomeIcon icon={faPenToSquare} className="ms-2"/>
                            </button>
                        </div>
                    </>
                )}
                <div id="editModal" className="modal">
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h3 className="modal-title">{t('edit')}<br/>{jobOffer.title}</h3>
                                <FontAwesomeIcon icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2" onClick={handleClose}/>
                            </div>
                            <div className="modal-body">
                                <form ref={formRef} onSubmit={handleSubmit}>
                                    <div className="mb-3">
                                        <label htmlFor="title" className="mt-3">{t('internshipTitle')}</label>
                                        <input type="text" className={`form-control ${warnings.title ? 'is-invalid' : ''}`} id="title" placeholder={jobOffer.title} onChange={handleChange} name="title"/>
                                        {warnings.title && (
                                            <div className="invalid-feedback">
                                                {warnings.title}
                                            </div>
                                        )}
                                        <label htmlFor="department" className="mt-3">{t('department')}</label>
                                        <select className={`form-select ${warnings.department ? 'is-invalid' : ''}`}
                                                id="department" name="department" defaultValue={t('chooseADepartment')} onChange={handleChange}>
                                            <option value="_410B0">{t('_410B0')}</option>
                                            <option value="_241A1">{t('_241A1')}</option>
                                            <option value="_420B0">{t('_420B0')}</option>
                                            <option value="_210AA">{t('_210AA')}</option>
                                            <option value="_144A1">{t('_144A1')}</option>
                                            <option value="_310A0">{t('_310A0')}</option>
                                            <option value="_145A0">{t('_145A0')}</option>
                                            <option value="_388A0">{t('_388A0')}</option>
                                            <option value="_140C0">{t('_140C0')}</option>
                                            <option value="_243C0">{t('_243C0')}</option>
                                            <option value="_243BA">{t('_243BA')}</option>
                                            <option value="_241D0">{t('_241D0')}</option>
                                            <option value="_243A0">{t('_243A0')}</option>
                                            <option value="_221B0">{t('_221B0')}</option>
                                            <option disabled={true}>{t('chooseADepartment')}</option>
                                        </select>
                                        {warnings.department && (
                                            <div className="invalid-feedback">
                                                {warnings.department}
                                            </div>
                                        )}
                                        <label htmlFor="location" className="mt-3">{t('location')}</label>
                                        <input type="text" className={`form-control ${warnings.location ? 'is-invalid' : ''}`} id="location" placeholder={jobOffer.location} onChange={handleChange} name="location"/>
                                        {warnings.location && (
                                            <div className="invalid-feedback">
                                                {warnings.location}
                                            </div>
                                        )}
                                        <label htmlFor="nbOfCandidates">{t('nbOfCandidates')}</label>
                                        <input type="number" min="0" max="10" className={`form-control ${warnings.nbOfCandidates ? 'is-invalid' : ''}`} id="nbOfCandidates" placeholder={jobOffer.nbOfCandidates} name="nbOfCandidates" onChange={handleChange}/>
                                        {warnings.nbOfCandidates && (
                                            <div className="invalid-feedback">
                                                {warnings.nbOfCandidates}
                                            </div>
                                        )}
                                        <label htmlFor="startDate" className="mt-3">{t('startDate')}</label>
                                        <input type="date" className={`form-control ${warnings.startDate ? 'is-invalid' : ''}`} id="startDate" placeholder={jobOffer.startDate.split('T')[0]} onChange={handleChange} name="startDate"/>
                                        {warnings.startDate && (
                                            <div className="invalid-feedback">
                                                {warnings.startDate}
                                            </div>
                                        )}
                                        <label htmlFor="duration" className="mt-3">{t('duration')}</label>
                                        <input type="number" className={`form-control ${warnings.duration ? 'is-invalid' : ''}`} id="duration" placeholder={`${jobOffer.duration} semaine(s)`} onChange={handleChange} name="duration"/>
                                        {warnings.duration && (
                                            <div className="invalid-feedback">
                                                {warnings.duration}
                                            </div>
                                        )}
                                        <label htmlFor="expirationDate" className="mt-3">{t('expirationDate')}</label>
                                        <input type="date" className={`form-control ${warnings.expirationDate ? 'is-invalid' : ''}`} id="expirationDate" placeholder={jobOffer.expirationDate.split('T')[0]} onChange={handleChange} name="expirationDate"/>
                                        {warnings.expirationDate && (
                                            <div className="invalid-feedback">
                                                {warnings.expirationDate}
                                            </div>
                                        )}
                                        <label htmlFor="salary" className="mt-3">{t('salary')}</label>
                                        <input type="number" className={`form-control ${warnings.salary ? 'is-invalid' : ''}`} id="salary" placeholder={`${jobOffer.salary}$/h`} onChange={handleChange} name="salary"/>
                                        {warnings.salary && (
                                            <div className="invalid-feedback">
                                                {warnings.salary}
                                            </div>
                                        )}
                                        <label htmlFor="hoursPerWeek" className="mt-3">{t('hoursPerWeek')}</label>
                                        <input type="number" className={`form-control ${warnings.hoursPerWeek ? 'is-invalid' : ''}`} id="hoursPerWeek" placeholder={`${jobOffer.hoursPerWeek}h/semaine`} onChange={handleChange} name="hoursPerWeek"/>
                                        {warnings.hoursPerWeek && (
                                            <div className="invalid-feedback">
                                                {warnings.hoursPerWeek}
                                            </div>
                                        )}
                                        <label htmlFor="description" className="mt-3">{t('internshipDetails')}</label>
                                        <textarea className={`form-control ${warnings.description ? 'is-invalid' : ''}`} id="description" placeholder={jobOffer.description} onChange={handleChange} name="description"/>
                                        {warnings.description && (
                                            <div className="invalid-feedback">
                                                {warnings.description}
                                            </div>
                                        )}
                                    </div>
                                    {!isModified ? (
                                    <div className="text-end">
                                        <button type="submit" className="btn btn-success me-2">{t('edit')}</button>
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={handleClose}>{t('cancel')}</button>
                                    </div>
                                    ) : (
                                        <div className="text-end">
                                            <div className="text-success me-2 text-center">{t('edited')}</div>
                                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={handleClose}>{t('close')}</button>
                                        </div>
                                    )}
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default FullJobOffer;