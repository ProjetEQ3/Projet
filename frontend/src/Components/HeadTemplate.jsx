import React from "react";
import logo from "../logo.png";

function HeadTemplate() {
    return (
        <header className="App-header">
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex align-items-center justify-content-between">
                        <a className="text-decoration-none text-white col-2" href="/">
                            <div className="d-flex">
                                <img alt="Logo" className="col-4" src={logo}/>
                                <p className="display-3 ms-2 my-3">GlucOSE</p>
                            </div>
                        </a>
                        <p className="lead">Gestion Libre, Utile et Collaborative de l'Obtention de Stage Étudiant.</p>
                        <div className="align-self-start d-flex m-3">
                            <a className="text-light text-decoration-none m-2 h6" href="/connexion">Connexion</a>
                            <div className="vr"></div>
                            <a className="text-light text-decoration-none m-2 h6" href="/register">Inscription</a>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    )
}
export default HeadTemplate;