import {useNavigate} from "react-router-dom";
import React, {useState} from "react";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import Loading from "../util/Loading";

const RegisterForm = () => {
	const navigate = useNavigate();
	const [isLoading, setIsLoading] = useState(false);
	const [formData, setFormData] = useState({
		email: '',
		password: '',
		passwordConfirm: '',
		role: ''
	});
	const [emailValid, setEmailValid] = useState(true);
	const [passwordValid, setPasswordValid] = useState(true);
	const [passwordConfirmValid, setPasswordConfirmValid] = useState(true);

	const register = async () => {
		setIsLoading(true);
		console.log("formData", formData)
		axiosInstance.post('/user',
			{
				email: formData.email,
				password: formData.password,
				role: formData.role
			}
		).then(() => {
			setIsLoading(false)
			toast.success('Votre compte a été créé avec succès');
			navigate('/')
		}).catch(() =>
			setIsLoading(false)
		)
	}

	const handleChanges = (e) => {
		const {name, value} = e.target;
		setFormData({...formData, [name]: value.trim()});
	}

	const handleSubmit = (e) => {
		e.preventDefault();
		setEmailValid(true);
		setPasswordValid(true);
		setPasswordConfirmValid(true);

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

		register().then(r => console.log(r));
	}

	return (
		<div className="row align-item-center">
			{isLoading ? <Loading/> :

				<div className="col-9 mx-auto">
					<form onSubmit={handleSubmit}>
						<div className="form-group">
							<label htmlFor="email" className="mt-3">Email</label>
							<input type="email" className={`form-control ${emailValid? '': 'is-invalid'}`} id="email1" placeholder="Email" name="email" onChange={handleChanges}/>
							<label htmlFor="password" className="mt-3">Mot de passe</label>
							<input type="password" className={`form-control ${passwordValid? '': 'is-invalid'}`} id="password1" placeholder="Mot de passe" name="password" onChange={handleChanges}/>
							<label htmlFor="password" className="mt-3">Confirmer le mot de passe</label>
							<input type="password" className={`form-control ${passwordConfirmValid? '': 'is-invalid'}`} id="password" placeholder="Mot de passe" name="passwordConfirm" onChange={handleChanges}/>
							<label htmlFor={"role"} className="mt-3">Rôle</label>
							<select className="form-control" id="role" name="role" onChange={handleChanges}>
								<option value="MANAGER">Manager</option>
								<option value="STUDENT">Étudiant</option>
								<option value="EMPLOYER">Employeur</option>
							</select>
						</div>
						<div className="row my-4">
							<div className="col-4 mx-auto">
								<button type="submit" className="btn btn-outline-ose col-12">S'inscrire</button>
							</div>
						</div>
					</form>
				</div>}
		</div>
	)
}

export default RegisterForm
