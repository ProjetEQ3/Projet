import React, {useEffect, useState} from 'react';
import ShortInterviewedStudentInfo from "./ShortInterviewedStudentInfo";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";
import {axiosInstance} from "../../App";

const StudentList = ({user}) => {
    const { t } = useTranslation();
    const [studentList, setStudentList] = useState([]);

    useEffect(() => {
        // TODO : Le call API qui est ici doit retourner une liste d'étudiants qui ont postulé à une offre de l'employeur (Tu peux voir avec les jobApplicationsIds)
        axiosInstance.get('API/TO/DEFINE', {params: {employerId: user.id}})
            .then(res => {
                // TODO : Ici, tu dois mettre une liste d'étudiants mais aussi le titre de l'offre à laquelle ils ont postulé
                // TODO : Tu peux faire un deuxième call API pour aller chercher le titre de l'offre à partir de l'id de l'application
                // TODO : J'ai mi que student.JobTitle donne le titre de l'offre mais tu peux changer ça si tu veux
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
