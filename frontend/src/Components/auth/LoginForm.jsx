import {useState} from "react"
import {useNavigate} from "react-router-dom"
import {toast} from "react-toastify"
import {axiosInstance} from "../../App"
import User from "../../model/User";

const LoginForm = ({user, setUser}) => {
	const navigate = useNavigate()
	const [email, setEmail] = useState("")
	const [password, setPassword] = useState("")

	const handleSubmit = (e) => {
		e.preventDefault()
		const data = {
			email: email,
			password: password
		}
		console.log("data", data)
		if(validateUser())
			axiosInstance.post("/user/login", data)
				.then((response) => {
					let newUser = new User()
					newUser.init(response.data)
					newUser.isLoggedIn = true
					setUser(newUser)
					console.log("response.data", newUser)
					toast.success("Vous êtes connecté")
				})
				.catch((error) => {
					console.log("ERROR: ", error)
					toast.error("Utilisateur ou mot de passe incorrect")
				})
	}

	const validateUser = () => {
		let isValid = true
		if(!validateEmail()){
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
		return emailRegex.test(email)
	}

	const validatePassword = () => {
		const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
		return passwordRegex.test(password)
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
						<form id="login-form" className="m-auto col-10">
							<div className="form-group row mx-auto">
								<label htmlFor="colFormLabelSm" className="col-sm-3 col-form-label">Email</label>
								<div className="col-12">
									<input type="email" className="form-control" id="inputEmail1"
										   aria-describedby="emailHelp" placeholder="Entrez email" value={email}
										   onChange={(e) => setEmail(e.target.value)}
										   required
									/>
								</div>
							</div>
							<div className="mt-2"></div>
							<div className="form-group row mx-auto">
								<label htmlFor="colFormLabelLg" className="col-sm-3 col-form-label">Password</label>
								<div className="col-12">
									<input
										type="password" className="form-control" id="inputPassword"
										placeholder="Mot de pass" value={password}
										onChange={(e) => setPassword(e.target.value)}
										required
									/>
								</div>
							</div>
							<div className="row col-6 mx-auto">
								<button type="submit" className="btn btn-outline-ose my-5 mx-auto" onClick={handleSubmit}>Se connecter</button>
							</div>
						</form>
					</>
				)
			}
		</>
	)
}

export default LoginForm
