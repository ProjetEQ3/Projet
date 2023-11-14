import React from 'react';
import ShortStudentInfo from "./ShortStudentInfo";

const StudentList = ({ offer, setSelectedOffer, getNbPostulations, getOffersWithSubmittedApplications }) => {
    const filterApplicationsList = (jobApplicationId) => {
        const updatedOffer = {
            ...offer,
            applications: offer.applications.filter((application) => application.id !== jobApplicationId)
        };
        setSelectedOffer(updatedOffer);
    }

    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {offer?.applications?.map((application, index) => (
                        <div key={index}>
                            <ShortStudentInfo application={application} filterApplicationsList={filterApplicationsList} getNbPostulations={getNbPostulations} getOffersWithSubmittedApplications={getOffersWithSubmittedApplications}/>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}

export default StudentList;
