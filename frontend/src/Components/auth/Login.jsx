import {useState} from "react"
import {NavLink} from "react-router-dom"
import {toast} from "react-toastify"
import serverIp, {axiosInstance} from "../../App"
import User from "../../model/User";

const Login = ({user, setToken}) => {
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
			axiosInstance.post(serverIp + "/auth/login", data)
			.then((response) => {
				setToken(response.data)
				toast.success("Vous êtes connecté")
			})
			.catch((error) => {
				toast.error("Utilisateur ou mot de passe incorrect")
			})
	}

	const validateUser = () => {
		let isValid = true
		if(!validateEmail()){
			toast.success('Logged in as ')
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
						<form id="login-form">
							<div className="form-group">
								<label htmlFor="inputEmail1">Adresse email</label>
								<input
									type="email"
									className="form-control"
									id="inputEmail1"
									aria-describedby="emailHelp"
									placeholder="Entrez email"
									value={email}
									onChange={(e) => setEmail(e.target.value)}
									required
								/>
							</div>
							<div className="form-group">
								<label htmlFor="inputPassword">Mot de pass</label>
								<input
									type="password"
									className="form-control"
									id="inputPassword"
									placeholder="Mot de pass"
									value={password}
									onChange={(e) => setPassword(e.target.value)}
									required
								/>
							</div>
							<button type="submit" className="btn btn-primary" onClick={handleSubmit}>Envoyer</button>
						</form>
					</>
				)
			}
		</>
	)
}

export default Login
