import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import Loading from "../util/Loading";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPaperPlane} from "@fortawesome/free-solid-svg-icons";

const NewOfferForm = ({user}) => {
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const [formData, setFormData] = useState({
        title: '',
        department: '',
        location: '',
        description: '',
        salary: '',
        hoursPerWeek: '',
        startDate: '',
        duration: '',
        expirationDate: '',
        jobOfferState: 'SUBMITTED',
    });
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

    const saveOffer = async () => {
        setIsLoading(true);
        console.log(formData)
        axiosInstance
            .post(`/employer/offer?employerId=${user.id}`, formData)
            .then((response) =>{
                toast.success("Offre créée avec succès")
                setIsLoading(false);
                navigate('/employer');
            })
            .catch((error) => {
                toast.error("Erreur lors de la création de l'offre: " + error.response.data.message)
                setIsLoading(false);
            }
        )
    }

    const handleChanges = (e) => {
        const {name, value} = e.target;
        setWarnings({...warnings, [name]: ""})
        setFormData({...formData, [name]: value.trim()});
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        const validationErrors = {};

        if (formData.title === '') {
            validationErrors.title = "Le titre est obligatoire";
        }

        if (formData.department === '') {
            validationErrors.department = "Le département est obligatoire";
        }

        if (formData.location === '') {
            validationErrors.location = "Le lieu de travail est obligatoire";
        }

        if (formData.description === '') {
            validationErrors.description = "La description est obligatoire";
        }

        if (formData.salary === '') {
            validationErrors.salary = "Le salaire est obligatoire";
        }
        else if (formData.salary <= 0) {
            validationErrors.salary = "Le salaire doit être positif";
        }

        if (formData.hoursPerWeek === '') {
            validationErrors.hoursPerWeek = "Le nombre d'heures par semaine est obligatoire";
        }
        else if (formData.hoursPerWeek <= 0) {
            validationErrors.hoursPerWeek = "Le nombre d'heures par semaine doit être positif";
        }

        if (formData.startDate === '') {
            validationErrors.startDate = "La date de début est obligatoire";
        }
        else if (formData.startDate && !/^\d{4}-\d{2}-\d{2}$/.test(formData.startDate)) {
            validationErrors.startDate = "La date de début doit être en format YYYY-MM-DD";
        }
        else if (formData.startDate && new Date(formData.startDate) < new Date()) {
            validationErrors.startDate = "La date de début ne doit pas déjà être passée";
        }

        if (formData.duration === '') {
            validationErrors.duration = "La durée du stage est obligatoire";
        }
        else if (formData.duration && formData.duration < 1) {
            validationErrors.duration = "La durée du stage doit être d'au moins 1 semaine";
        }

        if (formData.expirationDate === '') {
            validationErrors.expirationDate = "La date d'expiration est obligatoire";
        }
        else if (formData.expirationDate && !/^\d{4}-\d{2}-\d{2}$/.test(formData.expirationDate)) {
            validationErrors.expirationDate = "La date d'expiration doit être en format YYYY-MM-DD";
        }
        else if (formData.expirationDate && new Date(formData.expirationDate) < new Date()) {
            validationErrors.expirationDate = "La date d'expiration ne doit pas déjà être passée";
        }

        setWarnings(validationErrors);

        if (Object.keys(validationErrors).length === 0) {
            saveOffer().then(r => console.log(r));
        }
    }

    return (
        <div className="container">
            <h1 className="text-center fw-light">Création d'une nouvelle offre</h1>
            {isLoading ? (
                <Loading />
            ) : (
                <div className="row">
                    <div className="col-9 mx-auto">
                        <form className="form-group" onSubmit={handleSubmit}>
                            <div className="mb-3">
                                <label htmlFor="title">Titre du poste</label>
                                <input
                                    type="text"
                                    className={`form-control ${
                                        warnings.title ? 'is-invalid' : ''
                                    }`}
                                    id="title"
                                    placeholder="Titre"
                                    name="title"
                                    onChange={handleChanges}
                                    required
                                />
                                {warnings.title && (
                                    <div className="invalid-feedback">
                                        {warnings.title}
                                    </div>
                                )}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="department">Département</label>
                                <select
                                    className={`form-select ${
                                        warnings.department ? 'is-invalid' : ''
                                    }`}
                                    id="department"
                                    name="department"
                                    defaultValue="Choisir un département"
                                    onChange={handleChanges}
                                    required
                                >
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
                            </div>

                            <div className="mb-3">
                                <label htmlFor="location">Lieu de travail</label>
                                <input
                                    type="text"
                                    className={`form-control ${
                                        warnings.location ? 'is-invalid' : ''
                                    }`}
                                    id="location"
                                    placeholder="Lieu de travail"
                                    name="location"
                                    onChange={handleChanges}
                                    required
                                />
                                {warnings.location && (
                                    <div className="invalid-feedback">
                                        {warnings.location}
                                    </div>
                                )}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="description">
                                    Description du poste
                                </label>
                                <textarea
                                    className={`form-control ${
                                        warnings.description ? 'is-invalid' : ''
                                    }`}
                                    id="description"
                                    rows="3"
                                    placeholder="Description du poste"
                                    name="description"
                                    onChange={handleChanges}
                                    required
                                ></textarea>
                                {warnings.description && (
                                    <div className="invalid-feedback">
                                        {warnings.description}
                                    </div>
                                )}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="salary">Salaire horaire</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    className={`form-control ${
                                        warnings.salary ? 'is-invalid' : ''
                                    }`}
                                    id="salary"
                                    placeholder="Salaire horaire"
                                    name="salary"
                                    onChange={handleChanges}
                                    required
                                />
                                {warnings.salary && (
                                    <div className="invalid-feedback">
                                        {warnings.salary}
                                    </div>
                                )}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="hoursPerWeek">
                                    Nombre d'heures par semaine
                                </label>
                                <input
                                    type="number"
                                    step="0.01"
                                    className={`form-control ${
                                        warnings.hoursPerWeek ? 'is-invalid' : ''
                                    }`}
                                    id="hoursPerWeek"
                                    placeholder="Nombre d'heures par semaine"
                                    name="hoursPerWeek"
                                    onChange={handleChanges}
                                    required
                                />
                                {warnings.hoursPerWeek && (
                                    <div className="invalid-feedback">
                                        {warnings.hoursPerWeek}
                                    </div>
                                )}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="startDate">Date de début</label>
                                <input
                                    type="date"
                                    className={`form-control ${
                                        warnings.startDate ? 'is-invalid' : ''
                                    }`}
                                    id="startDate"
                                    placeholder="Date de début"
                                    name="startDate"
                                    onChange={handleChanges}
                                    required
                                />
                                {warnings.startDate && (
                                    <div className="invalid-feedback">
                                        {warnings.startDate}
                                    </div>
                                )}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="duration">
                                    Durée du stage (en semaine)
                                </label>
                                <input
                                    type="number"
                                    step="0.1"
                                    className={`form-control ${
                                        warnings.duration ? 'is-invalid' : ''
                                    }`}
                                    id="duration"
                                    placeholder="Durée du stage"
                                    name="duration"
                                    onChange={handleChanges}
                                    required
                                />
                                {warnings.duration && (
                                    <div className="invalid-feedback">
                                        {warnings.duration}
                                    </div>
                                )}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="expirationDate">
                                    Date d'expiration d'affichage
                                </label>
                                <input
                                    type="date"
                                    className={`form-control ${
                                        warnings.expirationDate
                                            ? 'is-invalid'
                                            : ''
                                    }`}
                                    id="expirationDate"
                                    placeholder="Date d'expiration"
                                    name="expirationDate"
                                    onChange={handleChanges}
                                    required
                                />
                                {warnings.expirationDate && (
                                    <div className="invalid-feedback">
                                        {warnings.expirationDate}
                                    </div>
                                )}
                            </div>

                            <div className="row my-4">
                                <div className="col-4 mx-auto">
                                    <button
                                        type="submit"
                                        className="btn btn-outline-ose col-12"
                                    >
                                        Soumettre l'offre{' '}
                                        <FontAwesomeIcon icon={faPaperPlane} />
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );

}

export default NewOfferForm;