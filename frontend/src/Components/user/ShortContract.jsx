import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import PDFPreview from "../util/PDF/PDFPreview";
import CvFile from "../../model/CvFile";
import {axiosInstance} from "../../App";
import Contract from "../../model/Contract";

const ShortContract = ({ contract }) => {
    const [t] = useTranslation();
    const [isDisplay, setIsDisplay] = useState(false);

    const handleClick = () => {
        setIsDisplay(!isDisplay);
    }

    return (
        <div className={'row'}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-12 d-flex justify-content-around align-items-baseline">
                        <h5 className="text-dark fw-light pt-1" data-testid="job-title">{contract.jobOfferName}</h5>
                        <p className="text-dark fw-light mb-3" data-testid="student-name">{t(contract.studentName)}</p>
                        <p className="text-dark fw-light mb-3" data-testid="employer-name">{t(contract.jobOfferCompany)}</p>
                        <button onClick={handleClick} className="btn btn-outline-ose btn-sm" data-testid="preview-btn">{t('preview')}</button>
                    </div>
                </div>
                {
                    isDisplay ? (
                            <PDFPreview file={CvFile.readBytes(contract.data)} setIsDisplay={setIsDisplay} contractComplete={contract.isComplete}/>
                        ) : null
                }
            </div>
        </div>
    );
}

export default ShortContract;
