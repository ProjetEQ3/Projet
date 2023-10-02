import React from "react";

const ShortStudentInfo = ({ student }) => {
    return (
        <div className="m-2 p-2 bg-white border rounded border-ose d-flex">
            <div className="col-12 col-lg-6">
                <h3 className="text-dark fw-light">{student.firstName + " " + student.lastName + " - " + student.matricule}</h3>
                <p className="text-dark fw-light">{student.email}</p>
            </div>
        </div>
    )
}
export default ShortStudentInfo;