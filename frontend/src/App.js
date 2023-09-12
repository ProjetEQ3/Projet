import './App.css'
import HeadTemplate from "./Components/HeadTemplate"
import axios from "axios"
import {ToastContainer} from "react-toastify"
import 'react-toastify/dist/ReactToastify.css'

function App(){
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
				<HeadTemplate/>
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
