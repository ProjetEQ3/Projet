import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from '@fortawesome/free-solid-svg-icons';
import ShortJobOffer from "../employer/ShortJobOffer";
import FullJobOffer from "../employer/FullJobOffer";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";

const EmployerPage = ({user}) => {
	const navigate = useNavigate();
	const [selectedOffer, setSelectedOffer] = useState(null);
	const [offers, setOffers] = useState([]);

	useEffect(() => {
		getOffers()
	}, []);

	const getOffers = () => {
		axiosInstance
			.get('/employer/offer/all', {params: {employerId: user.id}})
			.then((response) => {setOffers(response.data)})
			.catch((error) => {console.log("Error", error)});
	}

	const updateOffer = (offer) => {
		axiosInstance
			.put('/employer/offer', offer)
			.then((response) => {
				offers.map((o) => {
					if(o.id === offer.id){o = offer}
				})
				setOffers(offers)
				toast.success("Offre de stage mise à jour");
			})
			.catch((error) => {
				toast.error("Erreur lors de la mise à jour de l'offre de stage" + error.message);
			})
	}
	const deleteOffer = (offerId) => {
		console.log("Offer"+offerId)
		console.log(offers)

		axiosInstance
			.delete(`/employer/offer/${offerId}`)
			.then((response) => {
				let updatedOffers = offers.filter((o) => o.id !== offerId)
				setOffers(updatedOffers)
				toast.success("Offre de stage supprimée");
			})
			.catch((error) => {
				toast.error("Erreur lors de la suppression de l'offre de stage" + error.message);
			})
	}

	const handleNewButtonClicked = () => {
		navigate('/employer/newOffer');
	}
	return (
		<div className="bg-light">
			<div className="container-fluid px-5 py-2">
				<div className="row text-center">
					<div className="col-12">
						<h2 className="text-dark fw-light">Bonjour {user.firstName + " " + user.lastName} !</h2>
					</div>
				</div>
				<div className="row">
					<div className="col-12">
						<h3 className="text-dark fw-light">Vos offres de stages</h3>
						<div className="row justify-content-around">
							<div className="col-6">
								{
									offers.map((offer, index) => (
										<div onClick={() => setSelectedOffer(offer)}>
											<ShortJobOffer jobOffer={offer} key={offer.id} deleteOffer={() => deleteOffer(offer.id)}/>
										</div>
									))
								}
								<div className="row m-2">
									<button className="btn btn-outline-ose col-12" onClick={handleNewButtonClicked}><FontAwesomeIcon
										icon={faPlus}/></button>
								</div>
							</div>
							<div className="col-6">
								{selectedOffer === null ?
									<div className="row m-2">
										<div className="col-12 bg-white rounded">
											<h2 className="text-dark fw-light pt-1">Sélectionnez une offre de stage</h2>
										</div>
									</div>
									:
									<FullJobOffer jobOffer={selectedOffer}/>
								}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	)
}

export default EmployerPage;