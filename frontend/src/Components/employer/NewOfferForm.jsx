import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import Loading from "../util/Loading";

const NewOfferForm = ({user}) => {
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const [formData, setFormData] = useState({
        title: '',
        department: '',
        location: '',
        description: '',
        salary: '',
        hourPerWeek: '',
        startDate: '',
        duration: '',
        expireDate: '',
    });
    const [titleValid, setTitleValid] = useState(true);
    const [departmentValid, setDepartmentValid] = useState(true);
    const [locationValid, setLocationValid] = useState(true);
    const [descriptionValid, setDescriptionValid] = useState(true);
    const [salaryValid, setSalaryValid] = useState(true);
    const [hourPerWeekValid, setHourPerWeekValid] = useState(true);
    const [startDateValid, setStartDateValid] = useState(true);
    const [durationValid, setDurationValid] = useState(true);
    const [expireDateValid, setExpireDateValid] = useState(true);

    const saveOffer = async () => {
        setIsLoading(true);
        console.log(formData);
        setIsLoading(false);
        navigate('/employer');
    }
    const handleChanges = (e) => {
        const {name, value} = e.target;
        setFormData({...formData, [name]: value.trim()});
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        setTitleValid(true);
        setDepartmentValid(true);
        setLocationValid(true);
        setDescriptionValid(true);
        setSalaryValid(true);
        setHourPerWeekValid(true);
        setStartDateValid(true);
        setDurationValid(true);
        setExpireDateValid(true);

        if (formData.title === '') {
            setTitleValid(false);
            alert('Le titre est obligatoire');
            return;
        }

        if (formData.department === '') {
            setDepartmentValid(false);
            alert('Le département est obligatoire');
            return;
        }

        if (formData.location === '') {
            setLocationValid(false);
            alert('Le lieu de travail est obligatoire');
            return;
        }

        if (formData.description === '') {
            setDescriptionValid(false);
            alert('La description est obligatoire');
            return;
        }

        if (formData.salary === '') {
            setSalaryValid(false);
            alert('Le salaire est obligatoire');
            return;
        }
        else if (formData.salary <= 0) {
            setSalaryValid(false);
            alert('Le salaire doit être positif');
            return;
        }

        if (formData.hourPerWeek === '') {
            setHourPerWeekValid(false);
            alert('Le nombre d\'heures par semaine est obligatoire');
            return;
        }
        else if (formData.hourPerWeek <= 0) {
            setHourPerWeekValid(false);
            alert('Le nombre d\'heures par semaine doit être positif');
            return;
        }

        if (formData.startDate === '') {
            setStartDateValid(false);
            alert('La date de début est obligatoire');
            return;
        }
        else if (formData.startDate && !/^\d{4}-\d{2}-\d{2}$/.test(formData.startDate)) {
            setStartDateValid(false);
            alert('La date de début doit être en format YYYY-MM-DD');
            return;
        }
        else if (formData.startDate && new Date(formData.startDate) < new Date()) {
            setStartDateValid(false);
            alert('La date de début ne doit pas déjà être passée');
            return;
        }

        if (formData.duration === '') {
            setDurationValid(false);
            alert('La durée du stage est obligatoire');
            return;
        }
        else if (formData.duration && formData.duration < 1) {
            setDurationValid(false);
            alert('La durée du stage doit être d\'au moins 1 semaine');
            return;
        }

        if (formData.expireDate === '') {
            setExpireDateValid(false);
            alert('La date d\'expiration est obligatoire');
            return;
        }
        else if (formData.expireDate && !/^\d{4}-\d{2}-\d{2}$/.test(formData.expireDate)) {
            setExpireDateValid(false);
            alert('La date d\'expiration doit être en format YYYY-MM-DD');
            return;
        }
        else if (formData.expireDate && new Date(formData.expireDate) < new Date()) {
            setExpireDateValid(false);
            alert('La date d\'expiration ne doit pas déjà être passée');
            return;
        }

        saveOffer().then(r => console.log(r));
    }

    return (
        <div className="container">
            <h1 className="text-center fw-light">Création d'une nouvelle offre</h1>
            {isLoading ? <Loading/> :
            <div className="row">
                <div className="col-9 mx-auto">
                    <form className="form-group" onSubmit={handleSubmit}>
                        <label htmlFor="title" className="mt-3">Titre du poste</label>
                        <input type="text" className={`form-control ${titleValid? '': 'is-invalid'}`} id="title" placeholder="Titre" name="title" onChange={handleChanges} required/>
                        <label htmlFor="department" className="mt-3">Département</label>
                        <select className={`form-select ${departmentValid? '': 'is-invalid'}`} id="department" name="department" defaultValue="Choisir un département" onChange={handleChanges} required>
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
                        <label htmlFor="location" className="mt-3">Lieu de travail</label>
                        <input type="text" className={`form-control ${locationValid? '': 'is-invalid'}`} id="location" placeholder="Lieu de travail" name="location" onChange={handleChanges} required/>
                        <label htmlFor="description" className="mt-3">Description du poste</label>
                        <textarea className={`form-control ${descriptionValid? '': 'is-invalid'}`} id="description" rows="3" placeholder="Description du poste" name="description" onChange={handleChanges} required></textarea>
                        <label htmlFor="salary" className="mt-3">Salaire horaire</label>
                        <input type="number" step="0.01" className={`form-control ${salaryValid? '': 'is-invalid'}`} id="salary" placeholder="Salaire horaire" name="salary" onChange={handleChanges} required/>
                        <label htmlFor="hourPerWeek" className="mt-3">Nombre d'heures par semaine</label>
                        <input type="number" step="0.01" className={`form-control ${hourPerWeekValid? '': 'is-invalid'}`} id="hourPerWeek" placeholder="Nombre d'heures par semaine" name="hourPerWeek" onChange={handleChanges} required/>
                        <label htmlFor="startDate" className="mt-3">Date de début</label>
                        <input type="date" className={`form-control ${startDateValid? '': 'is-invalid'}`} id="startDate" placeholder="Date de début" name="startDate" onChange={handleChanges} required/>
                        <label htmlFor="duration"  className="mt-3">Durée du stage (en semaine)</label>
                        <input type="number" step="0.1" className={`form-control ${durationValid? '': 'is-invalid'}`} id="duration" placeholder="Durée du stage" name="duration" onChange={handleChanges} required/>
                        <label htmlFor="expireDate" className="mt-3">Date d'expiration d'affichage</label>
                        <input type="date" className={`form-control ${expireDateValid? '': 'is-invalid'}`} id="expireDate" placeholder="Date d'expiration" name="expireDate" onChange={handleChanges} required/>
                        <div className="row my-4">
                            <div className="col-4 mx-auto">
                                <button type="submit" className="btn btn-outline-ose col-12">Soumettre l'offre</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>}
        </div>
    )
}

export default NewOfferForm;