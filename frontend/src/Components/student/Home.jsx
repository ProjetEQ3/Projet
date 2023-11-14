import {useTranslation} from "react-i18next";
import ShortJobOffer from "./ShortJobOffer";
import ShortJobApplicationDisplay from "./ShortJobApplicationDisplay";
import React from "react";
import ShortContractNotif from "../user/ShortContractNotif";

const Home = ({setTab, setIdElement, jobOffers, applications, cv, contracts, handleViewJobOffer}) => {
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

    const handleContractClick = () => {
        setTab('contract');
    }

    return (
        <div className="container-fluid px-lg-5 px-2 py-2">
            {
                cv !== undefined ? (
                    cv.cvState === 'SUBMITTED' ?
                        (
                            <>
                                <h1 onClick={handleCvClick} className={"clickable bg-secondary rounded display-6 text-white text-center py-3"}>{t('cvWaitingApproval')}</h1>
                            </>
                        ) : cv.cvState === "ACCEPTED" ? null : <>
                            <h1 onClick={handleCvClick} className={"clickable alert alert-danger rounded display-6 text-center py-3"}>{t('cvCurrentlyRefused')}</h1>
                        </>
                ) : <>
                    <h1 onClick={handleCvClick} className={"clickable alert alert-danger rounded display-6 text-center py-3"}>{t('cvRequiredToContinue')}</h1>
                </>
            }
            {
                applications.length > 0 && (
                    <>
                        <h2>{t('myAppointments')}</h2>
                        <p>{t('appointmentsToAttest')}</p>
                        {
                            applications.map((application, index) => (
                                <div onClick={() => handleJobApplicationClick(application)} key={index} data-testid="application">
                                    <ShortJobApplicationDisplay application={application} />
                                </div>))
                        }
                    </>
                )
            }
            {
                jobOffers.length > 0 && (
                    <>
                        <h2>{t('availableInternships')}</h2>
                        <p>{t('availableInternshipsToApplyTo')}</p>
                        {
                            jobOffers.filter((jobOffer) => jobOffer.isViewed !== true).map((offer, index) => (
                                <div onClick={() => handleJobOfferClick(offer)} key={index} data-testid="job-offer">
                                    <ShortJobOffer jobOffer={offer} />
                                </div>))
                        }
                    </>
                )
            }
            {
                contracts.length > 0 && (
                    <>
                        <h2>{t('availableContracts')}</h2>
                        <p>{t('availableContractsToApplyTo')}</p>
                        {
                            contracts.filter((contract) => contract.studentSignature === null).map((contract, index) => (
                                <div onClick={() => handleContractClick()} key={index} data-testid="contract">
                                    <ShortContractNotif contract={contract}/>
                                </div>
                            ))
                        }
                    </>
                )
            }
        </div>
    )
}

export default Home