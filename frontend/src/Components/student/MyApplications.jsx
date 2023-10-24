import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../../App";
import JobOffer from "../../model/JobOffer";
import ShortJobOffer from "./ShortJobOffer";
import FilterObjectList from "../util/FilterObjectList";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPenToSquare, faX} from "@fortawesome/free-solid-svg-icons";

function MyApplications({ user }) {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [myApplications, setMyApplications] = useState([]);

    const [appointments, setAppointments] = useState([
        "2023-10-23T01:00:00.0000", "2023-10-23T02:00:00.0000", "2023-10-23T03:00:00.0000"
    ]);
    const [chosenAppointment, setChosenAppointment] = useState(null);

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
                toast.error(t('fetchError') + t(error.response.data.message));
            }
        }
        fetchAppointments();
        fetchMyApplications();
    }, [user, navigate]);

    const fetchAppointments = async () => {
        //     Get the appointments of the student (setAppointments)
    }

    function handleChosenAppointment(e) {
        e.preventDefault();
        const selectedAppointmentValue = e.target.querySelector('input[name="appointment"]:checked').value;
        console.log("Selected appointment: ", selectedAppointmentValue); // Change to API call

        // Close the modal
        // const myModal = document.getElementById("appointmentModal"); // Replace "myModal" with your modal's ID
        // const modal = new bootstrap.Modal(myModal); // Initialize the modal
        // modal.hide(); // Close the modal
    }

    function dateTimeToShortString(appointment) {
        let result = "";
        result += appointment.split("T")[0].split("-").reverse().join("-");
        result += " ";
        result += appointment.split("T")[1].split(":").slice(0, 2).join(":");

        return result;
    }

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
                                    <>
                                        <ShortJobOffer jobOffer={offer} key={offer.id}/>
                                        {appointments.length > 0 && (
                                            <div>
                                                <div className="text-end mb-2" data-bs-toggle="modal"
                                                     data-bs-target="#appointmentModal">
                                                    <button className="btn btn-outline-ose my-auto"
                                                            data-testid="appointment-button">
                                                        {t('chooseAppointment')}
                                                        <FontAwesomeIcon icon={faPenToSquare} className="ms-2"/>
                                                    </button>
                                                </div>
                                                 <div id="appointmentModal" className="modal">
                                                    <div className="modal-dialog">
                                                        <div className="modal-content">
                                                            <div className="modal-header">
                                                                <h3 className="modal-title">{t('appointment')}<br/></h3>
                                                                <FontAwesomeIcon icon={faX} data-bs-dismiss="modal"
                                                                                 className="danger-hover fa-lg pe-2"/>
                                                            </div>
                                                            <div className="modal-body">
                                                                <form onSubmit={handleChosenAppointment}>
                                                                    {
                                                                        appointments.map((appointment, index) => (
                                                                            <div className="form-check" key={index}>
                                                                                <input className="form-check-input"
                                                                                       type="radio"
                                                                                       name="appointment"
                                                                                       id={`appointment-${index}`}
                                                                                       value={appointment}
                                                                                       onClick={setChosenAppointment(appointment)}/>
                                                                                <label className="form-check-label"
                                                                                       htmlFor={`appointment-${index}`}>
                                                                                    {dateTimeToShortString(appointment)}
                                                                                </label>
                                                                            </div>
                                                                        ))
                                                                    }
                                                                    <button className="btn btn-success me-3 my-2" type="submit">{t('confirm')}</button>
                                                                    <button className="btn btn-danger my-2" type="reset" data-bs-dismiss="modal">{t('cancel')}</button>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        )}
                                    </>
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
