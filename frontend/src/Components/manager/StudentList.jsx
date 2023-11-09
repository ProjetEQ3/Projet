import React, {useState} from "react";
import {useTranslation} from "react-i18next";
import ShortStudent from "./ShortStudent";
import ShortCv from "./ShortCv";
import FilterObjectList from "../util/FilterObjectList";

const StudentList = ({students}) => {
    const {t} = useTranslation();

    const renderFilteredStudents = (filteredStudents) => {
        return (
            <div className="col-12">
                {
                    filteredStudents.length !== 0 ?
                        filteredStudents.map((student, index) => (
                            <div key={index}>
                                <ShortStudent student={student} index={index}/>
                            </div>)) :
                        <div className="col-12 text-center">
                            <h4 className="text-dark fw-light">{t('noStudent')}</h4>
                        </div>
                }
            </div>
        )
    }

    return (
        <div className="row">
            <div className="col-12">
                <h3 className="text-dark fw-light my-5">{t('studentList')}</h3>
                <div className="row justify-content-around">
                    <div className="col-12">
                        <FilterObjectList
                            items={students}
                            attributes={['firstName:' + t('firstName'),'lastName:' + t('lastName'),'matricule:'+t('matricule')]}
                            renderItem={renderFilteredStudents}
                        />
                    </div>
                </div>
            </div>
        </div>
    )
}

export default StudentList