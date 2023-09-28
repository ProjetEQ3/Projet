import React from "react";
import logo from "../../logo.png";
import {NavLink} from "react-router-dom";
import LngSelector from "../util/LngSelector";
import {useTranslation} from "react-i18next";

function Header({user}) {
    const {t} = useTranslation()
    return (
        <header className="App-header">
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex align-items-center justify-content-between">
                        <NavLink className="text-decoration-none text-white col-2" to="/">
                            <div className="d-flex">
                                <img alt="Logo" className="col-4 d-none d-md-block" src={logo}/>
                                <p className="display-3 ms-2 my-3">{t('glucoseAcronym')}</p>
                            </div>
                        </NavLink>
                        <p className="lead d-none d-md-block">{t('glucoseFullAcronym')}</p>
                        {user?.isLoggedIn ?
                            <div className="m-3 text-center lh-1">
                                <LngSelector/>
                                <h2 className="display-6">{user.firstName + " " + user.lastName}</h2>
                                <a className="text-light text-decoration-none h6" href="/">{t('disconnect')}</a>
                            </div> :
                            <div className="align-self-start d-flex m-3">
                                <LngSelector/>
                                <a className="text-light text-decoration-none m-2 h6" href="/auth/login">{t('login')}</a>
                                <div className="vr"></div>
                                <a className="text-light text-decoration-none m-2 h6" href="/auth/register">{t('register')}</a>
                            </div>
                        }
                    </div>
                </div>
            </div>
        </header>
    )
}
export default Header;