import React from 'react';
import ShortStudentInfo from "./ShortStudentInfo";
import {useTranslation} from "react-i18next";
import {Axios} from "axios";
import {axiosInstance} from "../../App";

const StudentList = ({ offer }) => {
    const { t } = useTranslation();
    const handleAccept = (e) => {
        e.preventDefault();
        axiosInstance.post('/employer/offer/accept/{jobApplicationId}', offer)
    }

    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {offer.students.map((student, index) => (
                        <div key={index}>
                            <ShortStudentInfo student={student} />
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default StudentList;
