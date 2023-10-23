import {Route, Routes} from "react-router-dom";
import Home from "../page/Home";
import AuthPage from "../page/AuthPage";
import StudentPage from "../page/StudentPage";
import ManagerPage from "../page/ManagerPage";
import EmployerPage from "../page/EmployerPage";
import PageNotFound from "../page/PageNotFound";
import NewOfferForm from "../employer/NewOfferForm";
import {useTranslation} from "react-i18next";
import {useSession} from "../util/SessionContext";

const Main = ({user, setUser}) => {
    const { t } = useTranslation();
    const { sessions, selectedSessionIndex, updateSession } = useSession();

    const fixMargin = {
        paddingBottom: "10em",
    };


    return (
        <main style={fixMargin} className='App-main bg-light mx-auto'>
            {user?.isLoggedIn &&
                <div className="row col-12 justify-content-start pt-2">
                    <h4 className="col-6 d-flex fw-light justify-content-end m-0">
                        {t('displayedSession')}
                    </h4>
                    <select className="col-2 d-flex justify-content-start text-capitalize"
                        onChange={(e) => updateSession(e.target.value)}
                        defaultValue={selectedSessionIndex}>
                        {
                            sessions.map((session, index) => (
                            <option key={index} value={index} className="text-capitalize">
                                {t(session.season.toLowerCase())} {session.year}
                            </option>))
                        }
                    </select>

                </div>
            }
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
