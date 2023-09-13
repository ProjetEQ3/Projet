import React, { useState } from 'react';
import Header from '../layout/Header';
import './RegistrationForm.css'
import StudentForm from "./StudentForm";
import EmployerForm from "./EmployerForm";

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
            <Header/>
            <div className="m-2"></div>
            <div className="container bg-light">
                <div className="row">
                    <div className="col-8 mx-auto btn-group btn-group-lg">
                        <button className={`btn btn-outline-ose text-ose ${studActive ? 'active' : ''}`} onClick={() => handleButtonClick('stud')}>Étudiant</button>
                        <button className={`btn btn-outline-ose text-ose ${empActive ? 'active' : ''}`} onClick={() => handleButtonClick('emp')}>Employeur</button>
                    </div>
                </div>
                {
                    studActive ?
                        <StudentForm/> : <EmployerForm/>
                }
            </div>
        </div>
    )
}

export default RegistrationForm