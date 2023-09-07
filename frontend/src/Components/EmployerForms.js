import axios from "axios";


const EmployerForms = () => {

    const registerEmployeur = async () => {
        const res = await axios.post('http://localhost:8080/employeur/register',
            {
                nom: this.nom,
                prenom: this.prenom,
                adresseCourriel: this.adresseCourriel,
                motDePasse: this.password,
                nomOrganisme: this.nomOrganisme,
                numEntreprise: this.numEntreprise,
            })
        return res.data
    }


    return (
        <div>
            <h1>Employer Forms</h1>
        </div>
    )
}

export default EmployerForms