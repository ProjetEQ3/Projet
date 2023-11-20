import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";

const HiredStudents = ({ jobApplications }) => {
    const [t] = useTranslation();
    const navigate = useNavigate()

    const handleTimeSheetButton = () => {
        navigate('/employer/timeSheet')
    }

    return (
        <div>
            <h1>{t('hiredStudents')}</h1>
            <div className="row">
                <button className="btn btn-outline-ose col-12" onClick={handleTimeSheetButton}>BOUTON TEMPORAIRE POUR FEUILLE DE TEMPS</button>
            </div>
        </div>
    )
}
export default HiredStudents;