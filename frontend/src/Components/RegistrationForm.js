import React, { useState } from 'react';

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
                        <button className={"btn text-dark ${isActive ? 'active text-primary' : ''}"}>Étudiant</button>
                        <button className={"btn text-dark ${!isActive ? 'active text-primary' : ''}"}>Employeur</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default RegistrationForm;