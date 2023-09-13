import {Route, Routes} from "react-router-dom";
import Home from "../page/Home";
import AuthPage from "../page/AuthPage";
import StudentPage from "../page/StudentPage";
import ManagerPage from "../page/ManagerPage";
import EmployerPage from "../page/EmployerPage";
import PageNotFound from "../page/PageNotFound";

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
                    path="student/*"
                    element={<StudentPage/>}
                />
                <Route
                    path="manager/*"
                    element={<ManagerPage/>}
                />
                <Route
                    path="employer/*"
                    element={<EmployerPage/>}
                />
                {<Route path="*" element={<PageNotFound/>}/>}
            </Routes>
        </main>
    )
}

export default Main
