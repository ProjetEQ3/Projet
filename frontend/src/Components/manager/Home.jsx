
import {useTranslation} from "react-i18next";
import {faCircleExclamation} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const Home = ({setTab, setIdElement, nbCvs}) => {
    const {t} = useTranslation();

    return (
        <div className="container-fluid row px-lg-5 px-2 py-2">
            <h1>{t('home')}</h1>
            {
                nbCvs > 0 &&
                <div className="col-12">
                    <h5 className="rounded rounded-3 alert alert-danger p-2 px-4 display-6 clickable"
                        onClick={() => setTab('cvs')}>
                        <FontAwesomeIcon icon={faCircleExclamation} className="me-4" />
                        {t('youHave')} <strong >{nbCvs}</strong>  {t('cvToTreat')}
                    </h5>
                </div>
            }
        </div>
    )
}

export default Home