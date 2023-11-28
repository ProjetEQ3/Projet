import React from 'react';
import {useLocation} from "react-router-dom";

function EnvEvaluation({ user }) {
    const location = useLocation();
    const { application } = location.state || {};

    return (
        <p>{application.jobOffer.title}</p>
    );

}

export default EnvEvaluation;
