import {Route, Routes} from "react-router-dom";
import LoginForm from "../auth/LoginForm";
import RegistrationForm from "../auth/RegistrationForm";

const AuthPage = ({user, setUser}) => {
    return (
        <Routes>
            <Route path="login" element={<LoginForm/>}/>
            <Route path="register" element={<RegistrationForm/>}/>
        </Routes>
    )
}

export default AuthPage
