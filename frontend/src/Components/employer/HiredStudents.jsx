import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { axiosInstance } from '../../App';
import ShortJobApplication from './ShortJobApplication';

const HiredStudents = ({ user }) => {
    const [t] = useTranslation();
    const navigate = useNavigate();
    const [acceptedApplications, setAcceptedApplications] = useState([]);

    useEffect(() => {
        getAcceptedApplications();
    }, [user.id]);

    const getAcceptedApplications = () => {
        if (!user?.id) return;
        axiosInstance
            .get(`/employer/applications/${user.id}`)
            .then((response) => {
                setAcceptedApplications(response.data);
            })
            .catch((error) => {
            });
    };

    const handleTimeSheetButton = () => {
        navigate('/employer/timeSheet');
    };

    return (
        <div>
            <h1>{t('hiredStudents')}</h1>
            <div className="row">
                <button className="btn btn-outline-ose col-12" onClick={handleTimeSheetButton}>BOUTON TEMPORAIRE POUR FEUILLE DE TEMPS</button>
            </div>
            <div>
                {acceptedApplications.map((application) => (
                    <ShortJobApplication key={application.id} jobApplication={application} />
                ))}
            </div>
        </div>
    );
};

export default HiredStudents;
