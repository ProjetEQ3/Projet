import React from "react";
import {useState} from "react";
import {axiosInstance} from "../App";

const StudentForm = () => {
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
        const res = await axiosInstance.post('http://localhost:8080/etudiant/register',
            {
                nom: formData.nom,
                prenom: formData.prenom,
                adresseCourriel: formData.email,
                motDePasse: formData.password,
                matricule: formData.matricule,
                departement: formData.programme,
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

        if(formData.prenom === '' && formData.nom === ''){
            setPrenomValid(false)
            alert('Le prénom est obligatoire');
            return;
        }
        else if(formData.prenom && !/^[a-zA-Z-]+$/.test(formData.prenom) && formData.nom && !/^[a-zA-Z-]+$/.test(formData.nom)){
            setPrenomValid(false)
            alert('Le nom et prénom doit contenir seulement des lettres');
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

        if(formData.programme === ''){
            setProgrammeValid(false)
            alert('Vous devez choisir un programme');
            return;
        }

        if(!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(formData.password)){
            setPasswordValid(false)
            alert('Le mot de passe doit contenir au moins 8 caractères incluant une majuscule et un chiffre');
            return;
        }
        else if (formData.password !== formData.passwordConfirm) {
            setPasswordValid(false)
            setPasswordConfirmValid(false)
            alert('Les deux mots de passe ne correspondent pas');
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
                        <label htmlFor="nom" className="mt-3">Nom</label>
                        <input type="text" className={`form-control ${nomValid? '': 'is-invalid'}`} id="nom" placeholder="Nom" name="nom" onChange={handleChanges}/>
                        <label htmlFor="prenom" className="mt-3">Prénom</label>
                        <input type="text" className={`form-control ${prenomValid? '': 'is-invalid'}`} id="prenom" placeholder="Prénom" name="prenom" onChange={handleChanges}/>
                        <label htmlFor="email" className="mt-3">Email</label>
                        <input type="email" className={`form-control ${emailValid? '': 'is-invalid'}`} id="email" placeholder="Email" name="email" onChange={handleChanges}/>
                        <label htmlFor="numEtudiant" className="mt-3">Matricule du Cégep</label>
                        <input type="text" className={`form-control ${matriculeValid? '': 'is-invalid'}`} id="matricule" placeholder="Matricule du Cégep" name="matricule" onChange={handleChanges}/>
                        <label htmlFor="programme" className="mt-3">Programme d'étude</label>
                        <select className={`form-select ${programmeValid? '': 'is-invalid'}`} id="programme" onChange={handleChanges} name="programme" defaultValue="Choisir un programme">
                            <option value="_410B0">410.B0 - Techniques de comptabilité et de gestion</option>
                            <option value="_241A1">241.A1 - Techniques de génie mécanique</option>
                            <option value="_420B0">420.B0 - Techniques de l’informatique</option>
                            <option value="_210AA">210.AA - Techniques de laboratoire : biotechnologies</option>
                            <option value="_144A1">144.A1 - Techniques de physiothérapie</option>
                            <option value="_310A0">310.A0 - Techniques policières</option>
                            <option value="_145A0">145.A0 - Techniques de santé animale</option>
                            <option value="_388A0">388.A0 - Techniques de travail social</option>
                            <option value="_140C0">140.C0 - Technologie d’analyses biomédicales</option>
                            <option value="_243C0">243.C0 - Technologie de l’électronique industrielle</option>
                            <option value="_243BA">243.BA - Technologie de l’électronique : Télécommunication</option>
                            <option value="_241D0">241.D0 - Technologie de maintenance industrielle</option>
                            <option value="_243A0">245.A0 - Technologie de systèmes ordinés</option>
                            <option value="_221B0">221.B0 - Technologie du génie civil</option>
                            <option disabled={true}>Choisir un programme</option>
                        </select>
                        <label htmlFor="password" className="mt-3">Mot de passe</label>
                        <input type="password" className={`form-control ${passwordValid? '': 'is-invalid'}`} id="password" placeholder="Mot de passe" name="password" onChange={handleChanges}/>
                        <label htmlFor="password" className="mt-3">Confirmer le mot de passe</label>
                        <input type="password" className={`form-control ${passwordConfirmValid? '': 'is-invalid'}`} id="password" placeholder="Mot de passe" name="passwordConfirm" onChange={handleChanges}/>

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

export default StudentForm