import './App.css'
import Header from "./Components/layout/Header"
import axios from "axios"
import {toast, ToastContainer} from "react-toastify"
import 'react-toastify/dist/ReactToastify.css'
import {useEffect, useState} from "react";
import User from "./model/User";
import Footer from "./Components/layout/Footer";
import Main from "./Components/layout/Main";
import {BrowserRouter} from "react-router-dom";
import {TranslationProvider} from "./Components/util/TranslationContext";

function App(){
	const [user, setUser] = useState(new User())
	let _token = localStorage.getItem('token')

	useEffect(() => {
		let tmpToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb3Vpc0BtaWNoYXVkLmNvbSIsImlhdCI6MTY5NzE0NTIyNCwiZXhwIjoxNjk3MjMxNjI0LCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiU1RVREVOVCJ9XX0.19ltpU8qICvm2ZGKTEdkLmcrhST1Z6H4QL2XPa2FM-I"
		localStorage.setItem('token', tmpToken)
		if (_token) {
			axiosInstance.defaults.headers.common['Authorization'] = tmpToken

			axiosInstance.get('/user/me')
				.then(res => {
					console.log("RES: ", res.data)
					let newUser = new User()
					newUser.init(res.data)
					newUser.isLoggedIn = true
					setUser(newUser)
				})
				.catch(err => {
					console.log(err.response.data.message)
					toast.error(err.response.data.message)
				})
		}
		console.log(user)
	}, []);

	return (
		<TranslationProvider>
			<ToastContainer
				position="top-center"
				autoClose={5000}
				hideProgressBar={false}
				newestOnTop={false}
				closeOnClick
				rtl={false}
				pauseOnFocusLoss
				pauseOnHover
				theme="colored"
			/>
			<div className="p-0">
				<BrowserRouter>
					<div className="min-vh-100 p-0 m-0 position-relative">
						<Header user={user}/>
						<Main user={user} setUser={setUser}/>
						<Footer/>
					</div>
				</BrowserRouter>
			</div>
		</TranslationProvider>
	)
}

export default App

export const baseURL = "http://localhost:8080"

export const axiosInstance = axios.create({
	baseURL: baseURL,
	headers: {
		'Content-Type': 'application/json',
		'Accept': 'application/json',
		// 'Authorisation': localStorage.getItem('token')
	}
})
