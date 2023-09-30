import FilterObjectList from "../util/FilterObjectList"
import ShortJobOffer from "./ShortJobOffer"
<<<<<<< HEAD
=======

const JobOffers = ({ offers, updateJobOfferList }) => {

  const renderFilteredOffers = (filteredOffers) => {
    return (
      <div className="col-12">
        {filteredOffers.map((offer, index) => (
          <div key={index} onClick={() => (offer)}>
            <ShortJobOffer jobOffer={offer} updateJobOfferList={updateJobOfferList} index={index}/>
          </div>
        ))}
      </div>
    )
  }

  return (
    <div className="row">
      <div className="col-12">
        <h3 className="text-dark fw-light my-5">Les offres de stages en attente de votre réponse :</h3>
        <div className="row justify-content-around">
          <FilterObjectList
            items={offers}
            attributes={['title', 'department']}
            renderItem={renderFilteredOffers}
          />
        </div>
      </div>
    </div>
  )
}

export default JobOffers;

/*import React from "react";
import ShortJobOffer from "./ShortJobOffer";
>>>>>>> origin/EQ3-13

const JobOffers = ({ offers, updateJobOfferList }) => {

  const renderFilteredOffers = (filteredOffers) => {
    return (
      <div className="col-12">
        {filteredOffers.map((offer, index) => (
          <div key={index} onClick={() => (offer)}>
            <ShortJobOffer jobOffer={offer} updateJobOfferList={updateJobOfferList} index={index}/>
          </div>
        ))}
      </div>
    )
  }

  return (
    <div className="row">
      <div className="col-12">
        <h3 className="text-dark fw-light my-5">Les offres de stages :</h3>
        <div className="row justify-content-around">
          <FilterObjectList
            items={offers}
            attributes={['title:Titre de l\'offre', 'department:Department', 'jobOfferState.select:Status']}
            renderItem={renderFilteredOffers}
            selectOptions={{jobOfferState: ['SUBMITTED', 'OPEN', 'PENDING', 'EXPIRED', 'TAKEN', 'REFUSED']}}
          />
        </div>
      </div>
    </div>
  )
}
<<<<<<< HEAD

export default JobOffers
=======
export default JobOffers;
*/
>>>>>>> origin/EQ3-13
