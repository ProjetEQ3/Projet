import {useState} from "react"
import {useNavigate} from "react-router-dom"
import {toast} from "react-toastify"
import {axiosInstance} from "../../App"
import User from "../../model/User";
import Loading from "../util/Loading";

const LoginForm = ({user, setUser}) => {
	const navigate = useNavigate()
	const [isLoading, setIsLoading] = useState(false)
	const [formData, setFormData] = useState({
		email: '',
		password: ''
	});

	const handleSubmit = (e) => {
		e.preventDefault()
		const data = {
			email: formData.email,
			password: formData.password
		}
		if(validateUser())
			axiosInstance.post("/user/login", data)
				.then((response) => {
					let newUser = new User()
					newUser.init(response.data)
					newUser.isLoggedIn = true
					setUser(newUser)
					toast.success("Vous êtes connecté")
				})
				.catch((error) => {
					toast.error("Utilisateur ou mot de passe incorrect")
				})
	}

	const validateUser = () => {
		let isValid = true
		if(!validateEmail()){
			toast.error('L\'adresse courriel doit être en format d\'adresse courriel');
			isValid = false
		}
		if(!validatePassword()){
			toast.error("Mot de passe invalide. " +
				"Le mot de passe doit comporter au moins 8 caractères " +
				"et contenir au moins une lettre majuscule, une lettre minuscule et un chiffre"
			)
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
						<div className="container">
							<h1 className="display-6 text-center mb-3">GlucOSE</h1>
							{isLoading ? <Loading/> :
							<div className="row">
								<div className="col-9 mx-auto">
								<form id="login-form" className="form-group">
									<label htmlFor="colFormLabelSm" className="col-sm-3 mt-3">Email</label>
									<input type="email" className="form-control" placeholder="Entrez email" name="email" onChange={handleChanges} required/>
									<label htmlFor="colFormLabelLg" className="col-sm-3 mt-3">Password</label>
									<input type="password" className="form-control" placeholder="Mot de passe" name="password" onChange={handleChanges} required/>
									<div className="row col-6 mx-auto">
										<button type="submit" className="btn btn-outline-ose my-5 mx-auto" onClick={handleSubmit}>Se connecter</button>
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
