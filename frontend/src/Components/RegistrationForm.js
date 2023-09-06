import React, { useState } from 'react';
import './RegistrationForm.css'

const RegistrationForm = () => {
    const [isActive, setIsActive] = useState(false);

    const handleButtonClick = () => {
        setIsActive(!isActive);
    };

    return (
        <div className="bg-light">
            <h1 className="text-danger text-center">Inscription</h1>
            <div className="container bg-light">
                <div className="row">
                    <div className="btn-group btn-group-lg">
                        <button className={`btn ${isActive ? 'active text-primary' : ''}`} onClick={handleButtonClick}>Étudiant</button>
                        <button className={`btn ${!isActive ? 'active text-primary' : ''}`} onClick={handleButtonClick}>Employeur</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default RegistrationForm