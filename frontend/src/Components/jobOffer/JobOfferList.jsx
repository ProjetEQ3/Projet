import React, {useState} from 'react'
import ShortJobOffer from "../student/ShortJobOffer";
import FullJobOffer from "../student/FullJobOffer";
import {useTranslation} from "react-i18next";

function JobOfferList({jobOffers}){
	const {t} = useTranslation();
	const [selectedOffer, setSelectedOffer] = useState(null);

	return (
		<div className="row justify-content-around">
			<div className="col-6">
				{
					jobOffers.length === 0 ?
						<div className="row m-2">
							<div className="col-12 bg-white rounded">
								<h2 className="text-dark fw-light pt-1">{t('noOpenInternship')}</h2>
							</div>
						</div> :

					jobOffers.map((offer, index) => (
						<div onClick={() => setSelectedOffer(offer)}>
							<ShortJobOffer jobOffer={offer} key={offer.id}/>
						</div>
					))
				}
			</div>
			<div className="col-6">
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
	)
}

export default JobOfferList;
