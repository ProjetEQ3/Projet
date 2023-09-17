import {Route, Routes} from "react-router-dom";
import LoginForm from "../auth/LoginForm";
import RegistrationForm from "../auth/RegistrationForm";
import PageNotFound from "./PageNotFound";
import RegisterForm from "../auth/RegisterForm";

const AuthPage = ({user, setUser}) => {
	return (
		<Routes>
			<Route path="login" element={<LoginForm user={user} setUser={setUser}/>}/>
			<Route path="register" element={<RegisterForm/>}/>
			<Route path="*" element={<PageNotFound/>}/>
		</Routes>
	)
}

export default AuthPage
