import React from "react";
import logo from "../../logo.png";
import {NavLink} from "react-router-dom";

function Header({user}) {
    return (
        <header className="App-header">
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex align-items-center justify-content-between">
                        <NavLink className="text-decoration-none text-white col-2" to="/">
                            <div className="d-flex">
                                <img alt="Logo" className="col-4" src={logo}/>
                                <p className="display-3 ms-2 my-3">GlucOSE</p>
                            </div>
                        </NavLink>
                        <p className="lead">Gestion Libre, Utile et Collaborative de l'Obtention de Stage Étudiant.</p>
                        {user?.isLoggedIn ?
                            <div className="m-3 text-center lh-1">
                                <h2 className="display-6">{user.firstName + " " + user.lastName}</h2>
                                <a className="text-light text-decoration-none h6" href="/">Se Déconnecter</a>
                            </div> :
                            <div className="align-self-start d-flex m-3">
                                <a className="text-light text-decoration-none m-2 h6" href="/auth/login">Connexion</a>
                                <div className="vr"></div>
                                <a className="text-light text-decoration-none m-2 h6" href="/auth/register">Inscription</a>
                            </div>
                        }
                    </div>
                </div>
            </div>
        </header>
    )
}
export default Header;