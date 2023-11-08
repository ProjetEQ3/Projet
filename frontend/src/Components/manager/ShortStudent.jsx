import {useState} from "react";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";
import {axiosInstance} from "../../App";
import State from "../util/State";

const ShortStudent = ({student}) => {
    const {t} = useTranslation();
    const [isHovered, setHovered] = useState(false);
    const [showApplications, setShowApplications] = useState(false);
    const [studentApplications, setStudentApplications] = useState([]);

    const handleMouseEnter = () => {
        setHovered(true);
    };

    const handleMouseLeave = () => {
        setHovered(false);
    };

    const handleStudentClick = () => {
        if (!showApplications) {
            setShowApplications(true);
        }
        else {
            setShowApplications(false);
            setStudentApplications([]);
        }
        getStudentApplications().then(r => r);
    }

    const getStudentApplications = async () => {
        await axiosInstance.get(`manager/student/jobApplications/${student.id}`)
            .then((response) => {
                setStudentApplications(response.data);
            }).catch((error) => {
                if (error.response?.status === 401) {
                    return;
                }
                toast.error(t('fetchError') + t(error));
            });
    }

return (
        <div className={`row clickable ${!isHovered? 'm-2':'m-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave} onClick={handleStudentClick}>
            <div className="col-12 bg-white rounded">
                <div className="my-2">
                    <div className="row col-11 ms-2">
                        <div className="col-12 col-md-3 my-auto">
                            <h5>{student.firstName} {student.lastName}</h5>
                        </div>
                        <div className="col-12 col-md-3 my-auto">
                            <h5>{t(student.department)}</h5>
                        </div>
                        <div className="col-12 col-md-3 my-auto">
                            <h5>{student.matricule}</h5>
                        </div>
                        <div className="col-12 col-md-3 text-center my-auto">
                            <State state={student.studentState}/>
                        </div>
                    </div>
                </div>
                {(showApplications && studentApplications.length > 0?
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
                        showApplications && studentApplications.length === 0 ?
                            <h5 className="fw-light bg-light p-3 rounded">{t('noApplications')}</h5>
                            :
                            ''
                    )}
            </div>
        </div>
    )
}
export default ShortStudent