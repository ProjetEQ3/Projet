import ShortJobOffer from "../employer/ShortJobOffer";
import FullJobOffer from "../employer/FullJobOffer";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import FilterObjectList from "../util/FilterObjectList";
import {useTranslation} from "react-i18next";
import StudentList from "../employer/StudentList";

const EmployerPage = ({user}) => {
	const [t, i18n] = useTranslation();
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
	}

	const updateOffer = (offer) => {
		console.log("updateOffer", offer)
		axiosInstance
			.put('/employer/offer', offer)
			.then((response) => {
				const offerIndex = offers.findIndex((o) => o.id === offer.id)
				const updatedOffers = offers.map((element, index) => {
					if (index === offerIndex) {
						return offer
					} else {
						return element
					}
				})
				toast.success(t('updateInternshipSuccess'));
				setOffers(updatedOffers)
				setSelectedOffer(offer)
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
				setSelectedOffer(null)
			})
			.catch((error) => {
				toast.error(t('deleteInternshipError') + error.message);
			})
	}

	const handleNewButtonClicked = () => {
		navigate('/employer/newOffer');
	}

	const handleSelectOffer = (offer) => {
		if (offer.jobOfferState === "OPEN") {
			axiosInstance.get(`/employer/offer/students/${offer.id}`)
				.then((response) => {
					offer.students = response.data;

					const updatedOffers = offers.map((o) => {
						if (o.id === offer.id) {
							return offer;
						} else {
							return o;
						}
					});

					setOffers(updatedOffers);
				})
				.catch((error) => {
					toast.error(t('getStudentsError') + error.message);
				});
		}

		setSelectedOffer(offer);
	};

	const renderFilteredOffers = (filteredOffers) => {
		return (
			<div className="col-12">
				{filteredOffers.map((offer, index) => (
					<div key={index} onClick={() => handleSelectOffer(offer)}>
						<ShortJobOffer jobOffer={offer} updateJobOfferList={updateOffer} deleteOffer={() => deleteOffer(offer.id)}/>
					</div>
				))}
			</div>
		)
	}

	return (
		<div className="bg-light">
			<div className="container-fluid px-lg-5 px-2 py-2">
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
								<div className="row m-2">
									<button className="btn btn-outline-ose col-12" onClick={handleNewButtonClicked}>{t('addInternship')}</button>
								</div>
							</div>
							<div className="order-1 order-lg-2 col-12 col-lg-6">
								<h3 className="fw-light d-lg-none d-block text-ose">{t('internshipDetails')}</h3>
								{selectedOffer === null ?
									<div className="row m-2">
										<div className="col-12 bg-white rounded">
											<h2 className="text-dark fw-light pt-1">{t('selectInternship')}</h2>
										</div>
									</div>
									:
									<>
                                        <FullJobOffer jobOffer={selectedOffer} updateOffer={updateOffer}/>
                                        {
											selectedOffer.jobOfferState === "OPEN" ?
												selectedOffer.students != null && selectedOffer.students.length > 0 ?
													<StudentList offer={selectedOffer}/> :
													<div><p className="display-6">{t('noStudent')}</p></div>
												: null
										}
									</>
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