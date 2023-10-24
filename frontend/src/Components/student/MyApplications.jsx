import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../../App";
import JobOffer from "../../model/JobOffer";
import ShortJobOffer from "./ShortJobOffer";
import FilterObjectList from "../util/FilterObjectList";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendar, faClock, faPenToSquare, faX} from "@fortawesome/free-solid-svg-icons";

function MyApplications({ user }) {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [myApplications, setMyApplications] = useState([]);

    useEffect(() => {
        if (!user?.isLoggedIn) {
            navigate("/");
        }

        async function fetchMyApplications() {
            try {
                const response = await axiosInstance.get(`/student/appliedJobOffer/${user.id}`);
                const jobOffers = response.data.map((jobOfferData) => {
                    const newJobOffer = new JobOffer();
                    newJobOffer.init(jobOfferData);
                    return newJobOffer;
                });
                setMyApplications(jobOffers);
            } catch (error) {
                toast.error(t('fetchError') + t(error.response?.data.message));
            }
        }
        fetchMyApplications();
    }, [user, navigate]);

    return (
        <div>
            {myApplications.length === 0 ? (
                <p>{t('noJobOffers')}</p>
            ) : (
                <div>
                    <FilterObjectList
                        items={myApplications}
                        attributes={["title", "companyName", "location", "jobOfferState.select:Status"]}
                        selectOptions={{jobOfferState: ['SUBMITTED', 'OPEN', 'PENDING', 'EXPIRED', 'TAKEN', 'REFUSED']}}
                        renderItem={(filteredJobOffers) => (
                            <div>
                                {filteredJobOffers.map((offer, index) => (
                                    <ShortJobOffer jobOffer={offer} key={offer.id}/>
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
