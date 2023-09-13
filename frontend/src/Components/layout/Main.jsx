import {Route, Routes} from "react-router-dom";
import Home from "../page/Home";
import AuthPage from "../page/AuthPage";
import StudentPage from "../page/StudentPage";
import ManagerPage from "../page/ManagerPage";
import EmployerPage from "../page/EmployerPage";
import PageNotFound from "../page/PageNotFound";

const Main = (user) => {
    return (
        <main className='App-main'>
            <Routes>
                <Route
                    path="/"
                    element={<Home user={user}/>}
                />
                <Route
                    path="auth/*"
                    element={<AuthPage user={user}/>}
                />
                <Route
                    path="student/*"
                    element={<StudentPage user={user}/>}
                />
                <Route
                    path="manager/*"
                    element={<ManagerPage user={user}/>}
                />
                <Route
                    path="employer/*"
                    element={<EmployerPage user={user}/>}
                />
                {<Route path="*" element={<PageNotFound/>}/>}
            </Routes>
        </main>
    )
}

export default Main
