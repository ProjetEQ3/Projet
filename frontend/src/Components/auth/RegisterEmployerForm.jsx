import React, {useState} from "react";
import {axiosInstance} from "../../App";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import Loading from "../util/Loading";


const RegisterEmployerForm = () => {
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const [formData, setFormData] = useState({
        lastName: '',
        firstName: '',
        organisationName: '',
        organisationPhone: '',
        email: '',
        password: '',
        passwordConfirm: '',
    });
    const [warnings, setWarnings] = useState({
        firstName: "",
        lastName: "",
        organisationName: "",
        organisationPhone: "",
        email: "",
        password: "",
        passwordConfirm: "",
    });

    const registerEmployer = async () => {
        setIsLoading(true)
        axiosInstance.post('/employer/register',
            {
                    registerDTO: {
                        email: formData.email.toLowerCase(),
                        password: formData.password,
                        role: "EMPLOYER"
                    },
                    employerDTO: {
                        firstName: formData.firstName,
                        lastName: formData.lastName,
                        organisationName: formData.organisationName,
                        organisationPhone: formData.organisationPhone
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

        if(formData.firstName === ''){
            validationErrors.firstName = "Le prénom est obligatoire";
        }
        else if (!/^[a-zA-Z-]+$/.test(formData.firstName)) {
            validationErrors.firstName = "Le prénom doit contenir seulement des lettres";
        }

        if(formData.lastName === ''){
            validationErrors.lastName = "Le nom est obligatoire";
        }
        else if(formData.lastName && !/^[a-zA-Z-]+$/.test(formData.lastName)){
            validationErrors.lastName = "Le nom doit contenir seulement des lettres";
        }

        if(formData.email === ''){
            validationErrors.email = "L'email est obligatoire";
        }
        else if(formData.email && !/^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z]{2,4}$/.test(formData.email)){
            validationErrors.email = "L'adresse courriel doit être en format d'adresse courriel";
        }

        if(formData.organisationName === ''){
            validationErrors.organisationName = "Le nom de l'organisme est obligatoire";
        }
        else if(!/^[a-zA-Z0-9-. ]+$/.test(formData.organisationName)){
            validationErrors.organisationName = "Le nom de l'organisme doit être en format alphanumérique";
        }

        if(formData.organisationPhone === ''){
            validationErrors.organisationPhone = "Le numéro de l'organisme est obligatoire";
        }
        else if(!/^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}$/.test(formData.organisationPhone)){
            validationErrors.organisationPhone = "Le numéro de l'organisme doit suivre un format de numéro de téléphone. Exemple: 514-123-4567";
        }

        if(formData.password.length < 8){
            validationErrors.password = "Le mot de passe doit contenir au moins 8 caractères";
        }
        else if (formData.password !== formData.passwordConfirm) {
            validationErrors.passwordConfirm = "Les deux mots de passe ne correspondent pas";
        }

        setWarnings(validationErrors);

        if (Object.keys(validationErrors).length === 0) {
            registerEmployer().then((r) => console.log(r));
        }
    }

    return (
        <div className="row">
            {isLoading ? (
                <Loading />
            ) : (
                <div className="col-9 mx-auto">
                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="firstName" className="mt-3">
                                Prénom
                            </label>
                            <input
                                type="text"
                                className={`form-control ${
                                    warnings.firstName ? "is-invalid" : ""
                                }`}
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
                                className={`form-control ${
                                    warnings.lastName ? "is-invalid" : ""
                                }`}
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
                                className={`form-control ${
                                    warnings.email ? "is-invalid" : ""
                                }`}
                                id="email"
                                placeholder="Email"
                                name="email"
                                onChange={handleChanges}
                            />
                            <div className="text-danger">{warnings.email}</div>
                            <label htmlFor="organisationName" className="mt-3">
                                Nom de l'organisme
                            </label>
                            <input
                                type="text"
                                className={`form-control ${
                                    warnings.organisationName ? "is-invalid" : ""
                                }`}
                                id="organisationName"
                                placeholder="Nom de l'organisme"
                                name="organisationName"
                                onChange={handleChanges}
                            />
                            <div className="text-danger">
                                {warnings.organisationName}
                            </div>
                            <label htmlFor="organisationPhone" className="mt-3">
                                Numéro de téléphone
                            </label>
                            <input
                                type="text"
                                className={`form-control ${
                                    warnings.organisationPhone ? "is-invalid" : ""
                                }`}
                                id="organisationPhone"
                                placeholder="Numéro de téléphone"
                                name="organisationPhone"
                                onChange={handleChanges}
                            />
                            <div className="text-danger">
                                {warnings.organisationPhone}
                            </div>
                            <label htmlFor="password" className="mt-3">
                                Mot de passe
                            </label>
                            <input
                                type="password"
                                className={`form-control ${
                                    warnings.password ? "is-invalid" : ""
                                }`}
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
                                placeholder="Confirmer le mot de passe"
                                name="passwordConfirm"
                                onChange={handleChanges}
                            />
                            <div className="text-danger">
                                {warnings.passwordConfirm}
                            </div>
                        </div>
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

export default RegisterEmployerForm