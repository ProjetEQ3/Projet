import React from "react";
import {t} from "i18next";

const State = ({ state }) => {
    const classes = state === 'OPEN' ? 'border-success text-success' :
        state === 'SUBMITTED' ? 'border-secondary text-secondary' :
            state === 'REFUSED' ? 'border-danger text-danger' :
                state === 'TAKEN' ? 'border-primary text-primary' :
                    state === 'PENDING' ? 'border-warning text-warning' :
                        state === 'EXPIRED' ? 'border-danger text-danger' :
                            state === 'ACCEPTED' ? 'border-success text-success' : '';

    return (
        <>
            {
                <div className={classes + " border rounded col-12 col-lg-10 float-end"}>
                    {t(state)}
                </div>
            }
        </>
    )
}

export default State;