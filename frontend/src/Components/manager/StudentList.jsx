import {useTranslation} from "react-i18next";
import ShortStudent from "./ShortStudent";
import FilterObjectList from "../util/FilterObjectList";

const StudentList = ({students}) => {
    const {t} = useTranslation();

    const renderFilteredOffers = (filteredOffers) => {
        return (
            <div className="col-12">
                {
                    filteredOffers.map((student, index) => (
                        <div key={index}>
                            <ShortStudent student={student} index={index}/>
                        </div>))
                }
            </div>
        )
    }

    return (
        <div className="row">
            <div className="col-12">
                <h3 className="text-dark fw-light my-5">{t('studentList')}</h3>
                <div className="row justify-content-around">
                    <FilterObjectList
                        items={students}
                        attributes={['studentState.select:Status']}
                        renderItem={renderFilteredOffers}
                        selectOptions={{studentState: ['STUDENT_NO_CV', 'STUDENT_COMPLETE', 'STUDENT_NO_JOB_APPLICATION', 'STUDENT_APPOINTMENT', 'STUDENT_NO_CONTRACT']}}
                    />
                </div>
            </div>
        </div>
    )
}

export default StudentList