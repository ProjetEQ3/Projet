import React, {useEffect, useState} from "react";
import { useTranslation } from "react-i18next";
import {toast} from "react-toastify";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendar, faX} from "@fortawesome/free-solid-svg-icons";
import { axiosInstance } from "../../App";
import Appointment from "../../model/Appointment";
import {id} from "date-fns/locale";

const ShortJobOfferApplication = ({ user, jobOffer, index }) => {
    const { t } = useTranslation();
    const [isHovered, setIsHovered] = useState(false);

    const [appointments, setAppointments] = useState([]);
    const [checkboxValue, setCheckboxValue] = useState(false);


    useEffect(() => {
        const fetchAppointments = async () => {
            setAppointments([]);
            await axiosInstance.get(`/student/appointmentsByJobOfferIdAndStudentId/${jobOffer.id}/${user.id}`)
                .then((response) => {
                    const newAppointments = response.data.map((appointment) => {
                        const newAppointment = new Appointment();
                        newAppointment.init(appointment);
                        return newAppointment;
                    });
                    newAppointments.sort((a, b) => {
                        return new Date(a.appointmentDate) - new Date(b.appointmentDate);
                    }, []);
                    setAppointments(newAppointments);
                })
                .catch((error) => {
                    console.error("Error fetching appointments:", error);
                    toast.error(t('errorFetchingAppointments'));
                });
        };
        fetchAppointments();
    }, [user, jobOffer, t]);

    function handleChosenAppointment(e) {
        e.preventDefault();
        const selectedAppointmentValue = e.target.querySelector('input[name="appointment"]:checked')?.value;
        if (selectedAppointmentValue === null || selectedAppointmentValue === undefined || selectedAppointmentValue === "" || selectedAppointmentValue === "default") {
            toast.error(t('noAppointmentSelected'));
            return;
        }

        axiosInstance.put(`/student/setAppointmentToChosen/${appointments[selectedAppointmentValue].id}`)
            .then((response) => {
                toast.success(t('appointmentChosen') + '\n' + dateTimeToShortString(selectedAppointmentValue));
                setCheckboxValue(true)
            })
            .catch(() => {
                toast.error(t('errorChoosingAppointment'));
            });
        setCheckboxValue(true)
    }

    function dateTimeToShortString(appointment) {
        let result = "";
        let date = new Date(appointment);
        date.setHours(date.getUTCHours() - 12);
        let day = ('0' + date.getDate()).slice(-2);
        let month = ('0' + (date.getMonth() + 1)).slice(-2);
        let year = date.getFullYear();
        result += `${day}-${month}-${year}`;
        let hours = ('0' + date.getUTCHours()).slice(-2);
        let minutes = ('0' + date.getUTCMinutes()).slice(-2);
        result += ` ${hours}:${minutes}`;
        return result;
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
                    {   appointments.length !== 0 && appointments[0].jobApplication.jobApplicationState === "CONVOKED" ?  (
                        <div className="col-md-3 col-sm-4 mt-sm-4">
                            <div className="text-end text-sm-center mb-2" data-bs-toggle="modal"
                                 data-bs-target={"#appointmentModal" + index}>
                                <button className="btn btn-outline-ose my-auto"
                                        data-testid="appointment-button">
                                    {t('chooseAppointment')}
                                    <FontAwesomeIcon icon={faCalendar} className="ms-2"/>
                                </button>
                            </div>
                            <div id={"appointmentModal" + index} className="modal">
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
                    ) : appointments.length !== 0 && appointments[0].jobApplication.jobApplicationState === "WAITING_APPOINTMENT" ? (
                        <div className="col-lg-3 col-md-4 col-sm-4 mt-sm-4">
                            <div className="text-end text-sm-center mb-2">
                                <button className="btn btn-outline-ose my-auto" data-testid="waiting-appointment-button-testid" disabled>
                                    {t('chosenAppointment')}
                                    <FontAwesomeIcon icon={faCalendar} className="ms-2"/>
                                </button>
                            </div>
                        </div>
                    ) : (
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
