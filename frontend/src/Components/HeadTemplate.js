import React from "react";

function HeadTemplate() {
    return (
        <header className="App-header">
            <div className="container">
                <div className="row">
                    <div className="d-flex align-items-center justify-content-between">
                        <p className="display-3">GlucOSE</p>
                        <p className="lead">Gestion Libre, Utile et Collaborative de l'Obtention de Stage Étudiant.</p>
                        <div className="btn-group">
                            <a className="text-ose btn">Connexion</a>
                            <button className="text-ose btn">Inscription</button>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    )
}
export default HeadTemplate;