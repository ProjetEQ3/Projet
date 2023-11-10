
import {useTranslation} from "react-i18next";
import {faCircleExclamation} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const Home = ({setTab, setIdElement, nbCvs}) => {
    const {t} = useTranslation();

    function handleCvClick() {
        setTab('cvs');
        setIdElement('cv');
    }

    return (
        <div className="container-fluid row px-lg-5 px-2 py-2">
            {
                nbCvs === 0 &&
                <div className="col-12 text-center">
                    <h1 className="rounded rounded-3 alert alert-success p-2 px-4 display-3">
                        😊<br />{t('allTreated')}
                    </h1>
                </div>
            }
            {
                nbCvs > 0 &&
                <div className="col-12">
                    <h5 className="rounded rounded-3 alert alert-danger p-2 px-4 display-6 clickable"
                        onClick={handleCvClick}>
                        <FontAwesomeIcon icon={faCircleExclamation} className="me-4" />
                        {t('youHave')} <strong >{nbCvs}</strong>  {t('cvToTreat')}
                    </h5>
                </div>
            }
        </div>
    )
}

export default Home