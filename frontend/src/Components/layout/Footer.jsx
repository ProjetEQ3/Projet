import React from 'react';
import {useTranslation} from "react-i18next";

function Footer() {
    const {t} = useTranslation()

    return (
        <footer className="App-footer col-12">
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
export default Footer;