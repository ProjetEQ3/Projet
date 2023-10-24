import {useEffect, useState} from "react";
import { useTranslation } from "react-i18next";

const ShortJobOffer = ({ jobOffer }) => {
    const { t } = useTranslation();
    const [isHovered, setIsHovered] = useState(false);
    const [appointments, setAppointments] = useState([]);
    const [chosenAppointment, setChosenAppointment] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const fetchAppointments = () => {
        // Fetch your appointments data and setAppointments(response.data);
    };
    const postAppointment = (appointment) => {
    //     post the appointment
    }

    useEffect(() => {
        fetchAppointments();
    }, []);

    const handleMouseEnter = () => {
        setIsHovered(true);
    };
    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    const openModal = () => {
        setIsModalOpen(true);
    };
    const closeModal = () => {
        setIsModalOpen(false);
    };

    const selectAppointment = (appointment) => {
        setChosenAppointment(appointment);
    };

    const confirmAppointment = () => {
        postAppointment(chosenAppointment)
        setChosenAppointment(null);
        closeModal();
    };

    return (
        <div className={`row ${!isHovered ? "m-2" : "m-1 shadow"}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-12">
                        <h5 data-testid="job-title" className="text-dark fw-light pt-1">
                            {jobOffer.title}
                        </h5>
                        <p data-testid="job-department" className="text-dark fw-light mb-3">
                            {t(jobOffer.department)}
                        </p>
                        {appointments.length === 0 ? (
                            <p className="text-dark fw-light text-end">{t("noAppointments")}</p>
                        ) : (
                            <>
                                <button onClick={openModal}>{t("seeAppointments")}</button>
                                {isModalOpen && (
                                    <div className="modal">
                                        <div className="modal-content">
                                            <h2>{t("appointments")}</h2>
                                            <ul>
                                                {appointments.map((appointment) => (
                                                    <li key={appointment.id}>
                                                        {appointment.date}
                                                        <button onClick={() => selectAppointment(appointment)}>
                                                            {t("select")}
                                                        </button>
                                                    </li>
                                                ))}
                                            </ul>
                                            {chosenAppointment && (
                                                <div>
                                                    <p>{t("selectedAppointment")}: {chosenAppointment.date}</p>
                                                    <button onClick={confirmAppointment}>{t("confirm")}</button>
                                                </div>
                                            )}
                                            <button onClick={closeModal}>{t("close")}</button>
                                        </div>
                                    </div>
                                )}
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ShortJobOffer;
