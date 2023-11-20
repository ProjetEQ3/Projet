import { useTranslation } from "react-i18next";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendar, faX} from "@fortawesome/free-solid-svg-icons";
import React from "react";
import {useDarkMode} from "../../context/DarkModeContext";

const ShortJobApplicationDisplay = ({ application }) => {
    const { t } = useTranslation();

    const { darkMode } = useDarkMode();

    function dateTimeToShortString(appointment) {
        const date = new Date(appointment);

        const timeZoneOffset = date.getTimezoneOffset();

        date.setMinutes(date.getMinutes() - timeZoneOffset);

        return  t(date.toDateString().split(' ')[0]) + " " +
            t('the') + " " +
            date.toDateString().split(' ')[2] + " " +
            t(date.toDateString().split(' ')[1]) + " " +
            t('at') + " " +
            date.getHours() + "h" + date.getMinutes().toString().padStart(2, '0');
    }

    return (
        application.appointments && application.appointments.length > 0 ? (
            <div className="row clickable m-2">
                <div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded d-flex justify-content-between align-items-center`}>
                    <div>
                        <h5 data-testid="job-title" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light pt-1`}>
                            {application.title}
                        </h5>
                        <p data-testid="job-department" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>
                            {t(application.department)}
                        </p>
                    </div>
                    <div className="col-lg-3 col-md-4 col-sm-4 mt-sm-2">
                        {
                            application.appointments.length > 1 ?
                                <div>
                                    <button className="btn btn-danger float-end me-5" data-testid="waiting-appointment-button-testid" disabled>
                                        {t('chooseAppointment')}
                                    </button>
                                </div> :
                            <div className="text-end text-sm-center mb-2">
                                <button className="btn btn-success my-auto " data-testid="waiting-appointment-button-testid"
                                        disabled>
                                    {t('chosenAppointment')}
                                    <p style={{whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis'}}
                                       className="pb-1 mb-0"> {dateTimeToShortString(application.appointments[0].appointmentDate)}
                                        <FontAwesomeIcon icon={faCalendar} className="ms-2"/></p>
                                </button>
                            </div>
                        }
                    </div>
                </div>
            </div>
        ) : null
    )
}

export default ShortJobApplicationDisplay;