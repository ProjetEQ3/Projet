import React, { useState } from 'react';
import RegisterStudentForm from "./RegisterStudentForm";
import RegisterEmployerForm from "./RegisterEmployerForm";

const RegistrationForm = () => {
    const [empActive, setEmpActive] = useState(false);
    const [studActive, setStudActive] = useState(true);

    const handleButtonClick = (type) => {
        if (type === 'emp') {
            setEmpActive(true);
            setStudActive(false);
        } else {
            setEmpActive(false);
            setStudActive(true);
        }
    };

    return (
        <div className="container mt-2">
            <div className="row">
                <div className="col-md-6 col-12 mx-auto btn-group btn-group-lg">
                    <button className={`btn btn-outline-ose text-ose ${studActive ? 'active' : ''}`} onClick={() => handleButtonClick('stud')}>Étudiant</button>
                    <button className={`btn btn-outline-ose text-ose ${empActive ? 'active' : ''}`} onClick={() => handleButtonClick('emp')}>Employeur</button>
                </div>
            </div>
            {
                studActive ?
                    <RegisterStudentForm/> : <RegisterEmployerForm/>
            }
        </div>
    )
}

export default RegistrationForm