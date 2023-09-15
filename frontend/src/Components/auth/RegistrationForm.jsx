import React, { useState } from 'react';
import './RegistrationForm.css'
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
        <div className="bg-light">
            <div className="m-2"></div>
            <div className="container bg-light">
                <div className="row">
                    <div className="col-6 mx-auto btn-group btn-group-lg">
                        <button className={`btn btn-outline-ose text-ose ${studActive ? 'active' : ''}`} onClick={() => handleButtonClick('stud')}>Étudiant</button>
                        <button className={`btn btn-outline-ose text-ose ${empActive ? 'active' : ''}`} onClick={() => handleButtonClick('emp')}>Employeur</button>
                    </div>
                </div>
                {
                    studActive ?
                        <RegisterStudentForm/> : <RegisterEmployerForm/>
                }
            </div>
        </div>
    )
}

export default RegistrationForm