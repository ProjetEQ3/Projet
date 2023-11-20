
import {useTranslation} from "react-i18next";
import ShortJobOffer from "./ShortJobOffer";
import ShortInterviewedStudentInfo from "./ShortInterviewedStudentInfo";
import React, {useEffect, useState} from "react";
import ShortContractNotif from "../user/ShortContractNotif";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";

const Home = ({setTab, setIdElement, fetchStudentList, jobOffers, studentList, contracts, refusedOffers}) => {
    const {t} = useTranslation();
    const [offers, setOffers] = useState([]);
    const [fetched, setFetched] = useState(false);


    useEffect(() => {
        if (jobOffers.length === 0) return;
        setOffers(jobOffers);
    }, [jobOffers]);

    useEffect(() => {
        if (fetched) return;
        if (offers.length > 0) {
            for (const offer of offers) {
                axiosInstance.get(`/employer/offer/applications/${offer.id}`)
                    .then((response) => {
                        setFetched(true)
                        offer.applications = response.data;
                        const updatedOffers = offers.map((o) => {
                            if(o.id === offer.id){
                                return offer
                            }else{
                                return o
                            }
                        })

                        setOffers(updatedOffers)
                    })
                    .catch((error) => {
                        toast.error(t('getStudentsError') + t(error.response?.data.message))
                    })
            }
        }
    }, [offers]);

    function handleSelectOffer(offer) {
        setIdElement(offer.id);
        setTab('stages');
    }

    function handleSelectStudent() {
        setTab('interviewed');
    }

    function allTreated() {
        return jobOffers.length === 0 && studentList.length === 0 && refusedOffers.length === 0 && contracts.filter((contract) => contract.employerSignature === null).length === 0;
    }

    function handleContractClick() {
        setTab('contract');
    }

    return (
        <div className="container-fluid px-lg-5 px-2 py-2">
            {
                allTreated() && (
                    <div className="col-12 text-center">
                        <h1 className="rounded rounded-3 alert alert-success p-2 px-4 display-3">
                            😊<br />{t('allTreated')}
                        </h1>
                    </div>
                )
            }
            {
                refusedOffers.length > 0 && (
                    <>
                        <h3>{t('refusedJobOffers')}</h3>
                        {refusedOffers.map((offer, index) => (
                            <div key={index} onClick={() => handleSelectOffer(offer)}>
                                <ShortJobOffer jobOffer={offer} isBold={true} />
                            </div>
                        ))}
                    </>
                )
            }
            {
                jobOffers.length > 0 && (
                    <>
                        <h3>{t('jobOffersStudentsApplied')}</h3>
                        {offers.map((offer, index) => {
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
            {
                studentList.length > 0 && (
                    <>
                        <h3>{t('studentApplication')}</h3>

                        {studentList.map((student, index) => {
                            return (
                                <div key={index}>
                                    <ShortInterviewedStudentInfo student={student} fetchStudentList={fetchStudentList}/>
                                </div>
                            )
                        })}
                    </>
                )
            }
            {
                contracts.filter((contract) => contract.employerSignature === null).length > 0 && (
                    <>
                        <h2>{t('availableContracts')}</h2>
                        <p>{t('availableContractsToApplyTo')}</p>
                        {
                            contracts.filter((contract) => contract.employerSignature === null).map((contract, index) => (
                                <div onClick={() => handleContractClick()} key={index}>
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