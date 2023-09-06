import React from "react";

const StudentForms = () => {
    return (
        <div className="row align-item-center">
            <div className="col-9 mx-auto">
                <form>
                    <div className="form-group">
                        <label htmlFor="nom" className="mt-3">Nom</label>
                        <input type="text" className="form-control" id="nom" placeholder="Nom"/>
                        <label htmlFor="prenom" className="mt-3">Prénom</label>
                        <input type="text" className="form-control" id="prenom" placeholder="Prénom"/>
                        <label htmlFor="numEtudiant" className="mt-3">Matricule du Cégep</label>
                        <input type="text" className="form-control" id="matricule" placeholder="Matricule du Cégep"/>
                        <label htmlFor="email" className="mt-3">Email</label>
                        <input type="email" className="form-control" id="email" placeholder="Email"/>
                        <label htmlFor="password" className="mt-3">Mot de passe</label>
                        <input type="password" className="form-control" id="password" placeholder="Mot de passe"/>
                        <label htmlFor="password" className="mt-3">Confirmer le mot de passe</label>
                        <input type="password" className="form-control" id="password" placeholder="Mot de passe"/>
                        <label htmlFor="programme" className="mt-3">Programme d'étude</label>
                        <select className="form-control" id="programme">
                            <option>Techniques de comptabilité et de gestion</option>
                            <option>Techniques de génie mécanique</option>
                            <option>Techniques de l’informatique</option>
                            <option>Techniques de laboratoire : biotechnologies</option>
                            <option>Techniques de physiothérapie</option>
                            <option>Techniques policières</option>
                            <option>Techniques de santé animale</option>
                            <option>Techniques de travail social</option>
                            <option>Technologie d’analyses biomédicales</option>
                            <option>Technologie de l’électronique industrielle</option>
                            <option>Technologie de l’électronique : Télécommunication</option>
                            <option>Technologie de maintenance industrielle</option>
                            <option>Technologie de systèmes ordinés</option>
                            <option>Technologie du génie civil</option>
                        </select>
                    </div>
                    <div className="row my-4">
                        <div className="col-4 mx-auto">
                            <button type="submit" className="btn btn-outline-ose col-12">S'inscrire</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default StudentForms