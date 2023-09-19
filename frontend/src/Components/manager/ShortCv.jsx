import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


const ShortCv = ({cv}) => {
    return (
        <div className="row">
            <div className="col-12">
                <div className="row p-3 bg-white rounded">
                    <div className="col-10">
                        <h4 className="text-dark fw-light">Nom du fichier : {cv.fileName}</h4>
                    </div>
                    <div className="col-2 my-auto d-flex justify-content-around">
                        <button className="btn">
                            <FontAwesomeIcon icon={}/>
                        </button>
                        <button className="btn">
                            <FontAwesomeIcon icon={}/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default ShortCv;