import React, {useState} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons";
import Loading from "../util/Loading";

function EnvEvaluation({ user }) {
    const [t] = useTranslation();
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const { darkMode } = useDarkMode();
    const location = useLocation();
    const { application } = location.state || {};

    const handleBack = () => {
        navigate(-1);
    };

    return (
        <div className="container">
            <button className="btn btn-outline-ose" onClick={handleBack}>
                <FontAwesomeIcon icon={faArrowLeft} className="fa-2 me-2"/>
                {t('back')}
            </button>
            <h1 className="text-center fw-light">{t('envEvaluation')}</h1>
        </div>
    );

}

export default EnvEvaluation;
