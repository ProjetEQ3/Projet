import {axiosInstance} from "../../App";

const sendEmail = async(to, subject, text) => {
	axiosInstance
		.post('/user/sendemail', {to: to, subject: subject, text: text})
		.then((response) => {console.log("response:", response)})
		.catch((error) => {console.log("error: ", error)})
}

export default sendEmail
