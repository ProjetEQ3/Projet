import React, {useState} from 'react'
import ShortJobOffer from "../student/ShortJobOffer";
import FullJobOffer from "../student/FullJobOffer";

function JobOfferList({jobOffers, user}){
	const [selectedOffer, setSelectedOffer] = useState(null);

	return (
		<div className="row justify-content-around">
			<div className="col-6">
				{
					jobOffers.length === 0 ?
						<div className="row m-2">
							<div className="col-12 bg-white rounded">
								<h2 className="text-dark fw-light pt-1">Aucune offre de stage Ouverte pour le moment</h2>
							</div>
						</div> :
						jobOffers.map((offer, index) => (
							offer.isApproved ? (
								<div onClick={() => setSelectedOffer(offer)}>
									<ShortJobOffer jobOffer={offer} key={offer.id}/>
								</div>
							) : null
						))
				}
			</div>
			<div className="col-6">
				{selectedOffer === null ?
					<div className="row m-2">
						<div className="col-12 bg-white rounded">
							<h2 className="text-dark fw-light pt-1">Sélectionner une offre de stage</h2>
						</div>
					</div>
					:
					<FullJobOffer jobOffer={selectedOffer} user={user}/>
				}
			</div>
		</div>
	)
}

export default JobOfferList;
