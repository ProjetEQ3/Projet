import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {faArrowLeft, faPaperPlane} from "@fortawesome/free-solid-svg-icons";
import Loading from "../util/Loading";
import { useTranslation } from "react-i18next";
import { useNavigate, useLocation } from "react-router-dom";
import { useDarkMode } from "../../context/DarkModeContext";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import TimeSheetWeek from "../../model/TimeSheetWeek";

const TimeSheet = ({ user }) => {
    const [t] = useTranslation();
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const { darkMode } = useDarkMode();
    const location = useLocation();
    const { jobApplication } = location.state || {};
    const [weeks, setWeeks] = useState([]);
    const [loadedPage, setLoadedPage] = useState(false);

    const handleBack = () => {
        navigate(-1);
    };

    useEffect(() => {
        if (loadedPage) return;
        fetchForExistingTimeSheet();
        setLoadedPage(true);
    }, [loadedPage]);

    const fetchForExistingTimeSheet = () => {
        axiosInstance.get(`/employer/timeSheet/${jobApplication.id}`)
            .then(res => {
                if (res.data && res.data.weeklyHours) {
                    const weeks = res.data.weeklyHours.map(weekData => {
                        let week = new TimeSheetWeek();
                        week.init({
                            weekNumber: weekData.weekNumber,
                            from: weekData.weekStartDate.split('T')[0],
                            to: weekData.weekEndDate.split('T')[0],
                            hoursWorked: weekData.internRealWorkingHours,
                            directSupervisionHours: weekData.directSupervisionHours,
                        });
                        return week;
                    });
                    setWeeks(weeks);
                }
            })
            .catch(() => {
            });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setIsLoading(true);
        const weeklyHours = weeks.map(week => ({
            weekNumber: week.weekNumber,
            weekStartDate: week.from + 'T00:00:00.000Z',
            weekEndDate: week.to + 'T23:59:59.999Z',
            internRealWorkingHours: week.hoursWorked,
            directSupervisionHours: week.directSupervisionHours,
        }));
        axiosInstance.post(`/employer/timeSheet/${jobApplication.id}`, weeklyHours)
            .then(() => {
                toast.success(t('timeSheetSaved'));
            })
            .catch(() => {
                toast.error(t('timeSheetNotSaved'));
            })
            .finally(() => {
                setIsLoading(false);
            })
    };

    useEffect(() => {
        if (jobApplication && jobApplication.jobOffer) {
            const { startDate, duration } = jobApplication.jobOffer;
            const start = new Date(startDate);
            const calculatedWeeks = Array.from({ length: duration }, (_, i) => {
                let week = new TimeSheetWeek();
                week.init({
                    weekNumber: i + 1,
                    from: new Date(start.getTime() + i * 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0],
                    to: new Date(start.getTime() + (i + 1) * 7 * 24 * 60 * 60 * 1000 - 1).toISOString().split('T')[0],
                    hoursWorked: '',
                    directSupervisionHours: '',
                });
                return week;
            });
            setWeeks(calculatedWeeks);
        }
    }, [jobApplication]);

    const handleHoursChange = (e, weekNumber) => {
        const newHours = e.target.value;
        setWeeks(weeks.map(week => {
            if (week.weekNumber === weekNumber) {
                let updatedWeek = new TimeSheetWeek();
                updatedWeek.init({ ...week, hoursWorked: newHours });
                return updatedWeek;
            }
            return week;
        }));
    };

    const handleSupervisionHoursChange = (e, weekNumber) => {
        const newSupervisionHours = e.target.value;
        setWeeks(weeks.map(week => {
            if (week.weekNumber === weekNumber) {
                let updatedWeek = new TimeSheetWeek();
                updatedWeek.init({ ...week, directSupervisionHours: newSupervisionHours });
                return updatedWeek;
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
            <h1 className="text-center fw-light">{t('timeSheet')} - {jobApplication.student.firstName} {jobApplication.student.lastName}</h1>
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
