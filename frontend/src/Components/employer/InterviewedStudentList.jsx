import React, {useEffect, useState} from 'react';
import ShortInterviewedStudentInfo from "./ShortInterviewedStudentInfo";
import {toast} from "react-toastify";
import {axiosInstance} from "../../App";
import {useTranslation} from "react-i18next";

const StudentList = ({user}) => {
    const {t} = useTranslation();
    const [studentList, setStudentList] = useState([]);

    useEffect(() => {
        axiosInstance.get('employer/waitingStudents', {params: {employerId: user.id}})
            .then(res => {
                if (res.data.length === 0) {
                    toast.info(t('noStudentsConvoked'));
                    return;
                }
                setStudentList(res.data);
                let newStudentList;
                for (let student of studentList) {
                    axiosInstance.get('employer/offerByApplication', {params: {jobApplicationId: res.data[0].jobApplications[0]}})
                        .then(res => {
                            student.jobTitle = res.data.title;
                            newStudentList = [...newStudentList, student];
                        })
                        .catch(err => {
                            toast.error(err.message)
                        })
                }
                setStudentList(newStudentList);
            })
            .catch(err => {
                toast.error(err.message)
            })
    }, []);

    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {
                        studentList.length === 0 ? <h3 className="text-center">{t('noStudentsConvoked')}</h3>
                            : studentList.map((student, index) => (
                        <div key={index}>
                            <ShortInterviewedStudentInfo student={student} jobOfferTitle={student.jobTitle}/>
                        </div>
                        ))
                    }
                </div>
            </div>
        </div>
    );
}

export default StudentList;
