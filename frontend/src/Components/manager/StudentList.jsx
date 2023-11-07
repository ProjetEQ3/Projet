import React, {useState} from "react";
import {useTranslation} from "react-i18next";
import ShortStudent from "./ShortStudent";

const StudentList = ({students}) => {
    const {t} = useTranslation();

    return (
        <div className="row">
            <div className="col-12">
                <h3 className="text-dark fw-light my-5">{t('studentList')}</h3>
                <div className="row justify-content-around">
                    <div className="col-12">
                        {
                            students.map((student, index) => (
                                <div key={index}>
                                    <ShortStudent student={student} index={index}/>
                                </div>))
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default StudentList