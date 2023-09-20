import React from 'react';

function Footer() {
    return (
        <footer className="App-footer position-absolute col-12 bottom-0">
            <div className="container-fluid">
                <div className="row">
                    <div className="col-12 d-none d-md-block text-center">
                        <p className="lead">GlucOSE - Gestion Libre, Utile et Collaborative de l'Obtention de Stage Étudiant.</p>
                        <p className="lead">© 2023 - Tous droits réservés.</p>
                    </div>
                    <div className="col-12 d-block d-md-none text-center">
                        <p className="lead"><small>GlucOSE - Gestion Libre, Utile et Collaborative de l'Obtention de Stage Étudiant.</small></p>
                        <p className="lead"><small>© 2023 - Tous droits réservés.</small></p>
                    </div>
                </div>
            </div>
        </footer>
    )
}
export default Footer;