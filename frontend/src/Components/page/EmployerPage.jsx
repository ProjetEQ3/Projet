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
			.get('/employer/offer/all', {params: {employerId: 1}})
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

	const deleteOffer = (offer) => {
		axiosInstance
			.delete(`/employer/offer/${offer.id}`)
			.then((response) => {
				offers.remove(offer)
				setOffers(offers)
				toast.success("Offre de stage supprimée");
			})
			.catch((error) => {
				toast.error("Erreur lors de la suppression de l'offre de stage" + error.message);
			})
	}

	const offersDTO = [{
		id: 1,
		title: "Offre de stage 1",
		state: "Open",
		department: "Informatique",
		location: "Montréal",
		description: "Description de l'offre de stage 1",
		salary: "20",
		hourPerWeek: "40",
		startDate: "2023-01-01",
		duration: "12 semaines",
		expireDate: "2022-12-31",
	},
		{
			id: 2,
			title: "Offre de stage 2",
			state: "Pending",
			department: "Informatique",
			location: "Montréal",
			description: "Description de l'offre de stage 1",
			salary: "20",
			hourPerWeek: "40",
			startDate: "2023-01-01",
			duration: "12 semaines",
			expireDate: "2022-12-31",
		},
		{
			id: 3,
			title: "Offre de stage 3",
			state: "Submitted",
			department: "Informatique",
			location: "Montréal",
			description: "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits logiciels.",
			salary: "20",
			hourPerWeek: "40",
			startDate: "2023-01-01",
			duration: "12 semaines",
			expireDate: "2022-12-31",
		},
		{
			id: 4,
			title: "Offre de stage 4",
			state: "Expired",
			department: "Informatique",
			location: "Montréal",
			description: "Description de l'offre de stage 1",
			salary: "20",
			hourPerWeek: "40",
			startDate: "2023-01-01",
			duration: "12 semaines",
			expireDate: "2022-12-31",
		},
		{
			id: 5,
			title: "Offre de stage 5",
			state: "Taken",
			department: "Informatique",
			location: "Montréal",
			description: "Description de l'offre de stage 1",
			salary: "20",
			hourPerWeek: "40",
			startDate: "2023-01-01",
			duration: "12 semaines",
			expireDate: "2022-12-31",
		}];

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
									offersDTO.map((offer, index) => (
										<div onClick={() => setSelectedOffer(offer)}>
											<ShortJobOffer jobOffer={offer} key={index}/>
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