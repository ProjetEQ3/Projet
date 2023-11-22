import {useState} from "react";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";
import {useNavigate} from "react-router-dom";
const ShortJobApplication = ({ jobApplication }) => {
    const [t] = useTranslation();
    const navigate = useNavigate();
    const [isHovered, setIsHovered] = useState(false);

    const { darkMode } = useDarkMode();

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    const handleTimeSheetButton = () => {
        navigate('/employer/timeSheet', { state: { jobApplication } });
    };

    const handleStudentEvalButton = () => {
        navigate('/employer/studentEval', { state: { jobApplication } });
    };

    return (
        <div className={`row clickable ${!isHovered ? 'm-2' : 'm-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded`}>
                <div className="row">
                    <div className="col-md-8 col-sm-6">
                        <h5 data-testid="job-application-title" className={`${darkMode ? 'text-light' : 'text-dark'} pt-1`}>
                            {jobApplication.student.firstName + " " + jobApplication.student.lastName}
                        </h5>
                        <p data-testid="job-application-student" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>
                            {jobApplication.jobOffer.title}
                        </p>
                    </div>
                    <div className="col-md-4 col-sm-6 d-flex justify-content-end align-items-center">
                        <button className="btn btn-outline-ose btn-sm m-2" onClick={handleTimeSheetButton}>{t('timeSheet')}</button>
                        <button className="btn btn-outline-ose btn-sm m-2" onClick={handleStudentEvalButton}>{t('evaluateStudent')}</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ShortJobApplication;