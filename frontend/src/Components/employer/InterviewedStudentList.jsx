import React, {useEffect, useState} from 'react';
import ShortInterviewedStudentInfo from "./ShortInterviewedStudentInfo";
import {toast} from "react-toastify";
import {axiosInstance} from "../../App";
import {useTranslation} from "react-i18next";

const StudentList = ({user}) => {
    const {t} = useTranslation();
    const [studentList, setStudentList] = useState([]);
    async function fetchStudentList() {
        await axiosInstance.get('employer/waitingStudents', {params: {employerId: user.id}})
            .then(res => {
                if (res.data.length === 0) {
                    toast.info(t('noStudentsConvoked'));
                    return;
                }
                fetchStudentsJobTitles(res.data);
            })
            .catch(err => {
                toast.error(err.message)
            })
    }
    async function fetchStudentsJobTitles(fetchedStudentList) {
        for (let student of fetchedStudentList) {
            await axiosInstance.get('employer/offerByApplication', {params: {applicationId: student.jobApplications[0]}})
                .then(res => {
                    student.jobTitle = res.data.title;
                })
                .catch(err => {
                    toast.error(err.message)
                })
        }
        setStudentList(fetchedStudentList)
    }

    useEffect(() => {
        fetchStudentList();
    }, []);

    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {
                        studentList.length === 0 ? <h3 className="text-center">{t('noStudentsConvoked')}</h3>
                            : studentList.map((student, index) => (
                        <div key={index}>
                            <ShortInterviewedStudentInfo student={student}/>
                        </div>
                        ))
                    }
                </div>
            </div>
        </div>
    );
}

export default StudentList;
