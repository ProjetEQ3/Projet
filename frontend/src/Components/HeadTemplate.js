import React from "react";

function HeadTemplate() {
    return (
        <header className="App-header">
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex align-items-center justify-content-between">
                        <p className="display-3 mx-5 my-3">GlucOSE</p>
                        <p className="lead">Gestion Libre, Utile et Collaborative de l'Obtention de Stage Étudiant.</p>
                        <div className="align-self-start d-flex m-3">
                            <a className="text-light text-decoration-none m-2 h6" href="/connexion">Connexion</a>
                            <div className="vr"></div>
                            <a className="text-light text-decoration-none m-2 h6" href="/inscription">Inscription</a>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    )
}
export default HeadTemplate;