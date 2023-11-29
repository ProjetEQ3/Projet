import React from "react";
import {t} from "i18next";

const State = ({ state }) => {
    const classes = state === 'OPEN' || state === 'ACCEPTED' ? 'border-success text-success' :
        state === 'REFUSED' ? 'border-secondary text-secondary' :
            state === 'SUBMITTED' || state === 'EXPIRED' ? 'border-danger text-danger' :
                state === 'TAKEN' ? 'border-primary text-primary' :
                    state === 'PENDING'? 'border-warning text-warning' : '';
    return (
        <>
            {
                <div className={classes + " border border-3 rounded rounded-5 col-12 col-lg-10 float-end fw-bold"}>{t(state)}</div>
            }
        </>
    )
}

export default State;