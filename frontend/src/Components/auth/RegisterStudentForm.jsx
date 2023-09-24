import React from "react";
import {useState} from "react";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import Loading from "../util/Loading";

const RegisterStudentForm = () => {
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const [formData, setFormData] = useState({
        lastName: '',
        firstName: '',
        matricule: '',
        email: '',
        password: '',
        passwordConfirm: '',
        department: ''
    });
    const [warnings, setWarnings] = useState({
        firstName: "",
        lastName: "",
        matricule: "",
        email: "",
        department: "",
        password: "",
        passwordConfirm: "",
    });

    const registerStudent = async () => {
        setIsLoading(true);
        axiosInstance.post('/student/register',
            {
                registerDTO: {
                    email: formData.email.toLowerCase(),
                    password: formData.password,
                    role: "STUDENT"
                },
                studentDTO:  {
                    firstName: formData.firstName,
                    lastName: formData.lastName,
                    matricule: formData.matricule,
                    department: formData.department
                }
            }
        ).then(() => {
            setIsLoading(false)
            toast.success('Votre compte a été créé avec succès');
            navigate('/auth/login')
        }).catch(() =>
            setIsLoading(false)
        )
    }

    const handleChanges = (e) => {
        const {name, value} = e.target;
        setWarnings({...warnings, [name]: ""});
        setFormData({...formData, [name]: value.trim()});
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        const validationErrors = {};

        if (formData.firstName === '') {
            validationErrors.firstName = "Le prénom est obligatoire";
        } else if (!/^[a-zA-Z- ]+$/.test(formData.firstName)) {
            validationErrors.firstName = "Le prénom doit contenir seulement des lettres";
        }

        if (formData.lastName === '') {
            validationErrors.lastName = "Le nom est obligatoire";
        } else if (!/^[a-zA-Z- ]+$/.test(formData.lastName)) {
            validationErrors.lastName = "Le nom doit contenir seulement des lettres";
        }

        if (formData.matricule !== '' && !/^\d+$/.test(formData.matricule)) {
            validationErrors.matricule = "Le matricule doit contenir seulement des chiffres";
        }

        if (formData.email === '') {
            validationErrors.email = "L'email est obligatoire";
        } else if (!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z]{2,4}$/.test(formData.email)) {
            validationErrors.email = "L'adresse courriel doit être en format d'adresse courriel";
        }

        if (formData.department === '') {
            validationErrors.department = "Vous devez choisir un programme";
        }

        if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(formData.password)) {
            validationErrors.password = "Le mot de passe doit contenir au moins 8 caractères incluant une majuscule et un chiffre";
        } else if (formData.password !== formData.passwordConfirm) {
            validationErrors.passwordConfirm = "Les deux mots de passe ne correspondent pas";
        }

        setWarnings(validationErrors);

        if (Object.keys(validationErrors).length === 0) {
            registerStudent().then(r => console.log(r));
        }
    }

    return (
        <div className="row">
            {isLoading ? (
                <Loading />
            ) : (
                <div className="col-9 mx-auto">
                    <form onSubmit={handleSubmit} className="form-group">
                        <label htmlFor="firstName" className="mt-3">
                            Prénom
                        </label>
                        <input
                            type="text"
                            className={`form-control ${warnings.firstName ? "is-invalid" : ""}`}
                            id="firstName"
                            placeholder="Prénom"
                            name="firstName"
                            onChange={handleChanges}
                        />
                        <div className="text-danger">{warnings.firstName}</div>
                        <label htmlFor="lastName" className="mt-3">
                            Nom
                        </label>
                        <input
                            type="text"
                            className={`form-control ${warnings.lastName ? "is-invalid" : ""}`}
                            id="lastName"
                            placeholder="Nom"
                            name="lastName"
                            onChange={handleChanges}
                        />
                        <div className="text-danger">{warnings.lastName}</div>
                        <label htmlFor="email" className="mt-3">
                            Email
                        </label>
                        <input
                            type="email"
                            className={`form-control ${warnings.email ? "is-invalid" : ""}`}
                            id="email"
                            placeholder="Email"
                            name="email"
                            onChange={handleChanges}
                        />
                        <div className="text-danger">{warnings.email}</div>
                        <label htmlFor="matricule" className="mt-3">
                            Matricule du Cégep
                        </label>
                        <input
                            type="text"
                            className={`form-control ${
                                warnings.matricule ? "is-invalid" : ""
                            }`}
                            id="matricule"
                            placeholder="Matricule du Cégep"
                            name="matricule"
                            onChange={handleChanges}
                        />
                        <div className="text-danger">{warnings.matricule}</div>
                        <label htmlFor="department" className="mt-3">
                            Programme d'étude
                        </label>
                        <select
                            className={`form-select ${
                                warnings.department ? "is-invalid" : ""
                            }`}
                            id="department"
                            onChange={handleChanges}
                            name="department"
                            defaultValue="Choisir un programme"
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
                            <option disabled={true}>Choisir un programme</option>
                        </select>
                        <div className="text-danger">{warnings.department}</div>
                        <label htmlFor="password" className="mt-3">
                            Mot de passe
                        </label>
                        <input
                            type="password"
                            className={`form-control ${warnings.password ? "is-invalid" : ""}`}
                            id="password"
                            placeholder="Mot de passe"
                            name="password"
                            onChange={handleChanges}
                        />
                        <div className="text-danger">{warnings.password}</div>
                        <label htmlFor="passwordConfirm" className="mt-3">
                            Confirmer le mot de passe
                        </label>
                        <input
                            type="password"
                            className={`form-control ${
                                warnings.passwordConfirm ? "is-invalid" : ""
                            }`}
                            id="passwordConfirm"
                            placeholder="Mot de passe"
                            name="passwordConfirm"
                            onChange={handleChanges}
                        />
                        <div className="text-danger">{warnings.passwordConfirm}</div>
                        <div className="row my-4">
                            <div className="col-12 col-md-4 mx-auto">
                                <button type="submit" className="btn btn-outline-ose col-12">
                                    S'inscrire
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            )}
        </div>
    );

}

export default RegisterStudentForm