
import {useTranslation} from "react-i18next";
import ShortJobOffer from "./ShortJobOffer";

const Home = ({setTab, setIdElement, jobOffers}) => {
    const {t} = useTranslation();


    function handleSelectOffer(offer) {
        setIdElement(offer.id);
        setTab('stages');
    }

    return (
        <div className="container-fluid px-lg-5 px-2 py-2">
            {
                jobOffers.length > 0 && (
                    <>
                        <h3>{t('jobOffers')}</h3>

                        {jobOffers.map((offer, index) => {
                            return (
                                <div key={index} onClick={() => handleSelectOffer(offer)}>
                                    <ShortJobOffer
                                        jobOffer={offer}
                                        isBold={true}
                                    />
                                </div>
                            )
                        })}
                    </>
                )
            }
        </div>
    )
}

export default Home