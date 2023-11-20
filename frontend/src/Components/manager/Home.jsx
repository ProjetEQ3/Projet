import {useTranslation} from "react-i18next";
import {faCircleExclamation} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import ShortContractNotif from "../user/ShortContractNotif";
import React from "react";

const Home = ({ setTab, setIdElement, nbCvs, contracts, nbSubmittedOffers, setOfferFilter }) => {
    const {t} = useTranslation();

    function handleCvClick() {
        setTab('cvs');
        setIdElement('cv');
    }

    function handleContractClick() {
        setTab('contracts');
    }

    function handleOfferClick() {
        setTab('stages');
        setOfferFilter('SUBMITTED');
    }

    function allTreated() {
        return nbCvs === 0 && contracts.filter((contract) => contract.managerSignature === null && contract.employerSignature !== null && contract.studentSignature !== null).length === 0 && nbSubmittedOffers === 0;
    }

    function getNbSignableContract() {
        return contracts.filter((contract) => contract.managerSignature === null && contract.employerSignature !== null && contract.studentSignature !== null).length;
    }

    return (
        <div className="container-fluid row px-lg-5 px-2 py-2">
            {
                allTreated() &&
                <div className="col-12 text-center">
                    <h1 className="rounded rounded-3 alert alert-success p-2 px-4 display-3">
                        😊<br />{t('allTreated')}
                    </h1>
                </div>
            }
            {
                nbCvs > 0 &&
                <div className="col-12">
                    <h5 className="rounded rounded-3 alert alert-danger p-2 px-4 display-6 clickable"
                        onClick={handleCvClick}>
                        <FontAwesomeIcon icon={faCircleExclamation} className="me-4" />
                        {t('youHave')} <strong>{nbCvs}</strong> {t('cvToTreat')}
                    </h5>
                </div>
            }
            {
                nbSubmittedOffers > 0 &&
                <div className="col-12">
                    <h5 className="rounded rounded-3 alert alert-danger p-2 px-4 display-6 clickable"
                        onClick={handleOfferClick}>
                        <FontAwesomeIcon icon={faCircleExclamation} className="me-4" />
                        {t('youHave')} <strong>{nbSubmittedOffers}</strong> {t('offersToReview')}
                    </h5>
                </div>
            }
            {
                contracts.filter((contract) => contract.managerSignature === null && contract.employerSignature !== null && contract.studentSignature !== null).length > 0 && (
                    <div className="col-12">
                        <h5 className="rounded rounded-3 alert alert-danger p-2 px-4 display-6 clickable"
                            onClick={handleContractClick}>
                            <FontAwesomeIcon icon={faCircleExclamation} className="me-4" />
                            {t('youHave')} <strong>{getNbSignableContract()}</strong> {t('contractsToSign')}
                        </h5>
                    </div>
                )
            }
        </div>
    )
}

export default Home
