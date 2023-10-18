import FilterObjectList from "../util/FilterObjectList"
import ShortJobOffer from "./ShortJobOffer"
import {useTranslation} from "react-i18next";

const JobOffers = ({ offers, updateJobOfferList, updateJobOfferListAfterApprovalOrRefusal }) => {
  const { t } = useTranslation()

  const renderFilteredOffers = (filteredOffers) => {
    return (
      <div className="col-12">
        {
          filteredOffers.length !== 0 ?
              filteredOffers.map((offer, index) => (
                  <div key={index} onClick={() => (offer)}>
                    <ShortJobOffer jobOffer={offer} updateJobOfferList={updateJobOfferList} index={index} updateJobOfferListAfterApprovalOrRefusal={updateJobOfferListAfterApprovalOrRefusal}/>
                  </div>)) :
                <div className="col-12">
                    <h5 className="text-center">{t('noInternship')}</h5>
                </div>
        }
      </div>
    )
  }

  return (
    <div className="row">
      <div className="col-12">
        <h3 className="text-dark fw-light my-5">{t('allInternship')}</h3>
        <div className="row justify-content-around">
          <FilterObjectList
            items={offers}
            attributes={['title:' + t('internshipTitle'), 'department:' + t('department'), 'jobOfferState.select:Status']}
            renderItem={renderFilteredOffers}
            selectOptions={{jobOfferState: ['SUBMITTED', 'OPEN', 'PENDING', 'EXPIRED', 'TAKEN', 'REFUSED']}}
          />
        </div>
      </div>
    </div>
  )
}

export default JobOffers
