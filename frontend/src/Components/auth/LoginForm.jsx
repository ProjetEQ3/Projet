import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../../App";
import User from "../../model/User";
import Loading from "../util/Loading";
import { useTranslation } from "react-i18next";
import {toast} from "react-toastify";
import {useDarkMode} from "../../context/DarkModeContext";

const LoginForm = ({ user, setUser }) => {
	const { t, i18n } = useTranslation();
	const navigate = useNavigate();

	const [isLoading, setIsLoading] = useState(false);
	const [formData, setFormData] = useState({
		email: '',
		password: ''
	});
	const [warnings, setWarnings] = useState({
		email: '',
		password: ''
	});

	const [formAttempted, setFormAttempted] = useState(false);

	const { darkMode } = useDarkMode();

	const validateUser = () => {
		let isValid = true;
		let updatedWarnings = { ...warnings };

		if (!validateEmail()) {
			updatedWarnings.email = t('wrongEmail');
			isValid = false;
		} else {
			updatedWarnings.email = "";
		}

		if (!validatePassword()) {
			updatedWarnings.password = t('wrongPassword');
			isValid = false;
		} else {
			updatedWarnings.password = "";
		}

		setWarnings(updatedWarnings);
		return isValid;
	};

	const validateEmail = () => {
		const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
		return emailRegex.test(formData.email);
	}

	const validatePassword = () => {
		const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
		return passwordRegex.test(formData.password);
	}

	const handleChanges = (e) => {
		const { name, value } = e.target;
		setWarnings({ ...warnings, [name]: "" });
		setFormData({ ...formData, [name]: value.trim() });
	}

	useEffect(() => {
		if (formAttempted) {
			const languageChangeListener = i18n.on('languageChanged', () => {
				validateUser();
			});
			return () => {
				i18n.off('languageChanged', languageChangeListener);
			};
		}
	}, [i18n, formAttempted]);

	const handleSubmit = (e) => {
		e.preventDefault();
		setFormAttempted(true);

		if (validateUser()) {
			axiosInstance.post("/user/login", {
				email: formData.email.toLowerCase(),
				password: formData.password
			}).then((response) => {

				axiosInstance.defaults.headers.common['Authorization'] = response.data.accessToken;
				sessionStorage.setItem('token', response.data.accessToken);

				axiosInstance.get('/user/me')
					.then(res => {
						let newUser = new User()
						newUser.init(res.data)
						newUser.isLoggedIn = true
						setUser(newUser)
					})
					.catch(err => {
						toast.error(t(err.response?.data.message))
					})
			}).catch((error) => {
				if (error.response) {
					if (error.response?.status === 406) {
						setWarnings({ ...warnings, email: t('wrongEmail') });
						setWarnings({ ...warnings, password: t('wrongPassword') });
					}
				} else {
					toast.error(t('fetchError') + t(error.response?.data.message));
					setWarnings({ ...warnings, email: t('wrongEmail'), password: t('wrongPassword') });
				}
			});
		}
	}

	return (
		<>
			{user?.isLoggedIn ? (
				user.role === "ROLE_STUDENT" ? navigate("/student") :
					user.role === "ROLE_EMPLOYER" ? navigate("/employer") :
						user.role === "ROLE_MANAGER" ? navigate("/manager") :
							navigate("/")
			) : (
				<div className="container mt-5">
					<h1 className="display-6 text-center mb-3">GlucOSE</h1>
					{isLoading ? <Loading /> :
						<div className="row">
							<div className="col-9 mx-auto">
								<form id="login-form" className="form-group" onSubmit={handleSubmit}>
									<label htmlFor="email" className="mt-3">{t('email')}</label>
									<input id="email" type="email" className={`form-control ${warnings.email ? "is-invalid" : ""} ${darkMode ? "dark-input" : ""}`} placeholder={t('placeHolderEmail')} name="email" onChange={handleChanges} required />
									<div className="text-danger">{warnings.email}</div>
									<label htmlFor="password" className="mt-3">{t('password')}</label>
									<input id="password" type="password" className={`form-control ${warnings.password ? "is-invalid" : ""} ${darkMode ? "dark-input" : ""}`} placeholder={t('placeHolderPassword')} name="password" onChange={handleChanges} required />
									<div className="text-danger">{warnings.password}</div>
									<div className="row col-6 mx-auto">
										<button type="submit" className="btn btn-outline-ose my-5 mx-auto">{t('loginSubmit')}</button>
									</div>
								</form>
							</div>
						</div>
					}
				</div>
			)}
		</>
	)
}

export default LoginForm;
