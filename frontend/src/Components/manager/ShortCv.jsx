import React from "react";

const ShortCv = ({cv}) => {
    return (
        <div className="row">
            <div className="col-12">
                <div className="row">
                    <div className="col-12">
                        <h4 className="text-dark fw-light my-5">Nom du fichier : {cv.fileName}</h4>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default ShortCv;