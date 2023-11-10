import {useTranslation} from "react-i18next";
import ShortStudent from "./ShortStudent";
import FilterObjectList from "../util/FilterObjectList";

const StudentList = ({students}) => {
    const {t} = useTranslation();

    const renderFilteredStudents = (filteredStudents) => {
        return (
            <div className="col-12">
                {
                    filteredStudents.length !== 0 ?
                        filteredStudents.map((student, index) => (
                            <div key={index}>
                                <ShortStudent student={student} index={index}/>
                            </div>)) :
                        <div className="col-12 text-center">
                            <h4 className="text-dark fw-light">{t('noStudent')}</h4>
                        </div>
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
                        items={students.map(student => {student.studentState = 'STUDENT_'+student.studentState; return student})}
                        attributes={['firstName:' + t('firstName'),'lastName:' + t('lastName'),'matricule:'+ t('matricule'), 'studentState.select:Status']}
                        renderItem={renderFilteredStudents}
                        selectOptions={{studentState: ['STUDENT_NO_CV', 'STUDENT_COMPLETE', 'STUDENT_NO_JOB_APPLICATION', 'STUDENT_NO_APPOINTMENT', 'STUDENT_NO_CONTRACT']}}
                    />
                </div>
            </div>
        </div>
    )
}

export default StudentList