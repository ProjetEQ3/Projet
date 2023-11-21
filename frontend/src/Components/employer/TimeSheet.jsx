import React, {useState} from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft, faPaperPlane} from "@fortawesome/free-solid-svg-icons";
import Loading from "../util/Loading";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {useDarkMode} from "../../context/DarkModeContext";
import { useLocation } from 'react-router-dom';

const TimeSheet = ({ user }) => {
    const [t] = useTranslation()
    const navigate = useNavigate()
    const [isLoading, setIsLoading] = useState(false)
    const { darkMode } = useDarkMode();
    const location = useLocation();
    const { jobApplication } = location.state || {};

    const handleBack = () => {
        navigate(-1)
    }

    const handleSubmit = (e) => {
        e.preventDefault()
    }

    return (
        <div className="container">
            <button className="btn btn-outline-ose" onClick={handleBack}>
                <FontAwesomeIcon icon={faArrowLeft} className="fa-2 me-2"/>
                {t('back')}
            </button>
            <h1 className="text-center fw-light">{t('timeSheet')}</h1>
            {isLoading ? (
                <Loading/>
            ) : (
                <div className="row">
                    <div className="col-9 mx-auto">
                        <form className="form-group" onSubmit={handleSubmit}>

                        </form>
                    </div>
                </div>
            )}
        </div>
    )

}

export default TimeSheet;
