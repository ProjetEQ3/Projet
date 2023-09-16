import {Route, Routes} from "react-router-dom";
import LoginForm from "../auth/LoginForm";
import RegistrationForm from "../auth/RegistrationForm";
import PageNotFound from "./PageNotFound";

const AuthPage = ({user, setUser}) => {
	return (
		<Routes>
			<Route path="login" element={<LoginForm user={user} setUser={setUser}/>}/>
			<Route path="register" element={<RegistrationForm/>}/>
			<Route path="*" element={<PageNotFound/>}/>
		</Routes>
	)
}

export default AuthPage
