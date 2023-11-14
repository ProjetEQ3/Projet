import {useTranslation} from "react-i18next";
import ShortStudent from "./ShortStudent";
import FilterObjectList from "../util/FilterObjectList";
import {useDarkMode} from "../../context/DarkModeContext";

const StudentList = ({students}) => {
    const {t} = useTranslation();

    const { darkMode } = useDarkMode();

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
                            <h4 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light`}>{t('noStudent')}</h4>
                        </div>
                }
            </div>
        )
    }

    return (
        <div className="row">
            <div className="col-12">
                <h3 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light my-5`}>{t('studentList')}</h3>
                <div className="row justify-content-around">
                    <FilterObjectList
                        items={students.map(student => {
                            if (student.studentState === 'NO_CV') student.studentState = 'STUDENT_NO_CV';
                            if (student.studentState === 'COMPLETE') student.studentState = 'STUDENT_COMPLETE';
                            if (student.studentState === 'NO_JOB_APPLICATION') student.studentState = 'STUDENT_NO_JOB_APPLICATION';
                            if (student.studentState === 'NO_APPOINTMENT') student.studentState = 'STUDENT_NO_APPOINTMENT';
                            if (student.studentState === 'NO_CONTRACT') student.studentState = 'STUDENT_NO_CONTRACT';
                            return student
                        })}
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