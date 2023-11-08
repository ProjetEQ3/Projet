import {useState} from "react";
import {useTranslation} from "react-i18next";

const ShortStudent = ({student}) => {
    const {t} = useTranslation();
    const [isHovered, setHovered] = useState(false);
    const [showApplications, setShowApplications] = useState(false);
    const [studentApplications, setStudentApplications] = useState([]);
    const [jobApplications, setJobApplications] = useState([]);

    const handleMouseEnter = () => {
        setHovered(true);
    };

    const handleMouseLeave = () => {
        setHovered(false);
    };

    const handleStudentClick = () => {
        console.log(showApplications)

        if (!showApplications) {
            setShowApplications(true);
        }
        else {
            setShowApplications(false);
            setStudentApplications([]);
        }

        //call API
    }

return (
        <div className={`row clickable ${!isHovered? 'm-2':'m-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave} onClick={handleStudentClick}>
            <div className="col-12 bg-white rounded">
                <div className="my-2">
                    <div className="row">
                        <div className="col-12 col-md-3">
                            <h5>{student.firstName} {student.lastName}</h5>
                        </div>
                        <div className="col-12 col-md-3">
                            <h5>{t(student.department)}</h5>
                        </div>
                        <div className="col-12 col-md-3">
                            <h5>{student.matricule}</h5>
                        </div>
                        <div className="col-12 col-md-3">
                            <h5>{student.studentState}</h5>
                        </div>
                    </div>
                </div>
                {(showApplications && jobApplications.length > 0?
                        <div className="row bg-light rounded">
                            <div className="col-12">
                                {
                                    studentApplications.map((application, index) => (
                                        <div key={index}>
                                            <h6>{application.jobOffer.title}</h6>
                                            <p>{application.jobOffer.description}</p>
                                        </div>
                                    ))
                                }
                            </div>
                        </div>:
                        showApplications && jobApplications.length === 0 ?
                            <h5 className="fw-light bg-light p-3 rounded">{t('noApplications')}</h5>
                            :
                            ''
                    )}
            </div>
        </div>
    )
}
export default ShortStudent