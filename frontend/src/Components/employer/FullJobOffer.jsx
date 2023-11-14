import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPenToSquare, faX} from '@fortawesome/free-solid-svg-icons';
import React, {useEffect, useRef, useState} from "react";
import Loading from "../util/Loading";
import State from "../util/State";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";
const FullJobOffer = ({ jobOffer, updateOffer}) => {
    const loadCalculateEndDate = () => {
        return new Date(new Date(jobOffer.startDate).getTime() + jobOffer.duration * 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0];
    }
    const {t} = useTranslation();
    const formRef = useRef(null)
    const [isLoading, setIsLoading] = useState(false);
    const [isModified, setIsModified] = useState(false);
    const [estimateEndDate, setEstimateEndDate] = useState('');
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

    const { darkMode } = useDarkMode();

    useEffect(() => {
        setEstimateEndDate(loadCalculateEndDate())
        setNewOffer({
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
        })
    }, [jobOffer]);

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

    const calculateEndDate = (startDate, duration) => {
        setEstimateEndDate(new Date(new Date(startDate).getTime() + duration * 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0]);
    }

    const handleChange = (e) => {

        e.preventDefault();
        const {name, value} = e.target;
        if(value.trim() !== '') {
            if(name === 'duration') {
                calculateEndDate(newOffer.startDate, value);
            }
            else if(name === 'startDate') {
                calculateEndDate(value, newOffer.duration);
            }
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
            <div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded`}>
                {isLoading ? (
                    <Loading/>
                ) : (
                    <>
                        <div className="row justify-content-between">
                            <div className="col-9">
                                <h2 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light pt-1`}>{jobOffer.title}</h2>
                            </div>
                            <div className="col-3 my-auto text-center pt-2">
                                    <State state={jobOffer.jobOfferState}/>
                            </div>
                        </div>
                        {jobOffer.jobOfferState === 'REFUSED' && (
                            <div className="alert alert-danger" role="alert">
                                {t('refusalReason') + " : " + jobOffer.refusReason}
                            </div>
                        )}
                        <div className="row">
                            <div className="col-12">
                                <h5 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t(jobOffer.department)}</h5>
                                <h5 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.location}</h5>
                                { jobOffer.startDate !== null &&
                                    (<h6 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('startDate') + jobOffer.startDate}</h6>)
                                }
                                <h6 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('duration') + jobOffer.duration + t('week')}</h6>
                                { jobOffer.expirationDate !== null &&
                                    (<h6 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t('expirationDate')} {jobOffer.expirationDate}</h6>)
                                }
                                <p className={`fst-italic fw-light ${darkMode ? 'text-light' : 'text-dark'}`}>{t('estimateEndDate')} {estimateEndDate}</p>
                                <h6 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.salary}$/h</h6>
                                <h6 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.hoursPerWeek}h/{t('week')}</h6>
                                <p className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{jobOffer.description}</p>
                            </div>
                        </div>
                        <div className="text-end mb-2" data-bs-toggle="modal" data-bs-target="#editModal" data-testid="editModal">
                            <button className="btn btn-outline-ose my-auto" data-testid="edit-button" onClick={() => setIsModified(false)}>
                                {t('edit')}
                                <FontAwesomeIcon icon={faPenToSquare} className="ms-2"/>
                            </button>
                        </div>
                    </>
                )}
                <div id="editModal" className="modal">
                    <div className="modal-dialog">
                        <div className={`modal-content ${darkMode ? 'bg-dark' : ''}`}>
                            <div className="modal-header">
                                <h3 className="modal-title">{t('edit')}<br/>{jobOffer.title}</h3>
                                <FontAwesomeIcon icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2" onClick={handleClose}/>
                            </div>
                            <div className="modal-body">
                                <form ref={formRef} onSubmit={handleSubmit} data-testid="form">
                                    <div className="mb-3">
                                        <label htmlFor="title" className="mt-3">{t('internshipTitle')}</label>
                                        <input type="text" className={`form-control ${warnings.title ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`} id="title" placeholder={jobOffer.title} onChange={handleChange} name="title"/>
                                        {warnings.title && (
                                            <div className="invalid-feedback">
                                                {warnings.title}
                                            </div>
                                        )}
                                        <label htmlFor="department" className="mt-3">{t('department')}</label>
                                        <select className={`form-select ${warnings.department ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`}
                                                id="department" name="department" defaultValue={t('chooseADepartment')} onChange={handleChange}>
                                            <option className="clickable" value="_410B0">{t('_410B0')}</option>
                                            <option className="clickable" value="_241A1">{t('_241A1')}</option>
                                            <option className="clickable" value="_420B0">{t('_420B0')}</option>
                                            <option className="clickable" value="_210AA">{t('_210AA')}</option>
                                            <option className="clickable" value="_144A1">{t('_144A1')}</option>
                                            <option className="clickable" value="_310A0">{t('_310A0')}</option>
                                            <option className="clickable" value="_145A0">{t('_145A0')}</option>
                                            <option className="clickable" value="_388A0">{t('_388A0')}</option>
                                            <option className="clickable" value="_140C0">{t('_140C0')}</option>
                                            <option className="clickable" value="_243C0">{t('_243C0')}</option>
                                            <option className="clickable" value="_243BA">{t('_243BA')}</option>
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
                                        <input type="text" className={`form-control ${warnings.location ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`} id="location" placeholder={jobOffer.location} onChange={handleChange} name="location"/>
                                        {warnings.location && (
                                            <div className="invalid-feedback">
                                                {warnings.location}
                                            </div>
                                        )}
                                        <label htmlFor="nbOfCandidates">{t('nbOfCandidates')}</label>
                                        <input type="number" min="0" max="10" className={`form-control ${warnings.nbOfCandidates ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`} id="nbOfCandidates" placeholder={jobOffer.nbOfCandidates} name="nbOfCandidates" onChange={handleChange}/>
                                        {warnings.nbOfCandidates && (
                                            <div className="invalid-feedback">
                                                {warnings.nbOfCandidates}
                                            </div>
                                        )}
                                        <label htmlFor="startDate" className="mt-3">{t('startDate')}</label>
                                        <input type="date" className={`form-control ${warnings.startDate ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`} id="startDate" placeholder={jobOffer.startDate} onChange={handleChange} name="startDate"/>
                                        {warnings.startDate && (
                                            <div className="invalid-feedback">
                                                {warnings.startDate}
                                            </div>
                                        )}
                                        <label htmlFor="duration" className="mt-3">{t('duration')}</label>
                                        <input type="number" className={`form-control ${warnings.duration ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`} id="duration" placeholder={`${jobOffer.duration} semaine(s)`} onChange={handleChange} name="duration"/>
                                        {warnings.duration && (
                                            <div className="invalid-feedback">
                                                {warnings.duration}
                                            </div>
                                        )}
                                        <p className="fst-italic fw-light text-dark">{t('estimateEndDate')} {estimateEndDate}</p>
                                        <label htmlFor="expirationDate" className="mt-3">{t('expirationDate')}</label>
                                        <input type="date" className={`form-control ${warnings.expirationDate ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`} id="expirationDate" placeholder={jobOffer.expirationDate.split('T')[0]} onChange={handleChange} name="expirationDate"/>
                                        {warnings.expirationDate && (
                                            <div className="invalid-feedback">
                                                {warnings.expirationDate}
                                            </div>
                                        )}
                                        <label htmlFor="salary" className="mt-3">{t('salary')}</label>
                                        <input type="number" className={`form-control ${warnings.salary ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`} id="salary" placeholder={`${jobOffer.salary}$/h`} onChange={handleChange} name="salary"/>
                                        {warnings.salary && (
                                            <div className="invalid-feedback">
                                                {warnings.salary}
                                            </div>
                                        )}
                                        <label htmlFor="hoursPerWeek" className="mt-3">{t('hoursPerWeek')}</label>
                                        <input type="number" className={`form-control ${warnings.hoursPerWeek ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`} id="hoursPerWeek" placeholder={`${jobOffer.hoursPerWeek}h/semaine`} onChange={handleChange} name="hoursPerWeek"/>
                                        {warnings.hoursPerWeek && (
                                            <div className="invalid-feedback">
                                                {warnings.hoursPerWeek}
                                            </div>
                                        )}
                                        <label htmlFor="description" className="mt-3">{t('internshipDetails')}</label>
                                        <textarea className={`form-control ${warnings.description ? 'is-invalid' : ''} ${darkMode ? "dark-input" : ""}`} id="description" placeholder={jobOffer.description} onChange={handleChange} name="description"/>
                                        {warnings.description && (
                                            <div className="invalid-feedback">
                                                {warnings.description}
                                            </div>
                                        )}
                                    </div>
                                    {!isModified ? (
                                    <div className="text-end">
                                        <button type="submit" className="btn btn-success me-2" data-testid="edit">{t('edit')}</button>
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={handleClose} data-testid="cancel">{t('cancel')}</button>
                                    </div>
                                    ) : (
                                        <div className="text-end">
                                            <div className="text-success me-2 text-center">{t('edited')}</div>
                                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={handleClose} data-testid="close">{t('close')}</button>
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