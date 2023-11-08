
import {useTranslation} from "react-i18next";
import ShortJobOffer from "./ShortJobOffer";
import ShortJobApplicationDisplay from "./ShortJobApplicationDisplay";

const Home = ({cv, setTab, setIdElement, jobOffers, applications, handleViewJobOffer}) => {
    const {t} = useTranslation();

    const handleJobOfferClick = (jobOffer) => {
        handleViewJobOffer(jobOffer);
        setIdElement(jobOffer.id);
        setTab('stages');
    }

    const handleJobApplicationClick = (application) => {
        setIdElement(application.id);
        setTab('my_applications');
    }

    const handleCvClick = () => {
        setTab('cv');
    }

    return (
        <div className="container-fluid px-lg-5 px-2 py-2">
            <h1>{t('home')}</h1>
            {
                cv !== undefined ? (
                    cv.cvState === 'SUBMITTED' ?
                        (
                            <>
                                <h1 onClick={handleCvClick} className={"clickable bg-secondary rounded display-6 text-white text-center py-3"}>{t('cvWaitingApproval')}</h1>
                            </>
                        ) : cv.cvState === "ACCEPTED" ? null : <>
                                <h1 onClick={handleCvClick} className={"clickable bg-danger rounded display-6 text-center text-white py-3"}>{t('cvCurrentlyRefused')}</h1>
                            </>
                    ) : <>
                            <h1 onClick={handleCvClick} className={"clickable bg-danger rounded display-6 text-center text-white py-3"}>{t('cvRequiredToContinue')}</h1>
                        </>
            }
            {
                applications.length !== 0 && (
                    <>
                        <h2>{t('myAppointments')}</h2>
                        <p>{t('appointmentsToAttest')}</p>
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
                        <p>{t('availableInternshipsToApplyTo')}</p>
                        {
                            jobOffers.filter((jobOffer) => jobOffer.isViewed !== true).map((offer, index) => (
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