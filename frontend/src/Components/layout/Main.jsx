import {Route, Routes} from "react-router-dom";
import Home from "../page/Home";
import AuthPage from "../page/AuthPage";
import StudentPage from "../page/StudentPage";
import ManagerPage from "../page/ManagerPage";
import EmployerPage from "../page/EmployerPage";
import PageNotFound from "../page/PageNotFound";
import NewOfferForm from "../employer/NewOfferForm";
import {useTranslation} from "react-i18next";
import {axiosInstance} from "../../App";
import {useEffect, useState} from "react";

const Main = ({user, setUser}) => {
    const {t} = useTranslation();
    const [sessions, setSessions] = useState([]);
    const fixMargin = {
        paddingBottom: "10em"
    }

    useEffect(() => {
        if (user?.isLoggedIn) {
            axiosInstance.get('/user/semesters')
                .then((response) => {
                    console.log(response.data)
                    setSessions(response.data)
                }).catch((error) => {
                    if (error.response.status === 401) return;
                });
        }
    },[user]);

    const handleUpdateAxios = (index) => {
        axiosInstance.defaults.params = {season: sessions[index].season, year: sessions[index].year}
    }


    return (
        <main style={fixMargin} className='App-main bg-light mx-auto'>
            {user?.isLoggedIn &&
                <div className="row col-12 justify-content-start pt-2">
                    <h4 className="col-6 d-flex fw-light justify-content-end m-0">
                        {t('displayedSession')}
                    </h4>
                    <select className="col-2 d-flex justify-content-start text-capitalize">
                        {
                            sessions.map((session, index) => (
                                <option value={index} onChange={() => handleUpdateAxios(index)} className="text-capitalize">{t(session.season.toLowerCase())} {session.year}</option>
                            ))
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
