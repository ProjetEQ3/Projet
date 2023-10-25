import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../../App";
import JobOffer from "../../model/JobOffer";
import ShortJobOfferApplication from "./ShortJobOfferApplication";
import FilterObjectList from "../util/FilterObjectList";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";

function MyApplications({ user }) {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [myApplications, setMyApplications] = useState([]);

    useEffect(() => {
        if (!user?.isLoggedIn) {
            navigate("/");
        }
        fetchMyApplications();
    }, [user, navigate]);
    async function fetchMyApplications() {
        await axiosInstance.get(`/student/appliedJobOffer/${user.id}`)
            .then((response) => {
                const jobOffers = response.data.map((jobOfferData) => {
                    const newJobOffer = new JobOffer();
                    newJobOffer.init(jobOfferData);
                    return newJobOffer;
                });
                setMyApplications(jobOffers);
            })
            .catch((error) => {
                toast.error(t('fetchError') + t(error.response?.data.message));
            });
    }
    function handleRefresh() {
        fetchMyApplications();
    }

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
                                    <ShortJobOfferApplication index={index} user={user} jobOffer={offer} key={offer.id} refresh={handleRefresh}/>
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
