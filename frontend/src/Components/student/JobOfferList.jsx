import React, {useEffect, useState} from 'react'
import ShortJobOffer from "./ShortJobOffer"
import FullJobOffer from "./FullJobOffer"
import {useTranslation} from "react-i18next"
import {useDarkMode} from "../../context/DarkModeContext";

function JobOfferList({jobOffers, user, setJobOffers, selectedById, handleViewJobOffer}) {
    const {t} = useTranslation();
    const [selectedOffer, setSelectedOffer] = useState(null);

	const { darkMode } = useDarkMode();

	const updatedOffer = (jobOffer) => {
		setSelectedOffer(jobOffer)
		const updatedOffers = jobOffers.map((offer) => offer.id === jobOffer.id ? jobOffer : offer)
		setJobOffers(updatedOffers)
	}

	const handleClick = (offer) => {
		setSelectedOffer(offer);
		handleViewJobOffer(offer);
	}

	useEffect(() => {
		if (jobOffers.length === 0) return;
		if (selectedById === null) return;
		setSelectedOffer(jobOffers.find((offer) => offer.id === selectedById));
	}, [selectedById]);

	return (
		<div className="row justify-content-around mx-2">
			<div className="col-lg-6 col-12 order-2 order-lg-1">
				{
					jobOffers.length === 0 ?
						<div className="row m-2">
							<div className="col-12 bg-white rounded">
								<h2 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light pt-1`}>{t('noOpenInternship')}</h2>
							</div>
						</div> :
						jobOffers.map((offer, index) => (
							offer.jobOfferState === "OPEN" ? (
								<div key={offer.id} onClick={() => handleClick(offer)}>
									<ShortJobOffer user={user} jobOffer={offer} key={offer.id} />
								</div>
							) : null
						))
				}
			</div>
			<div className="col-lg-6 col-12 order-1 order-lg-2">
				{selectedOffer === null ?
					<div className="row m-2">
						<div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded`}>
							<h2 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light pt-1`}>{t('selectInternship')}</h2>
						</div>
					</div>
					: <FullJobOffer user={user} jobOffer={selectedOffer} updatedOffer={updatedOffer}/>
				}
			</div>
		</div>
	)
}

export default JobOfferList