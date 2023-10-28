import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash, faX} from '@fortawesome/free-solid-svg-icons';

import React, {useEffect, useState} from "react";
import State from "../util/State";
import {useTranslation} from "react-i18next";
import PDFPreview from "../util/PDF/PDFPreview";
import CvFile from "../../model/CvFile";
import {axiosInstance} from "../../App";

const ShortContract = () => {
    const [t, i18n] = useTranslation();
    const [isHovered, setIsHovered] = useState(false);
    const [isDisplayed, setIsDisplayed] = useState(false);
    const [contract, setContract] = useState({});

    useEffect(() => {
        let newContract = {};
        axiosInstance.get('manager/contract/1').then((response) => {
            console.log(response.data);
            newContract.data = response.data;
            newContract.title = "test"
            newContract.studentName = "Test Test"
            newContract.organisationName = "We rich"
            console.log(newContract)
            setContract(contract);
        }).catch((error) => {
            console.log(error);
        });
    }, []);

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    const handleClick = () => {
        setIsDisplayed(!isDisplayed);
    }

    return (
        <div onClick={handleClick} className={`row ${!isHovered? 'm-2':'m-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-10 col-sm-6">
                        <h5 className="text-dark fw-light pt-1" data-testid="job-title">{contract?.title}</h5>
                        <p className="text-dark fw-light mb-3" data-testid="student-name">{t(contract?.studentName)}</p>
                        <p className="text-dark fw-light mb-3" data-testid="employer-name">{t(contract?.organisationName)}</p>
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
