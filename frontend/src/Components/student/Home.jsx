
import {useTranslation} from "react-i18next";
import ShortJobOffer from "./ShortJobOffer";

const Home = ({setTab, setIdElement, jobOffers}) => {
    const {t} = useTranslation();

    const handleJobOfferClick = (jobOffer) => {
        console.log("Job:", jobOffer)
        setIdElement(jobOffer.id);
        setTab('stages');
    }

    return (
        <div className="container-fluid px-lg-5 px-2 py-2">
            <h1>{t('home')}</h1>
            {
                jobOffers.length !== 0 && (
                    <>
                        <h2>{t('availableInternships')}</h2>
                        {
                            jobOffers.filter((jobOffer) => jobOffer.hasApplied !== true).map((offer, index) => (
                            <div onClick={() => handleJobOfferClick(offer)} key={index}>
                                <ShortJobOffer jobOffer={offer} />
                            </div>))
                        }
                    </>
                )
            }
        </div>
    )
}

export default Home