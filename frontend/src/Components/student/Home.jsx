
import {useTranslation} from "react-i18next";
import ShortJobOffer from "./ShortJobOffer";
import ShortJobApplicationDisplay from "./ShortJobApplicationDisplay";

const Home = ({setTab, setIdElement, jobOffers, applications}) => {
    const {t} = useTranslation();

    const handleJobOfferClick = (jobOffer) => {
        setIdElement(jobOffer.id);
        setTab('stages');
    }

    const handleJobApplicationClick = (application) => {
        setIdElement(application.id);
        setTab('my_applications');
    }

    return (
        <div className="container-fluid px-lg-5 px-2 py-2">
            <h1>{t('home')}</h1>
            {
                applications.length !== 0 && (
                    <>
                        <h2>{t('myAppointments')}</h2>
                        {
                            applications.map((application, index) => (
                                <div onClick={() => handleJobApplicationClick(application)} key={index}>
                                    <ShortJobApplicationDisplay application={application} />
                                </div>))
                        }
                    </>
                )
            }
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