import React from "react";

const ShortStudentInfo = ({ student }) => {
    return (
        <div className="m-2 p-2 bg-light border border-dark">
            <p>{student.firstName} {student.lastName}, {student.cvFile}</p>
        </div>
    )
}
export default ShortStudentInfo;