import {axiosInstance} from "../../App";

const sendEmail = async(to, subject, text) => {
	axiosInstance
		.post('/user/sendemail', {to: to, subject: subject, text: text})
		.then((response) => {console.log(response)})
		.catch((error) => {console.log(error)})
}

export default sendEmail
