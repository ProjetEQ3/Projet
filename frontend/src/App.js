import './App.css'
import axios from "axios"
import Footer from "./Components/layout/Footer";
import {BrowserRouter} from "react-router-dom";
import Header from "./Components/layout/Header";
import Main from "./Components/layout/Main";

function App(){
	return (
		<BrowserRouter>
			<Header/>
			<Main/>
			<Footer/>
		</BrowserRouter>
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
