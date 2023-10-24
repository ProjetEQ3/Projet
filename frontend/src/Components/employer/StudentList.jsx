import React from 'react';
import ShortStudentInfo from "./ShortStudentInfo";

const StudentList = ({ offer, setSelectedOffer }) => {
    const filterStudentList = (jobApplicationId) => {
        const updatedOffer = {
            ...offer,
            students: offer.students.filter((student) => student.jobApplications[0] !== jobApplicationId)
        };
        setSelectedOffer(updatedOffer);
    }

    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {offer.students.map((student, index) => (
                        <div key={index}>
                            <ShortStudentInfo student={student} filterStudentList={filterStudentList}/>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default StudentList;
