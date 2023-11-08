import React from "react";
import {t} from "i18next";

const StudentState = ({ state }) => {
    const classes = state === 'COMPLETE' || state === 'ACCEPTED' ? 'border-success text-success' :
            state === 'NO_CV' || state === 'NO_JOB_APPLICATION'? 'border-danger text-danger' :
                    state === 'NO_APPOINTMENT' || state === 'NO_CONTRACT' || state === 'SUBMITTED' || state === 'CONVOKED' || state === 'WAITING_APPOINTMENT' ? 'border-warning text-warning' : '';
    console.log(state);
    return (
        <>
            {
                <div className={classes + " border rounded col-12 col-lg-10 float-end"}>{t('STUDENT_'+state)}</div>
            }
        </>
    )
}

export default StudentState;