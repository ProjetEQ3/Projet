import React from "react";
import {useState} from "react";
import axios from "axios";

const StudentForms = () => {
    const [formData, setFormData] = useState({
        nom: '',
        prenom: '',
        matricule: '',
        email: '',
        password: '',
        passwordConfirm: '',
        programme: ''
    });
    const [prenomValid, setPrenomValid] = useState(true);
    const [nomValid, setNomValid] = useState(true);
    const [matriculeValid, setMatriculeValid] = useState(true);
    const [emailValid, setEmailValid] = useState(true);
    const [passwordValid, setPasswordValid] = useState(true);
    const [passwordConfirmValid, setPasswordConfirmValid] = useState(true);
    const [programmeValid, setProgrammeValid] = useState(true);

    const registerEtudiant = async () => {
        const res = await axios.post('http://localhost:8080/etudiant/register',
            {
                nom: this.nom,
                prenom: this.prenom,
                adresseCourriel: this.email,
                motDePasse: this.password,
                matricule: this.matricule,
                departement: this.programme,
            }
        )
        return res.data
    }

    const handleChanges = (e) => {
        const {name, value} = e.target;
        setFormData({...formData, [name]: value.trim()});
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        setPrenomValid(true);
        setNomValid(true);
        setMatriculeValid(true);
        setEmailValid(true);
        setPasswordValid(true);
        setPasswordConfirmValid(true);
        setProgrammeValid(true);

        if(formData.nom === ''){
            setNomValid(false)
            alert('Le nom est obligatoire');
            return;
        }
        else if(formData.nom && !/^[a-zA-Z-]+$/.test(formData.nom)){
            setNomValid(false)
            alert('Le nom doit contenir seulement des lettres');
            return;
        }

        if(formData.prenom === ''){
            setPrenomValid(false)
            alert('Le prénom est obligatoire');
            return;
        }
        else if(formData.prenom && !/^[a-zA-Z-]+$/.test(formData.prenom)){
            setPrenomValid(false)
            alert('Le prénom doit contenir seulement des lettres');
            return;
        }

        if(formData.matricule !== '' && !/^\d+$/.test(formData.matricule)){
            setMatriculeValid(false)
            alert('Le matricule doit contenir seulement des chiffres');
            return;
        }

        if(formData.email === ''){
            setEmailValid(false)
            alert('L\'email est obligatoire');
            return;
        }
        else if(formData.email && !/^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z]{2,4}$/.test(formData.email)){
            setEmailValid(false)
            alert('L\'adresse courriel doit être en format d\'adresse courriel');
            return;
        }

        if(formData.password.length < 8){
            setPasswordValid(false)
            alert('Le mot de passe doit contenir au moins 8 caractères');
            return;
        }
        else if (formData.password !== formData.passwordConfirm) {
            setPasswordValid(false)
            setPasswordConfirmValid(false)
            alert('Les deux mots de passe ne correspondent pas');
            return;
        }

        if(formData.programme === ''){
            setProgrammeValid(false)
            alert('Vous devez choisir un programme');
            return;
        }

        registerEtudiant().then(r => console.log(r));

        console.log(formData);
    }

    return (
        <div className="row align-item-center">
            <div className="col-9 mx-auto">
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="prenom" className="mt-3">Prénom</label>
                        <input type="text" className={`form-control ${prenomValid? '': 'is-invalid'}`} id="prenom" placeholder="Prénom" name="prenom" onChange={handleChanges}/>
                        <label htmlFor="nom" className="mt-3">Nom</label>
                        <input type="text" className="form-control" id="nom" placeholder="Nom" name="nom" onChange={handleChanges}/>
                        <label htmlFor="numEtudiant" className="mt-3">Matricule du Cégep</label>
                        <input type="text" className="form-control" id="matricule" placeholder="Matricule du Cégep" name="matricule" onChange={handleChanges}/>
                        <label htmlFor="email" className="mt-3">Email</label>
                        <input type="email" className="form-control" id="email" placeholder="Email" name="email" onChange={handleChanges}/>
                        <label htmlFor="password" className="mt-3">Mot de passe</label>
                        <input type="password" className="form-control" id="password" placeholder="Mot de passe" name="password" onChange={handleChanges}/>
                        <label htmlFor="password" className="mt-3">Confirmer le mot de passe</label>
                        <input type="password" className="form-control" id="password" placeholder="Mot de passe" name="passwordConfirm" onChange={handleChanges}/>
                        <label htmlFor="programme" className="mt-3">Programme d'étude</label>
                        <select className="form-control" id="programme" onChange={handleChanges} name="programme" defaultValue="Choisir un programme">

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
                            <option disabled={true}>Choisir un programme</option>
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