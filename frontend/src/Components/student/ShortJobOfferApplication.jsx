import React, {useEffect, useState} from "react";
import { useTranslation } from "react-i18next";
import {toast} from "react-toastify";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendar, faX} from "@fortawesome/free-solid-svg-icons";
import { axiosInstance } from "../../App";
import Appointment from "../../model/Appointment";

const ShortJobOfferApplication = ({ user, jobOffer }) => {
    const { t } = useTranslation();
    const [isHovered, setIsHovered] = useState(false);

    const [appointments, setAppointments] = useState([]);
    const [checkboxValue, setCheckboxValue] = useState(false);

    useEffect(() => {
        fetchAppointments();
    }, [user, jobOffer]);

    const fetchAppointments = async () => {
        setAppointments([]);
        await axiosInstance.get(`/student/appointmentsByJobOfferIdAndStudentId/${jobOffer.id}/${user.id}`)
            .then((response) => {
                response.data.map((appointment) => {
                    const newAppointment = new Appointment();
                    newAppointment.init(appointment);
                    setAppointments((appointments) => [...appointments, newAppointment]);
                });
            })
            .catch((error) => {
                console.error("Error fetching appointments:", error);
                toast.error(t('errorFetchingAppointments'));
            });
    };

    function handleChosenAppointment(e) {
        e.preventDefault();
        const selectedAppointmentValue = e.target.querySelector('input[name="appointment"]:checked')?.value;
        if (selectedAppointmentValue === null || selectedAppointmentValue === undefined || selectedAppointmentValue === "" || selectedAppointmentValue === "default") {
            toast.error(t('noAppointmentSelected'));
            return;
        }

        console.log("Selected appointment: ", selectedAppointmentValue); // Change to API call

        toast.success(t('appointmentChosen') + '\n' + dateTimeToShortString(selectedAppointmentValue));
        setCheckboxValue(true)
    }

    function dateTimeToShortString(appointment) {
        let result = "";
        result += appointment.split("T")[0].split("-").reverse().join("-");
        result += " ";
        result += appointment.split("T")[1].split(":").slice(0, 2).join(":");

        return result;
    }

    function appointmentChosable() {
        console.log("Job offer state: ", jobOffer.jobOfferState)
        if (appointments.length === 0) return false;
        return jobOffer.jobOfferState === "CONVOKED";
    }

    const handleMouseEnter = () => {
        setIsHovered(true);
    };
    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    return (
        <div className={`row ${!isHovered ? "m-2" : "m-1 shadow"}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-lg-9 col-md-8 col-sm-6">
                        <h5 data-testid="job-title" className="text-dark fw-light pt-1">
                            {jobOffer.title}
                        </h5>
                        <p data-testid="job-department" className="text-dark fw-light mb-3">
                            {t(jobOffer.department)}
                        </p>
                    </div>
                    {appointmentChosable() === true ?  (
                        <div className="col-md-3 col-sm-4 mt-sm-4">
                            <div className="text-end text-sm-center mb-2" data-bs-toggle="modal"
                                 data-bs-target="#appointmentModal">
                                <button className="btn btn-outline-ose my-auto"
                                        data-testid="appointment-button">
                                    {t('chooseAppointment')}
                                    <FontAwesomeIcon icon={faCalendar} className="ms-2"/>
                                </button>
                            </div>
                            <div id="appointmentModal" className="modal">
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h3 className="modal-title">{t('appointments')}<br/></h3>
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
                                                                   value={appointment.appointmentDate}/>
                                                            <label className="form-check-label"
                                                                   htmlFor={`appointment-${index}`}>
                                                                {dateTimeToShortString(appointment.appointmentDate)}
                                                            </label>
                                                        </div>
                                                    ))
                                                }
                                                <div className="form-check" key="default">
                                                    <input hidden={true}
                                                           type="radio"
                                                           name="appointment"
                                                           id={`appointment-default`}
                                                           value="default"
                                                           checked={checkboxValue}/>
                                                    <label hidden={true}
                                                           htmlFor={`appointment-default`}>
                                                        default
                                                    </label>
                                                </div>
                                                <button className="btn btn-success me-3 my-2" type="submit" data-bs-dismiss="modal">{t('confirm')}</button>
                                                <button className="btn btn-danger my-2" type="reset" data-bs-dismiss="modal">{t('cancel')}</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ): (
                        <div className="col-lg-3 col-md-4 col-sm-4 mt-sm-4">
                            <div className="text-end text-sm-center mb-2">
                                <button className="btn btn-outline-ose my-auto" data-testid="no-appointment-button-testid" disabled>
                                    {t('noAppointments')}
                                    <FontAwesomeIcon icon={faCalendar} className="ms-2"/>
                                </button>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ShortJobOfferApplication;
