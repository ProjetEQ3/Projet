import {Route, Routes} from "react-router-dom";
import Home from "../page/Home";
import AuthPage from "../page/AuthPage";
import StudentPage from "../page/StudentPage";
import ManagerPage from "../page/ManagerPage";
import EmployerPage from "../page/EmployerPage";
import PageNotFound from "../page/PageNotFound";
import NewOfferForm from "../employer/NewOfferForm";

const Main = ({user, setUser}) => {
    const fixMargin = {
        paddingBottom: "10em"
    }

    return (
        <main style={fixMargin} className='App-main bg-light mx-auto'>
            <Routes>
                <Route
                    path="/"
                    element={<Home user={user}/>}
                />
                <Route
                    path="auth/*"
                    element={<AuthPage user={user} setUser={setUser}/>}
                />
                <Route
                    path="student/*"
                    element={<StudentPage user={user} setUser={setUser}/>}
                />
                <Route
                    path="manager/*"
                    element={<ManagerPage user={user}/>}
                />
                <Route
                    path="employer/*"
                    element={<EmployerPage user={user}/>}
                />
                <Route
                    path="employer/newOffer"
                    element={<NewOfferForm user={user}/>}
                />
                {<Route path="*" element={<PageNotFound/>}/>}
            </Routes>
        </main>
    )
}

export default Main
