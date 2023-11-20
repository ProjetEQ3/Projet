import {useTranslation} from "react-i18next";

const HiredStudents = ({ jobApplications }) => {
    const [t] = useTranslation();

    return (
        <div>
            <h1>{t('hiredStudents')}</h1>
            <div className="row">

            </div>
        </div>
    )
}
export default HiredStudents;