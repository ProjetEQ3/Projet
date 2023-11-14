import React from "react";
import {t} from "i18next";

const StudentState = ({ state }) => {
    const classes = state === 'STUDENT_COMPLETE' || state === 'STUDENT_ACCEPTED' ? 'border-success text-success' :
            state === 'STUDENT_NO_CV' || state === 'STUDENT_NO_JOB_APPLICATION'? 'border-danger text-danger' :
                    state === 'STUDENT_NO_APPOINTMENT' || state === 'STUDENT_NO_CONTRACT' || state === 'STUDENT_SUBMITTED' || state === 'STUDENT_CONVOKED' || state === 'STUDENT_WAITING_APPOINTMENT' ? 'border-warning text-warning' : '';
    return (
        <>
            {
                <div className={classes + " border border-3 rounded rounded-5 col-12 col-lg-10 float-end fw-bold"}>{t(state)}</div>
            }
        </>
    )
}

export default StudentState;