import logo from './logo.svg';
import './App.css';
import RegistrationForm from "./Components/RegistrationForm";
import HeadTemplate from "./Components/HeadTemplate";
import axios from "axios";


function App() {

  return (
    <div className="App">
        <HeadTemplate/>
    </div>
  );
}

export default App;
export const baseURL = "http://localhost:8080";
export const axiosInstance = axios.create({
    baseURL: baseURL,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    }
});