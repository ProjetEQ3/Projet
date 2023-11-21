import {useTranslation} from "react-i18next";

const HiredStudents = ({ jobApplications }) => {
    const [t] = useTranslation();
    console.log(jobApplications)

    return (
        <div>
            <h1>{t('hiredStudents')}</h1>
            {jobApplications.map((jobApplication) => (
                <div className="row">
                    <div className="col-12">
                        {jobApplication.student.firstName} {jobApplication.student.lastName}
                    </div>
                </div>
                )
            )}
        </div>
    )
}
export default HiredStudents;