import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {useTranslation} from "react-i18next";

const Home = ({ user }) => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    useEffect(() => {
        if (user?.isLoggedIn) {
            switch (user.role) {
                case 'ROLE_STUDENT':
                    navigate('/student');
                    break;
                case 'ROLE_EMPLOYER':
                    navigate('/employer');
                    break;
                case 'ROLE_MANAGER':
                    navigate('/manager');
                    break;
                default:
                    navigate('/');
                    break;
            }
        }
    }, [user, navigate]);

    return (
        <>
            {user?.isLoggedIn ? (<div></div>
            ) : (
                <div className="text-center mt-5">
                    <h1>{t('welcome')} {t('glucoseAcronym')}</h1>
                    <h3>{t('descTitle')}</h3>
                    <p>{t('description')}</p>
                </div>
            )}
        </>
    );
};

export default Home;
