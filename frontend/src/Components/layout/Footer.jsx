import React from 'react';
import { useTranslation } from "react-i18next";
import { useDarkMode } from "../../context/DarkModeContext";

function Footer() {
    const {t} = useTranslation()
    const footerStyle = {
        position: 'absolute',
        bottom: 0,
    }
    const { darkMode } = useDarkMode();

    return (
        <footer style={footerStyle} className={`App-footer col-12 ${darkMode ? 'bg-dark text-light' : ''}`}>
            <div className="container-fluid">
                <div className="row">
                    <div className="col-12 d-none d-md-block text-center">
                        <p className="lead">{t('glucoseAcronym')} - {t('glucoseFullAcronym')}</p>
                        <p className="lead">{t('copyRights')}</p>
                    </div>
                    <div className="col-12 d-block d-md-none text-center">
                        <p className="lead"><small>{t('glucoseAcronym')} - {t('glucoseFullAcronym')}</small></p>
                        <p className="lead"><small>{t('copyRights')}</small></p>
                    </div>
                </div>
            </div>
        </footer>
    )
}

export default Footer
