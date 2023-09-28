import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPenToSquare, faX} from '@fortawesome/free-solid-svg-icons';
import React, {useState} from "react";
import Loading from "../util/Loading";
const FullJobOffer = ({ jobOffer, updateOffer}) => {
    const [isLoading, setIsLoading] = useState(false);
    const [isModified, setIsModified] = useState(false);
    const [formData, setFormData] = useState({
        title: jobOffer.title,
        department: jobOffer.department,
        location: jobOffer.location,
        description: jobOffer.description,
        salary: jobOffer.salary,
        hoursPerWeek: jobOffer.hoursPerWeek,
        startDate: jobOffer.startDate,
        duration: jobOffer.duration,
        expirationDate: jobOffer.expirationDate,
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
    })

    const handleChange = (e) => {
        e.preventDefault();
        const {name, value} = e.target;
        if(value.trim() !== '') {
            setFormData({...formData, [name]: value.trim()});
        }
        else {
            setFormData({...formData, [name]: jobOffer[name]});
        }
    }

    const handleClose = (e) => {
        e.preventDefault();
        setFormData(
            {
                title: jobOffer.title,
                department: jobOffer.department,
                location: jobOffer.location,
                description: jobOffer.description,
                salary: jobOffer.salary,
                hoursPerWeek: jobOffer.hoursPerWeek,
                startDate: jobOffer.startDate,
                duration: jobOffer.duration,
                expirationDate: jobOffer.expirationDate,
            }
        )
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        formData.startDate = formData.startDate.split('T')[0];
        formData.expirationDate = formData.expirationDate.split('T')[0];

        const validationErrors = {};

        if (formData.salary <= 0) {
            validationErrors.salary = "Le salaire doit être positif";
        }

        if (formData.hoursPerWeek <= 0) {
            validationErrors.hoursPerWeek = "Le nombre d'heures par semaine doit être positif";
        }

        if (formData.startDate && !/^\d{4}-\d{2}-\d{2}$/.test(formData.startDate)) {
            validationErrors.startDate = "La date de début doit être en format YYYY-MM-DD";
        }
        else if (formData.startDate && new Date(formData.startDate) < new Date()) {
            validationErrors.startDate = "La date de début ne doit pas déjà être passée";
        }

        if (formData.duration && formData.duration < 1) {
            validationErrors.duration = "La durée du stage doit être d'au moins 1 semaine";
        }

        if (formData.expirationDate && !/^\d{4}-\d{2}-\d{2}$/.test(formData.expirationDate)) {
            validationErrors.expirationDate = "La date d'expiration doit être en format YYYY-MM-DD";
        }
        else if (formData.expirationDate && new Date(formData.expirationDate) < new Date()) {
            validationErrors.expirationDate = "La date d'expiration ne doit pas déjà être passée";
        }

        setWarnings(validationErrors);

        if (Object.keys(validationErrors).length === 0) {
            updateOffer(formData);
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
                            <div className="col-3 my-auto text-center">
                                    <div className={`border rounded px-2 
                                    ${jobOffer.jobOfferState === 'OPEN' ? 'border-success text-success':
                                        jobOffer.jobOfferState === 'PENDING' ? 'border-warning text-warning':
                                            jobOffer.jobOfferState === 'SUBMITTED' ? 'border-secondary text-secondary' :
                                                jobOffer.jobOfferState === 'TAKEN' ? 'border-primary text-primary': 'border-danger text-danger'} `}>
                                        {jobOffer.jobOfferState}
                                    </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-12">
                                <h5 className="text-dark fw-light mb-3">{jobOffer.department}</h5>
                                <h5 className="text-dark fw-light mb-3">{jobOffer.location}</h5>
                                <h6 className="text-dark fw-light mb-3">Date de début: {jobOffer.startDate}</h6>
                                <h6 className="text-dark fw-light mb-3">Durée: {jobOffer.duration}</h6>
                                <h6 className="text-dark fw-light mb-3">Date d'expiration: {jobOffer.expirationDate}</h6>
                                <h6 className="text-dark fw-light mb-3">{jobOffer.salary}$/h</h6>
                                <h6 className="text-dark fw-light mb-3">{jobOffer.hoursPerWeek}h/semaine</h6>
                                <p className="text-dark fw-light mb-3">{jobOffer.description}</p>
                            </div>
                        </div>
                        <div className="text-end mb-2" data-bs-toggle="modal" data-bs-target="#editModal">
                            <button className="btn btn-outline-ose my-auto" onClick={() => setIsModified(false)}>
                                Modifier
                                <FontAwesomeIcon icon={faPenToSquare} className="ms-2"/>
                            </button>
                        </div>
                    </>
                )}
                <div id="editModal" className="modal">
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h3 className="modal-title">Modifier<br/>{jobOffer.title}</h3>
                                <FontAwesomeIcon icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2" onClick={handleClose}/>
                            </div>
                            <div className="modal-body">
                                <form onSubmit={handleSubmit}>
                                    <div className="mb-3">
                                        <label htmlFor="title" className="mt-3">Titre</label>
                                        <input type="text" className={`form-control ${warnings.title ? 'is-invalid' : ''}`} id="title" placeholder={jobOffer.title} onChange={handleChange} name="title"/>
                                        {warnings.title && (
                                            <div className="invalid-feedback">
                                                {warnings.title}
                                            </div>
                                        )}
                                        <label htmlFor="department" className="mt-3">Département</label>
                                        <select className={`form-select ${warnings.department ? 'is-invalid' : ''}`}
                                                id="department" name="department" defaultValue="Choisir un département" onChange={handleChange}>
                                            <option value="_410B0">410.B0 - Techniques de comptabilité et de gestion</option>
                                            <option value="_241A1">241.A1 - Techniques de génie mécanique</option>
                                            <option value="_420B0">420.B0 - Techniques de l’informatique</option>
                                            <option value="_210AA">210.AA - Techniques de laboratoire : biotechnologies</option>
                                            <option value="_144A1">144.A1 - Techniques de physiothérapie</option>
                                            <option value="_310A0">310.A0 - Techniques policières</option>
                                            <option value="_145A0">145.A0 - Techniques de santé animale</option>
                                            <option value="_388A0">388.A0 - Techniques de travail social</option>
                                            <option value="_140C0">140.C0 - Technologie d’analyses biomédicales</option>
                                            <option value="_243C0">243.C0 - Technologie de l’électronique industrielle</option>
                                            <option value="_243BA">243.BA - Technologie de l’électronique : Télécommunication</option>
                                            <option value="_241D0">241.D0 - Technologie de maintenance industrielle</option>
                                            <option value="_243A0">245.A0 - Technologie de systèmes ordinés</option>
                                            <option value="_221B0">221.B0 - Technologie du génie civil</option>
                                            <option disabled={true}>Choisir un département</option>
                                        </select>
                                        {warnings.department && (
                                            <div className="invalid-feedback">
                                                {warnings.department}
                                            </div>
                                        )}
                                        <label htmlFor="location" className="mt-3">Lieu</label>
                                        <input type="text" className={`form-control ${warnings.location ? 'is-invalid' : ''}`} id="location" placeholder={jobOffer.location} onChange={handleChange} name="location"/>
                                        {warnings.location && (
                                            <div className="invalid-feedback">
                                                {warnings.location}
                                            </div>
                                        )}
                                        <label htmlFor="startDate" className="mt-3">Date de début</label>
                                        <input type="date" className={`form-control ${warnings.startDate ? 'is-invalid' : ''}`} id="startDate" placeholder={jobOffer.startDate.split('T')[0]} onChange={handleChange} name="startDate"/>
                                        {warnings.startDate && (
                                            <div className="invalid-feedback">
                                                {warnings.startDate}
                                            </div>
                                        )}
                                        <label htmlFor="duration" className="mt-3">Durée</label>
                                        <input type="number" className={`form-control ${warnings.duration ? 'is-invalid' : ''}`} id="duration" placeholder={`${jobOffer.duration} semaine(s)`} onChange={handleChange} name="duration"/>
                                        {warnings.duration && (
                                            <div className="invalid-feedback">
                                                {warnings.duration}
                                            </div>
                                        )}
                                        <label htmlFor="expirationDate" className="mt-3">Date d'expiration</label>
                                        <input type="date" className={`form-control ${warnings.expirationDate ? 'is-invalid' : ''}`} id="expirationDate" placeholder={jobOffer.expirationDate.split('T')[0]} onChange={handleChange} name="expirationDate"/>
                                        {warnings.expirationDate && (
                                            <div className="invalid-feedback">
                                                {warnings.expirationDate}
                                            </div>
                                        )}
                                        <label htmlFor="salary" className="mt-3">Salaire</label>
                                        <input type="number" className={`form-control ${warnings.salary ? 'is-invalid' : ''}`} id="salary" placeholder={`${jobOffer.salary}$/h`} onChange={handleChange} name="salary"/>
                                        {warnings.salary && (
                                            <div className="invalid-feedback">
                                                {warnings.salary}
                                            </div>
                                        )}
                                        <label htmlFor="hoursPerWeek" className="mt-3">Heures par semaine</label>
                                        <input type="number" className={`form-control ${warnings.hoursPerWeek ? 'is-invalid' : ''}`} id="hoursPerWeek" placeholder={`${jobOffer.hoursPerWeek}h/semaine`} onChange={handleChange} name="hoursPerWeek"/>
                                        {warnings.hoursPerWeek && (
                                            <div className="invalid-feedback">
                                                {warnings.hoursPerWeek}
                                            </div>
                                        )}
                                        <label htmlFor="description" className="mt-3">Description</label>
                                        <textarea className={`form-control ${warnings.description ? 'is-invalid' : ''}`} id="description" placeholder={jobOffer.description} onChange={handleChange} name="descrption"/>
                                        {warnings.description && (
                                            <div className="invalid-feedback">
                                                {warnings.description}
                                            </div>
                                        )}
                                    </div>
                                    {!isModified ? (
                                    <div className="text-end">
                                        <button type="submit" className="btn btn-success me-2">Modifier</button>
                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={handleClose}>Annuler</button>
                                    </div>
                                    ) : (
                                        <div className="text-end">
                                            <button type="submit" className="btn btn-success me-2" disabled>Modifié</button>
                                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={handleClose}>Fermer</button>
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