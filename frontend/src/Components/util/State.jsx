import React from "react";

const State = ({ state }) => {
    console.log(state)
    return (
        <>
            {
                state === 'OPEN' ?
                <div className="border rounded px-2 border-success text-success">
                    Ouvert aux candidatures
                </div>
                :
                state === 'SUBMITTED' ?
                <div className="border rounded px-2 border-secondary text-secondary">
                    Attente d'approbation
                </div>
                :
                state === 'REFUSED' ?
                <div className="border rounded px-2 border-danger text-danger">
                    Refusé
                </div>
                :
                state === 'TAKEN' ?
                <div className="border rounded px-2 border-primary text-primary">
                    Pris
                </div>
                :

                state === 'PENDING' ?
                <div className="border rounded px-2 border-warning text-warning">
                    En attente
                </div>
                :
                state === 'EXPIRED' ?
                <div className="border rounded px-2 border-danger text-danger">
                    Expiré
                </div>
                :
                state === 'ACCEPTED' ?
                <div className="border rounded px-2 border-success text-success">
                    Accepté
                </div>
                :
                ""
            }
        </>
    )
}

export default State;