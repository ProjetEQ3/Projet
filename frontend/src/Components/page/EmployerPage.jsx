import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from '@fortawesome/free-solid-svg-icons';
import ShortJobOffer from "../employer/ShortJobOffer";
import FullJobOffer from "../employer/FullJobOffer";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import FilterObjectList from "../util/FilterObjectList";
import {useTranslation} from "react-i18next";

const EmployerPage = ({user}) => {
	const {t} = useTranslation();
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
				toast.success(t('updateInternshipSuccess'));
			})
			.catch((error) => {
				toast.error(t('updateInternshipError') + error.message);
			})
	}
	const deleteOffer = (offerId) => {
		axiosInstance
			.delete(`/employer/offer/${offerId}`)
			.then((response) => {
				let updatedOffers = offers.filter((o) => o.id !== offerId)
				setOffers(updatedOffers)
				toast.success(t('deleteInternshipSuccess'));
			})
			.catch((error) => {
				toast.error(t('deleteInternshipError') + error.message);
			})
	}

	const handleNewButtonClicked = () => {
		navigate('/employer/newOffer');
	}

	const renderFilteredOffers = (filteredOffers) => {
		return (
			<div className="col-12">
				{filteredOffers.map((offer, index) => (
					<div key={index} onClick={() => setSelectedOffer(offer)}>
						<ShortJobOffer jobOffer={offer} updateJobOfferList={updateOffer} deleteOffer={() => deleteOffer(offer.id)}/>
					</div>
				))}
			</div>
		)
	}

	return (
		<div className="bg-light">
			<div className="container-fluid px-5 py-2">
				<div className="row text-center">
					<div className="col-12">
						<h2 className="text-dark fw-light">{t('hello') + user.firstName + " " + user.lastName} !</h2>
					</div>
				</div>
				<div className="row">
					<div className="col-12">
						<h3 className="text-dark fw-light d-none d-lg-block">{t('yourInternship')}</h3>
						<div className="row justify-content-around">
							<div className="order-2 order-lg-1 col-12 col-lg-6">
								<h3 className="fw-light d-lg-none d-block text-ose">{t('internshipList')}</h3>
								<FilterObjectList
									items={offers}
									attributes={['title:' + t('internshipTitle'), 'department:' + t('department'), 'jobOfferState.select:Status']}
									renderItem={renderFilteredOffers}
									selectOptions={{jobOfferState: [ "SUBMITTED", "OPEN", "PENDING", "EXPIRED", "TAKEN", "REFUSED"]}}
								/>
								{/*{
									offers.map((offer, index) => (
										<div onClick={() => setSelectedOffer(offer)}>
											<ShortJobOffer jobOffer={offer} key={offer.id} deleteOffer={() => deleteOffer(offer.id)}/>
										</div>
									))
								}*/}
								<div className="row m-2">
									<button className="btn btn-outline-ose col-12" onClick={handleNewButtonClicked}>{t('addInternship')}</button>
								</div>
							</div>
							<div className="order-1 order-lg-2 col-12 col-lg-6">
								<h3 className="fw-light d-lg-none d-block text-ose">{t('intershipDetails')}</h3>
								{selectedOffer === null ?
									<div className="row m-2">
										<div className="col-12 bg-white rounded">
											<h2 className="text-dark fw-light pt-1">{t('selectIntership')}</h2>
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