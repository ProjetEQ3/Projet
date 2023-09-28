import FilterObjectList from "../util/FilterObjectList"
import ShortJobOffer from "./ShortJobOffer"

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
            attributes={['title', 'department', 'jobOfferState.select']}
            renderItem={renderFilteredOffers}
            selectOptions={{
              jobOfferState: ['Open', 'Closed', 'Pending']
            }}
          />
          {/*<FilterObjectList
            items={offers}
            attributes={['title', 'department', 'jobOfferState']}
            renderItem={renderFilteredOffers}
          />*/}
        </div>
      </div>
    </div>
  )
}

export default JobOffers;

/*import React from "react";
import ShortJobOffer from "./ShortJobOffer";

const JobOffers = ({offers, updateJobOfferList}) => {
    return (
        <div className="row">
            <div className="col-12">
                <h3 className="text-dark fw-light my-5">Les offres de stages en attente de votre réponse :</h3>
                <div className="row justify-content-around">
                    <div className="col-12">
                        {
                            offers.map((offer, index) => (
                                <div key={index} onClick={() => (offer)}>
                                    <ShortJobOffer jobOffer={offer} updateJobOfferList={updateJobOfferList} index={index}/>
                                </div>
                            ))
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}
export default JobOffers;
*/
