import {useState} from "react"
import {NavLink} from "react-router-dom"
import axios from "axios"
import {toast} from "react-toastify"
import serverIp from "../App"

const Login = ({user, setToken}) => {
	const [email, setEmail] = useState("")
	const [password, setPassword] = useState("")

	const handleSubmit = (e) => {
		e.preventDefault()
		const data = {
			email: email,
			password: password
		}
		if(!validateUser())
			return
		axios.post(serverIp + "/auth/login", data)
			.then((response) => {
				if(response?.data["message"] === "success"){
					toast.success("Login successful")
					setToken(response.data["body"])
				}else{
					toast.error("Unknown error")
				}
			})
			.catch((error) => {
				error?.response?.data?.errors?.length > 0
					? error.response.data.errors.map((error) => {
						toast.error(error)
					})
					: toast.error("Login failed")
			})
	}

	const validateUser = () => {
		if(validateEmail()){
			toast.error("Invalid email")
			return false
		}
		if(validatePassword()){
			toast.error("Invalid password. " +
				"Password must be at least 8 characters long " +
				"and contain at least one uppercase letter, one lowercase letter and one number"
			)
			return false
		}
		return true
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
						<h2>Login</h2>
						<form id="login-form">
							<label htmlFor="email">Email</label>
							<input
								type="email"
								id="email"
								value={email}
								onChange={(e) => setEmail(e.target.value)}
								required
							/>
							<label htmlFor="password">Password</label>
							<input
								type="password"
								id="password"
								value={password}
								onChange={(e) => setPassword(e.target.value)}
								required
							/>
							<button onClick={handleSubmit}>Login</button>
						</form>
					</>
				)
			}
		</>
	)
}

export default Login
