import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Home = ({ user }) => {
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
                <div className="Home text-center">
                    <h1>Bienvenue sur GlucOSE</h1>
                    <h3>Votre application de gestion de stage</h3>
                    <p>GlucOSE est une application web qui permet de gérer les stages des étudiants.</p>
                </div>
            )}
        </>
    );
};

export default Home;
