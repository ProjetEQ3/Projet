import React, { useState, useEffect } from "react";
import { axiosInstance } from "../../App";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import Loading from "../util/Loading";
import { useTranslation } from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";

const RegisterStudentForm = () => {
    const { t, i18n } = useTranslation();
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

    const [formAttempted, setFormAttempted] = useState(false);

    const { darkMode } = useDarkMode();

    const validateForm = () => {
        const validationErrors = {};

        if (formData.firstName === '') {
            validationErrors.firstName = t('firstNameRequired');
        } else if (!/^[a-zA-Z- \u00C0-\u017F]+$/.test(formData.firstName)) {
            validationErrors.firstName = t('firstNameInvalid');
        }

        if (formData.lastName === '') {
            validationErrors.lastName = t('lastNameRequired');
        } else if (!/^[a-zA-Z- \u00C0-\u017F]+$/.test(formData.lastName)) {
            validationErrors.lastName = t('lastNameInvalid');
        }

        if (formData.matricule === '') {
            validationErrors.matricule = t('matriculeRequired');
        } else if (!/^\d+$/.test(formData.matricule)) {
            validationErrors.matricule = t('matriculeInvalid');
        }

        if (formData.email === '') {
            validationErrors.email = t('emailRequired');
        } else if (!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z]{2,4}$/.test(formData.email)) {
            validationErrors.email = t('emailInvalid');
        }

        if (formData.department === '') {
            validationErrors.department = t('departmentStudentRequired');
        }

        if (formData.password === '') {
            validationErrors.password = t('passwordRequired');
        } else if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(formData.password)) {
            validationErrors.password = t('passwordInvalid');
        } else if (formData.password !== formData.passwordConfirm) {
            validationErrors.passwordConfirm = t('passwordConfirmInvalid');
        }

        return validationErrors;
    }

    useEffect(() => {
        if (formAttempted) {
            const languageChangeListener = i18n.on('languageChanged', () => {
                setWarnings(validateForm());
            });

            return () => {
                i18n.off('languageChanged', languageChangeListener);
            };
        }
    }, [i18n, formAttempted]);

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
            toast.success(t('successRegister'));
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
        setFormAttempted(true);
        const validationErrors = validateForm();
        setWarnings(validationErrors);

        if (Object.keys(validationErrors).length === 0) {
            registerStudent();
        }
    }

    return (
        <div className="row">
            {isLoading ? (
                <Loading />
            ) : (
                <div className="col-9 mx-auto">
                    <form onSubmit={handleSubmit} className="form-group">
                        <label htmlFor="firstName" className="mt-3">{t('firstName')}</label>
                        <input type="text" className={`form-control ${warnings.firstName ? "is-invalid" : ""} ${darkMode ? "dark-input" : ""}`}
                               id="firstName" placeholder={t('placeHolderFirstName')} name="firstName" onChange={handleChanges}/>
                        <div className="text-danger">{warnings.firstName}</div>
                        <label htmlFor="lastName" className="mt-3">{t('lastName')}</label>
                        <input type="text" className={`form-control ${warnings.lastName ? "is-invalid" : ""} ${darkMode ? "dark-input" : ""}`}
                               id="lastName" placeholder={t('placeHolderLastName')} name="lastName" onChange={handleChanges}/>
                        <div className="text-danger">{warnings.lastName}</div>
                        <label htmlFor="email" className="mt-3">{t('email')}</label>
                        <input type="email" className={`form-control ${warnings.email ? "is-invalid" : ""} ${darkMode ? "dark-input" : ""}`} id="email"
                               placeholder={t('placeHolderEmail')} name="email" onChange={handleChanges}/>
                        <div className="text-danger">{warnings.email}</div>
                        <label htmlFor="matricule" className="mt-3">{t('matricule')}</label>
                        <input type="text" className={`form-control ${warnings.matricule ? "is-invalid" : ""} ${darkMode ? "dark-input" : ""}`}
                               id="matricule" placeholder={t('placeHolderMatricule')} name="matricule" onChange={handleChanges}/>
                        <div className="text-danger">{warnings.matricule}</div>
                        <label htmlFor="department" className="mt-3">{t('departmentStudent')}</label>
                        <select className={`form-select ${warnings.department ? "is-invalid" : ""} ${darkMode ? "dark-input" : ""}`} id="department"
                                onChange={handleChanges} name="department" defaultValue="default">
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
                            <option className="clickable" value="_241D0">{t('_241D0')}</option>
                            <option className="clickable" value="_243A0">{t('_243A0')}</option>
                            <option className="clickable" value="_221B0">{t('_221B0')}</option>
                            <option className="clickable" value="default" disabled={true}>{t('placeHolderDepartmentStudent')}</option>
                        </select>
                        <div className="text-danger">{warnings.department}</div>
                        <label htmlFor="password" className="mt-3">{t('password')}</label>
                        <input type="password" className={`form-control ${warnings.password ? "is-invalid" : ""} ${darkMode ? "dark-input" : ""}`}
                               id="password" placeholder={t('placeHolderPassword')} name="password" onChange={handleChanges}/>
                        <div className="text-danger">{warnings.password}</div>
                        <label htmlFor="passwordConfirm" className="mt-3">{t('passwordConfirm')}</label>
                        <input type="password" className={`form-control ${warnings.passwordConfirm ? "is-invalid" : ""} ${darkMode ? "dark-input" : ""}`}
                               id="passwordConfirm" placeholder={t('placeHolderPasswordConfirm')} name="passwordConfirm" onChange={handleChanges}/>
                        <div className="text-danger">{warnings.passwordConfirm}</div>
                        <div className="row my-4">
                            <div className="col-12 col-md-4 mx-auto">
                                <button type="submit" className="btn btn-outline-ose col-12">{t('registerSubmit')}</button>
                            </div>
                        </div>
                    </form>
                </div>
            )}
        </div>
    );

}

export default RegisterStudentForm