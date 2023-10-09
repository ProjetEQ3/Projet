import {useState} from "react"
import {useNavigate} from "react-router-dom"
import {toast} from "react-toastify"
import {axiosInstance} from "../../App"
import User from "../../model/User";
import Loading from "../util/Loading";
import {useTranslation} from "react-i18next";

const LoginForm = ({user, setUser}) => {
	const {t} = useTranslation()
	const navigate = useNavigate()
	const [isLoading, setIsLoading] = useState(false)
	const [formData, setFormData] = useState({
		email: '',
		password: ''
	});

	const handleSubmit = (e) => {
		e.preventDefault()
		const data = {
			email: formData.email.toLowerCase(),
			password: formData.password
		}
		if(validateUser())
			axiosInstance.post("/user/login", data)
				.then((response) => {
					let newUser = new User()
					newUser.init(response.data)
					newUser.isLoggedIn = true
					setUser(newUser)
					toast.success(t('successLogin'))
				})
				.catch((error) => {
					toast.error(t('errorLogin'))
				})
	}

	const validateUser = () => {
		let isValid = true
		if(!validateEmail()){
			toast.error(t('wrongEmail'))
			isValid = false
		}
		if(!validatePassword()){
			toast.error(t('wrongPassword'))
			isValid = false
		}
		return isValid
	}

	const validateEmail = () => {
		const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
		return emailRegex.test(formData.email)
	}

	const validatePassword = () => {
		const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
		return passwordRegex.test(formData.password)
	}

	const handleChanges = (e) => {
		const {name, value} = e.target
		setFormData({...formData, [name]: value.trim()})
	}

	return (
		<>
			{
				user?.isLoggedIn ? (
					user.role === "ROLE_STUDENT" ? navigate("/student") :
						user.role === "ROLE_EMPLOYER" ? navigate("/employer") :
							user.role === "ROLE_MANAGER" ? navigate("/manager") :
					navigate("/")
				) : (
					<>
						<div className="container mt-5">
							<h1 className="display-6 text-center mb-3">GlucOSE</h1>
							{isLoading ? <Loading/> :
							<div className="row">
								<div className="col-9 mx-auto">
								<form id="login-form" className="form-group">
									<label htmlFor="colFormLabelSm" className="mt-3">{t('email')}</label>
									<input type="email" className="form-control" placeholder={t('placeHolderEmail')} name="email" onChange={handleChanges} required/>
									<label htmlFor="colFormLabelLg" className="mt-3">{t('password')}</label>
									<input type="password" className="form-control" placeholder={t('placeHolderPassword')} name="password" onChange={handleChanges} required/>
									<div className="row col-6 mx-auto">
										<button type="submit" className="btn btn-outline-ose my-5 mx-auto" onClick={handleSubmit}>{t('loginSubmit')}</button>
									</div>
								</form>
								</div>
							</div>}
						</div>
					</>
				)
			}
		</>
	)
}

export default LoginForm
