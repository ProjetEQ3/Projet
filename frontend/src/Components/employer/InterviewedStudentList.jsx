import React, {useEffect, useState} from 'react';
import ShortInterviewedStudentInfo from "./ShortInterviewedStudentInfo";
import {toast} from "react-toastify";
import {axiosInstance} from "../../App";

const StudentList = ({user}) => {
    const [studentList, setStudentList] = useState([]);

    useEffect(() => {
        axiosInstance.get('API/TO/DEFINE', {params: {employerId: user.id}})
            .then(res => {
                setStudentList(res.data)
            })
            .catch(err => {
                toast.error(err.message)
            })
    }, []);

    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {studentList.map((student, index) => (
                        <div key={index}>
                            <ShortInterviewedStudentInfo student={student} jobOfferTitle={student.jobTitle}/>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default StudentList;
