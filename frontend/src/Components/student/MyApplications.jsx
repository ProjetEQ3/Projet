import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../../App";
import JobOffer from "../../model/JobOffer";
import ShortJobOffer from "./ShortJobOffer";

function MyApplications({ user }) {
    const [myApplications, setMyApplications] = useState([]);
    const navigate = useNavigate();

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
                console.log("Fetch error: " + error);
            }
        }

        fetchMyApplications();
    }, [user, navigate]);

    return (
        <div>
            {myApplications.length === 0 ? (
                <p>Vous n'avez pas d'applications.</p>
            ) : (
                <ul>
                    {myApplications.map((offer, index) => (
                        <ShortJobOffer jobOffer={offer} key={offer.id}/>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default MyApplications;
