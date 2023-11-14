import React from "react";
import logo from "../../logo.svg";
import {NavLink} from "react-router-dom";
import LngSelector from "../util/LngSelector";
import {useTranslation} from "react-i18next";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMoon, faSun } from '@fortawesome/free-solid-svg-icons';
import {useDarkMode} from "../../context/DarkModeContext";

function Header({user}) {
    const {t} = useTranslation()
    const { darkMode, toggleDarkMode } = useDarkMode();

    return (
        <header className="App-header">
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex align-items-center justify-content-between">
                        <NavLink className="text-decoration-none text-white col-2" to="/">
                            <div className="d-flex">
                                <img alt="Logo" className="col-4 d-none d-md-block" src={logo}/>
                                <p className="display-5 ms-2 my-3">{t('glucoseAcronym')}</p>
                            </div>
                        </NavLink>
                        <p className="lead d-none d-lg-block col-lg-5 col-md-2 ms-auto m-xl-0 text-center">{t('glucoseFullAcronym')}</p>
                        {user?.isLoggedIn ?
                            <div className="m-3 col-lg-3 col-md-4 col-6 text-center lh-1">
                                <div className="d-md-flex justify-content-around align-items-center">
                                    <div onClick={toggleDarkMode} style={{ cursor: 'pointer', padding: '5px' }}>
                                        <FontAwesomeIcon icon={darkMode ? faSun : faMoon} size="sm"/>
                                    </div>
                                    <LngSelector/>
                                    <h2 className="display-6">{user.firstName + " " + user.lastName}</h2>
                                    <div className="m-0 p-0 float-end">
                                    </div>
                                </div>
                                <a className="text-light text-decoration-none h6" href="/"
                                   onClick={() => sessionStorage.removeItem('token') } >{t('disconnect')}</a>
                            </div> :
                            <div className="align-self-start d-md-flex m-3">
                                <div onClick={toggleDarkMode} style={{ cursor: 'pointer', padding: '5px' }}>
                                    <FontAwesomeIcon icon={darkMode ? faSun : faMoon} size="sm"/>
                                </div>
                                <div className="p-0 ps-5 ms-5 ms-md-0 ps-md-5 me-md-5">
                                    <LngSelector/>
                                </div>
                                <a className="text-light text-decoration-none m-2 h6" href="/auth/login">{t('login')}</a>
                                <div className="vr"></div>
                                <a className="text-light text-decoration-none m-2 h6" href="/auth/register">{t('registerSubmit')}</a>
                            </div>
                        }
                    </div>
                </div>
            </div>
        </header>
    )
}
export default Header;