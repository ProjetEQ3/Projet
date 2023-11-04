import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../../App";
import JobOffer from "../../model/JobOffer";
import ShortJobOfferApplication from "./ShortJobOfferApplication";
import FilterObjectList from "../util/FilterObjectList";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";

function MyApplications({ user, myApplications, setMyApplications, fetchMyApplications }) {
    const {t} = useTranslation();
    const navigate = useNavigate();

    useEffect(() => {
        if (!user?.isLoggedIn) {
            navigate("/");
        }
    }, [user, navigate]);

    return (
        <div>
            {myApplications.length === 0 ? (
                <p>{t('noJobOffers')}</p>
            ) : (
                <div>
                    <FilterObjectList
                        items={myApplications}
                        attributes={['title:' + t('internshipTitle')]}
                        selectOptions={{jobOfferState: ['SUBMITTED', 'OPEN', 'PENDING', 'EXPIRED', 'TAKEN', 'REFUSED']}}
                        renderItem={(filteredJobOffers) => (
                            <div>
                                {filteredJobOffers.map((offer, index) => (
                                    <ShortJobOfferApplication index={index} user={user} jobOffer={offer} key={offer.id} refresh={fetchMyApplications}/>
                                ))}
                            </div>
                        )}
                    />
                </div>
            )}
        </div>
    );
}

export default MyApplications;
