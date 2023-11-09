import React from 'react';
import ShortStudentInfo from "./ShortStudentInfo";

const StudentList = ({ offer, setSelectedOffer }) => {
    const filterApplicationsList = (jobApplicationId) => {
        const updatedOffer = {
            ...offer,
            applications: offer.applications.filter((application) => application.id !== jobApplicationId)
        };
        setSelectedOffer(updatedOffer);
    }

    console.log(offer);
    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {offer?.applications?.map((application, index) => (
                        <div key={index}>
                            <ShortStudentInfo student={application.student} filterApplicationsList={filterApplicationsList} jobApplicationId={application.id}/>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}

export default StudentList;
