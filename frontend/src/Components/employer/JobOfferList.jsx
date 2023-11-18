import FilterObjectList from "../util/FilterObjectList"
import FullJobOffer from "./FullJobOffer"
import StudentList from "./StudentList"
import {useTranslation} from "react-i18next"
import {useEffect, useState} from "react"
import ShortJobOffer from "./ShortJobOffer"
import {useNavigate} from "react-router-dom"
import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import {useSession} from "../util/SessionContext"
import {useDarkMode} from "../../context/DarkModeContext";

const JobOfferList = ({user, getNbPostulations, offersWithApplications, getOffersWithSubmittedApplications, selectedById, setSelectedById, getOffers, offers, setOffers }) => {
	const {t} = useTranslation()
	const [selectedOffer, setSelectedOffer] = useState(null)
	const [defaultSelect, setDefaultSelect] = useState('')
	const navigate = useNavigate()
	const {selectedSessionIndex} = useSession()

	const { darkMode } = useDarkMode();

	useEffect(() => {
		if (!user?.isLoggedIn) {
			navigate('/');
			return;
		}
		setSelectedOffer(null);
		getOffersWithSubmittedApplications();
	}, [user, selectedSessionIndex]);

	useEffect(() => {
		if (offers.length === 0) return;
		if (selectedById === null) return;
		let jobOffer = offers.find((offer) => offer.id === selectedById)
		getSelectedOfferState(jobOffer);
		handleSelectOffer(jobOffer)
	}, [offers, selectedById]);

	function getSelectedOfferState(jobOffer) {
		if(selectedById < 0 || selectedById === null) return ''
		if(jobOffer === null || jobOffer === undefined) return ''
		setDefaultSelect('jobOfferState.select:' + jobOffer.jobOfferState)
	}

	useEffect(() => {
		handleSessionChange()
	}, [selectedSessionIndex])

	useEffect(() => {
		if (offers.length === 0)
			getOffers();
	});

	const handleSessionChange = () => {
		setOffers([])
		setSelectedOffer(null)
		getOffers()
	}

	const handleNewButtonClicked = () => {
		navigate('/employer/newOffer')
	}

	const isOfferInSubmissions = (offerId) => {
		return offersWithApplications.some((submission) => submission.id === offerId);
	};

	const renderFilteredOffers = (filteredOffers) => {
		return (
			<div className="col-12">
				{
					filteredOffers.length !== 0 ?
						filteredOffers.map((offer, index) => {
							const boldTitle = isOfferInSubmissions(offer.id);
							const isRefused = offer.jobOfferState === "REFUSED";
							return (
								<div key={index} onClick={() => handleSelectOffer(offer)}>
									<ShortJobOffer
										jobOffer={offer}
										deleteOffer={() => deleteOffer(offer.id)}
										isBold={boldTitle || isRefused}
									/>
								</div>
							);
						}) :
						<div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded p-3`}>
							<h2 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light pt-1`}>{t('noInternship')}</h2>
						</div>
				}
			</div>
		);
	};

	const updateOffer = (offer) => {
		axiosInstance
			.put('/employer/offer', offer)
			.then((response) => {
				const updatedOffers = offers.map((element, index) => {
					if(index === response.data.id){
						console.log(response.data)
						return response.data
					}else{
						return element
					}
				})
				toast.success(t('updateInternshipSuccess'))
				setOffers(updatedOffers)
				getOffers()
				setSelectedOffer(response.data)
			})
			.catch((error) => {
				toast.error(t('updateInternshipError') + t(error.response?.data.message))
			})
	}
	const deleteOffer = (offerId) => {
		axiosInstance
			.delete(`/employer/offer/${offerId}`)
			.then(() => {
				let updatedOffers = offers.filter((o) => o.id !== offerId)
				setOffers(updatedOffers)
				toast.success(t('deleteInternshipSuccess'))
				setSelectedOffer(null)
			})
			.catch((error) => {
				toast.error(t('deleteInternshipError') + t(error.response?.data.message))
			})
	}

	const handleSelectOffer = (offer) => {
		if (offer === undefined) return;
		setSelectedById(offer.id)
		if (selectedById === selectedOffer?.id) {
			return;
		}
		if(offer.jobOfferState === "OPEN"){
			axiosInstance.get(`/employer/offer/applications/${offer.id}`)
				.then((response) => {
					offer.applications = response.data

					const updatedOffers = offers.map((o) => {
						if(o.id === offer.id){
							return offer
						}else{
							return o
						}
					})

					setOffers(updatedOffers)
				})
				.catch((error) => {
					toast.error(t('getStudentsError') + t(error.response?.data.message))
				})
		}
		setSelectedOffer(offer)
	}

	return (
		<div className="row" data-testid="job-offer-list">
			<div className="col-12">
				<h3 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light d-none d-lg-block`}>{t('yourInternship')}</h3>
				<div className="row justify-content-around">
					<div className="order-2 order-lg-1 col-12 col-lg-6">
						<h3 className="fw-light d-lg-none d-block text-ose">{t('internshipList')}</h3>
						<FilterObjectList
							items={offers}
							attributes={['title:' + t('internshipTitle'), 'department:' + t('department'), 'jobOfferState.select:Status']}
							renderItem={renderFilteredOffers}
							selectOptions={{jobOfferState: ["SUBMITTED", "OPEN", "PENDING", "EXPIRED", "TAKEN", "REFUSED"]}}
							defaultJobOfferSelect={defaultSelect}
						/>
						<div className="row m-2">
							<button className="btn btn-outline-ose col-12"
							        onClick={handleNewButtonClicked}>{t('addInternship')}</button>
						</div>
					</div>
					<div className="order-1 order-lg-2 col-12 col-lg-6">
						<h3 className="fw-light d-lg-none d-block text-ose">{t('internshipDetails')}</h3>
						{selectedOffer === null ?
							<div className="row m-2">
								<div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded`}>
									<h2 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light pt-1`}>{t('selectInternship')}</h2>
								</div>
							</div>
							:
							<>
								<FullJobOffer jobOffer={selectedOffer} updateOffer={updateOffer}/>
								{
									selectedOffer.jobOfferState === "OPEN" ?
										selectedOffer.applications != null && selectedOffer.applications.length > 0 ?
											<StudentList offer={selectedOffer} setSelectedOffer={setSelectedOffer} getNbPostulations={getNbPostulations} getOffersWithSubmittedApplications={getOffersWithSubmittedApplications}/> :
											<div><p className="display-6">{t('noStudent')}</p></div>
										: null
								}
							</>
						}
					</div>
				</div>
			</div>
		</div>
	)
}

export default JobOfferList