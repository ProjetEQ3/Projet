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
        <h3 className="text-dark fw-light my-5">Les offres de stages :</h3>
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
