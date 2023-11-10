
import {useTranslation} from "react-i18next";
import ShortJobOffer from "./ShortJobOffer";
import ShortInterviewedStudentInfo from "./ShortInterviewedStudentInfo";
import React from "react";

const Home = ({setTab, setIdElement, fetchStudentList, jobOffers, studentList}) => {
    const {t} = useTranslation();


    function handleSelectOffer(offer) {
        setIdElement(offer.id);
        setTab('stages');
    }

    function handleSelectStudent() {
        setTab('interviewed');
    }

    return (
        <div className="container-fluid px-lg-5 px-2 py-2">
            {
                jobOffers.length === 0 && studentList.length === 0 && (
                    <div className="col-12 text-center">
                        <h1 className="rounded rounded-3 alert alert-success p-2 px-4 display-3">
                            😊<br />{t('allTreated')}
                        </h1>
                    </div>
                )
            }
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
        </div>
    )
}

export default Home