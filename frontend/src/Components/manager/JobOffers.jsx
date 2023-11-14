import FilterObjectList from "../util/FilterObjectList";
import ShortJobOffer from "./ShortJobOffer";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";

const JobOffers = ({ offers, updateJobOfferList, updateJobOfferListAfterApprovalOrRefusal, filter }) => {
  const { t } = useTranslation();
  const filteredOffers = filter ? offers.filter(offer => offer.jobOfferState === filter) : offers;

  const { darkMode } = useDarkMode();

  const renderFilteredOffers = () => {
    return (
        <div className="col-12">
          {filteredOffers.length !== 0 ?
              filteredOffers.map((offer, index) => (
                  <div key={index} onClick={() => (offer)}>
                    <ShortJobOffer jobOffer={offer} updateJobOfferList={updateJobOfferList} index={index} updateJobOfferListAfterApprovalOrRefusal={updateJobOfferListAfterApprovalOrRefusal}/>
                  </div>
              )) :
              <div className="col-12">
                <h5 className={`${darkMode ? 'text-light' : 'text-dark'} text-center`}>{t('noInternship')}</h5>
              </div>
          }
        </div>
    );
  };

  return (
      <div className="row">
        <div className="col-12">
            <h3 className={`fw-light my-5 ${darkMode ? 'text-light' : 'text-dark'}`}>{t('allInternship')}</h3>
            <div className="row justify-content-around">
            <FilterObjectList
                items={filteredOffers}
                renderItem={renderFilteredOffers}
                attributes={['title:' + t('internshipTitle'), 'department:' + t('department'), 'jobOfferState.select:Status']}
                selectOptions={{jobOfferState: ['SUBMITTED', 'OPEN', 'PENDING', 'EXPIRED', 'TAKEN', 'REFUSED']}}
            />
          </div>
        </div>
      </div>
  );
};

export default JobOffers;
