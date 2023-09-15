import {useState} from "react"
import {NavLink} from "react-router-dom"
import {toast} from "react-toastify"
import {axiosInstance, baseURL} from "../../App"
import User from "../../model/User";

const LoginForm = ({user, setToken}) => {
	user = new User();
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
					setToken(response.data)
					console.log("response.data", response.data)
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
				user.isLoggedIn ? (
					<p>Logged in as {user.email} <NavLink to="../logout">Logout?</NavLink></p>
				) : (
					<>
						<h2>Se connecter</h2>
						<form id="login-form" className="m-auto col-lg-8 p-5">
							<div className="form-group row">
								<label htmlFor="colFormLabelSm" className="col-sm-3 col-form-label col-form-label-lg">Email</label>
								<div className="col-sm-9">
									<input
										type="email"
										className="form-control form-control-lg"
										id="inputEmail1"
										aria-describedby="emailHelp"
										placeholder="Entrez email"
										value={email}
										onChange={(e) => setEmail(e.target.value)}
										required
									/>
								</div>
							</div>
							<div className="form-group row">
								<label htmlFor="colFormLabelLg" className="col-sm-3 col-form-label col-form-label-lg">Password</label>
								<div className="col-sm-9">
									<input
										type="email"
										className="form-control form-control-lg"
										id="inputPassword"
										placeholder="Mot de pass"
										value={password}
										onChange={(e) => setPassword(e.target.value)}
										required
									/>
								</div>
							</div>
							<button type="submit" className="btn btn-primary my-3 mx-auto" onClick={handleSubmit}>Envoyer</button>
						</form>
					</>
				)
			}
		</>
	)
}

export default LoginForm
