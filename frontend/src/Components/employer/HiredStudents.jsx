import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { axiosInstance } from '../../App';
import ShortJobApplication from './ShortJobApplication';

const HiredStudents = ({ user }) => {
    const [t] = useTranslation();
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

    return (
        <div>
            <h1>{t('hiredStudents')}</h1>
            <div>
                {acceptedApplications.length > 0 ? (
                    acceptedApplications.map((application) => (
                        <ShortJobApplication key={application.id} jobApplication={application} />
                    ))
                ) : (
                    <p>{t('noStudentsHired')}</p>
                )}
            </div>
        </div>
    );
};

export default HiredStudents;
