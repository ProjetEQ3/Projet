import './App.css'
import Header from "./Components/layout/Header"
import axios from "axios"
import {ToastContainer} from "react-toastify"
import 'react-toastify/dist/ReactToastify.css'
import {useState} from "react";
import User from "./model/User";
import Footer from "./Components/layout/Footer";
import Main from "./Components/layout/Main";
import {BrowserRouter} from "react-router-dom";

function App(){
	const [user, setUser] = useState(new User())
	return (
		<>
			<ToastContainer
				position="top-left"
				autoClose={5000}
				hideProgressBar={false}
				newestOnTop={false}
				closeOnClick
				rtl={false}
				pauseOnFocusLoss
				pauseOnHover
				theme="colored"
			/>
			<div className="App">
				<BrowserRouter>
					<Header user={user}/>
					<Main/>
					<Footer/>
				</BrowserRouter>
			</div>
		</>
	)
}

export default App

export const baseURL = "http://localhost:8080"

export const axiosInstance = axios.create({
	baseURL: baseURL,
	headers: {
		'Content-Type': 'application/json',
		'Accept': 'application/json'
	}
})
