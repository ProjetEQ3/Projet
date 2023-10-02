import React from 'react'
import ShortStudentInfo from "./ShortStudentInfo";

const StudentList = (students) => {
    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {students.map((student, index) => (
                        <div key={index}>
                            <ShortStudentInfo student={student}/>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}

export default StudentList