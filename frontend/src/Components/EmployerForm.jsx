import axios from "axios";
import React, {useState} from "react";


const EmployerForm = () => {
    const [formData, setFormData] = useState({
        nom: '',
        prenom: '',
        nomOrganisme: '',
        numOrganisme: '',
        email: '',
        password: '',
        passwordConfirm: '',
    });
    const [prenomValid, setPrenomValid] = useState(true);
    const [nomValid, setNomValid] = useState(true);
    const [nomOrganismeValid, setNomOrganismeValid] = useState(true);
    const [numOrganismeValid, setNumOrganismeValid] = useState(true);
    const [emailValid, setEmailValid] = useState(true);
    const [passwordValid, setPasswordValid] = useState(true);
    const [passwordConfirmValid, setPasswordConfirmValid] = useState(true);

    const registerEmployeur = async () => {
        const res = await axios.post('/employeur/register',
            {
                nom: this.nom,
                prenom: this.prenom,
                adresseCourriel: this.adresseCourriel,
                motDePasse: this.password,
                nomOrganisme: this.nomOrganisme,
                numOrganisme: this.numOrganisme,
            })
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
        setNomOrganismeValid(true);
        setNumOrganismeValid(true);
        setEmailValid(true);
        setPasswordValid(true);
        setPasswordConfirmValid(true);

        if(formData.prenom === '' && formData.nom === ''){
            setPrenomValid(false)
            alert('Le prénom est obligatoire');
            return;
        }
        else if(formData.prenom && !/^[a-zA-Z-]+$/.test(formData.prenom) && formData.nom && !/^[a-zA-Z-]+$/.test(formData.nom)){
            setPrenomValid(false)
            alert('Le prénom doit contenir seulement des lettres');
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

        if(formData.nomOrganisme === ''){
            setNomOrganismeValid(false)
            alert('Le nom de l\'organisme est obligatoire');
            return;
        }
        else if(!/^[a-zA-Z0-9-.]+$/.test(formData.nomOrganisme)){
            setNomOrganismeValid(false)
            alert('Le nom de l\'organisme doit être en format alphanumérique');
            return;
        }

        if(formData.numOrganisme === ''){
            setNumOrganismeValid(false)
            alert('Le numéro de l\'organisme est obligatoire');
            return;
        }
        else if(!/^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}$/.test(formData.numOrganisme)){
            setNumOrganismeValid(false)
            alert('Le numéro de l\'organisme doit contenir seulement des chiffres');
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

        registerEmployeur().then(r => console.log(r));

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
                        <label htmlFor="nomOrganisme" className="mt-3">Nom de l'organisme</label>
                        <input type="text" className={`form-control ${nomOrganismeValid? '': 'is-invalid'}`} id="nomOrganisme" placeholder="Nom de l'organisme" name="nomOrganisme" onChange={handleChanges}/>
                        <label htmlFor="numOrganisme" className="mt-3">Numéro de l'organisme</label>
                        <input type="text" className={`form-control ${numOrganismeValid? '': 'is-invalid'}`} id="numOrganisme" placeholder="Numéro de l'organisme" name="numOrganisme" onChange={handleChanges}/>
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

export default EmployerForm