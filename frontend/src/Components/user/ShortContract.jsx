import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash, faX} from '@fortawesome/free-solid-svg-icons';

import React, {useEffect, useState} from "react";
import State from "../util/State";
import {useTranslation} from "react-i18next";
import PDFPreview from "../util/PDF/PDFPreview";
import CvFile from "../../model/CvFile";
import {axiosInstance} from "../../App";

const ShortContract = (contractId) => {
    const [t, i18n] = useTranslation();
    const [isDisplayed, setIsDisplayed] = useState(false);
    const [contract, setContract] = useState({});

    useEffect(() => {
        let newContract = {};
        axiosInstance.get('manager/contract/${contractId}').then((response) => {
            console.log(response.data);
            newContract.data = response.data;
            newContract.title = "test"
            newContract.studentName = "Test Test"
            newContract.organisationName = "We rich"
            setContract(newContract);
        }).catch((error) => {
            console.log(error);
        });
    }, []);

    const handleClick = () => {
        setIsDisplayed(!isDisplayed);
    }

    return (
        <div className={'row'}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-12 d-flex justify-content-around align-items-baseline">
                        <h5 className="text-dark fw-light pt-1" data-testid="job-title">{contract?.title}</h5>
                        <p className="text-dark fw-light mb-3" data-testid="student-name">{t(contract?.studentName)}</p>
                        <p className="text-dark fw-light mb-3" data-testid="employer-name">{t(contract?.organisationName)}</p>
                        <button onClick={handleClick} className="btn btn-outline-ose btn-sm" data-testid="preview-btn">{t('preview')}</button>
                    </div>
                </div>
                {
                    isDisplayed ? (
                            <PDFPreview file={CvFile.readBytes(contract.data)} setIsDisplay={setIsDisplayed}/>
                        ) : null
                }
            </div>
        </div>
    );
}

export default ShortContract;
