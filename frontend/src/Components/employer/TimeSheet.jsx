import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {faArrowLeft, faPaperPlane} from "@fortawesome/free-solid-svg-icons";
import Loading from "../util/Loading";
import { useTranslation } from "react-i18next";
import { useNavigate, useLocation } from "react-router-dom";
import { useDarkMode } from "../../context/DarkModeContext";

const TimeSheet = ({ user }) => {
    const [t] = useTranslation();
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const { darkMode } = useDarkMode();
    const location = useLocation();
    const { jobApplication } = location.state || {};
    const [weeks, setWeeks] = useState([]);

    const calculateEndDate = (startDate, weekNumber) => {
        const oneWeek = 7 * 24 * 60 * 60 * 1000;
        const endDate = new Date(new Date(startDate).getTime() + (oneWeek * (weekNumber - 1)));
        return endDate.toISOString().split('T')[0];
    };

    const handleBack = () => {
        navigate(-1);
    };

    // LOAD TIME SHEET FROM DB

    const handleSubmit = (e) => {
        e.preventDefault();

        // SAVE TIME SHEET TO DB

    };

    useEffect(() => {
        if (jobApplication && jobApplication.jobOffer) {
            const { startDate, duration } = jobApplication.jobOffer;
            const start = new Date(startDate);
            const calculatedWeeks = Array.from({ length: duration }, (_, i) => {
                const from = new Date(start.getTime() + i * 7 * 24 * 60 * 60 * 1000);
                const to = new Date(from.getTime() + 6 * 24 * 60 * 60 * 1000);
                return {
                    weekNumber: i + 1,
                    from: from.toISOString().split('T')[0],
                    to: to.toISOString().split('T')[0],
                    hoursWorked: '',
                    directSupervisionHours: '',
                };
            });
            setWeeks(calculatedWeeks);
        }
    }, [jobApplication]);

    const handleHoursChange = (e, weekNumber) => {
        const newHours = e.target.value;
        setWeeks(weeks.map(week => {
            if (week.weekNumber === weekNumber) {
                return { ...week, hoursWorked: newHours };
            }
            return week;
        }));
    };

    const handleSupervisionHoursChange = (e, weekNumber) => {
        const newSupervisionHours = e.target.value;
        setWeeks(weeks.map(week => {
            if (week.weekNumber === weekNumber) {
                return { ...week, directSupervisionHours: newSupervisionHours };
            }
            return week;
        }));
    };

    return (
        <div className="container">
            <button className="btn btn-outline-ose" onClick={handleBack}>
                <FontAwesomeIcon icon={faArrowLeft} className="fa-2 me-2"/>
                {t('back')}
            </button>
            <h1 className="text-center fw-light">{t('timeSheet')}</h1>
            <br/>
            {isLoading ? (
                <Loading/>
            ) : (
                <div className="row">
                    <div className="col-md-9 mx-auto">
                        <form onSubmit={handleSubmit}>
                            <table className={`table table-striped ${darkMode ? 'table-dark' : ''}`}>
                                <thead>
                                <tr>
                                    <th>{t('weekFrom')}</th>
                                    <th>{t('weekTo')}</th>
                                    <th>{t('actualWorkHours')}</th>
                                    <th>{t('actualDirectSupervisionHours')}</th>
                                </tr>
                                </thead>
                                <tbody>
                                {weeks.map(week => (
                                    <tr key={week.weekNumber}>
                                        <td>{week.from}</td>
                                        <td>{week.to}</td>
                                        <td>
                                            <input
                                                type="number"
                                                className={`form-control ${darkMode ? "dark-input" : ""}`}
                                                value={week.hoursWorked}
                                                onChange={(e) => handleHoursChange(e, week.weekNumber)}
                                            />
                                        </td>
                                        <td>
                                            <input
                                                type="number"
                                                className={`form-control ${darkMode ? "dark-input" : ""}`}
                                                value={week.directSupervisionHours}
                                                onChange={(e) => handleSupervisionHoursChange(e, week.weekNumber)}
                                            />
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                            <div className="row my-4">
                                <div className="col-4 mx-auto">
                                    <button type="submit" className="btn btn-outline-ose col-12">{t('save')}</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default TimeSheet;
