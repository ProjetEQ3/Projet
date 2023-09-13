import {Route, Routes} from "react-router-dom";
import Home from "../page/Home";
import AuthPage from "../page/AuthPage";
import StudentPage from "../page/StudentPage";
import ManagerPage from "../page/ManagerPage";
import EmployerPage from "../employer/EmployerPage";

const Main = () => {
    return (
        <main className='App-main'>
            <Routes>
                <Route
                    path="/"
                    element={<Home/>}
                />
                <Route
                    path="auth/*"
                    element={<AuthPage/>}
                />
                <Route
                    path="etudiant/*"
                    element={<StudentPage/>}
                />
                <Route
                    path="manager/*"
                    element={<ManagerPage/>}
                />
                <Route
                    path="clientManager"
                    element={<EmployerPage/>}
                />
                {/*<Route path="*" element={<PageNotFound/>}/>*/}
            </Routes>
        </main>
    )
}

export default Main
