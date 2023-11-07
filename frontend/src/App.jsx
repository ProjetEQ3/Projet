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
import {SessionProvider} from "./Components/util/SessionContext";
import {useTranslation} from "react-i18next";

function App(){
	const [t] = useTranslation();
	const [user, setUser] = useState(new User())
	let _token = sessionStorage.getItem('token')

	useEffect( () => {
		if (_token) {
			axiosInstance.defaults.headers.common['Authorization'] = _token

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
		}
	}, [_token])

	useEffect(() => {
		if (user.role === 'ROLE_MANAGER') {
			axiosInstance.defaults.params['department'] = user.department
		}
	}, [user])

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
			<SessionProvider user={user}>
				<div className="p-0">
					<BrowserRouter>
						<div className="min-vh-100 p-0 m-0 position-relative">
							<Header user={user}/>
							<Main user={user} setUser={setUser}/>
							<Footer/>
						</div>
					</BrowserRouter>
				</div>
			</SessionProvider>
		</TranslationProvider>
	)
}

export default App

export const baseURL = "http://localhost:8080"

export const axiosInstance = axios.create({
	baseURL: baseURL,
	headers: {
		'Content-Type': 'application/json',
		'Accept': 'application/json'
	},
	params: {}
})
