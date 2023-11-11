import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import PDFPreview from "../util/PDF/PDFPreview";
import CvFile from "../../model/CvFile";
import {axiosInstance} from "../../App";
import Contract from "../../model/Contract";
import {toast} from "react-toastify";

const ShortContract = ({ contract, user }) => {
    const [t] = useTranslation();
    const [isDisplay, setIsDisplay] = useState(false);
    const [showSigningModal, setShowSigningModal] = useState(false);
    const [signaturePassword, setSignaturePassword] = useState('');

    const handleSignClick = () => {
        setShowSigningModal(true);
        setIsDisplay(false)
    }

    const handleSignaturePasswordChange = (e) => {
        setSignaturePassword(e.target.value);
    }

    const handleSignatureSubmit = () => {
        axiosInstance.post(`/${user.role.split('_')[1].toLowerCase()}/contract/sign/${contract.id}`,{
            email: user.email,
            password: signaturePassword,
        })
            .then(response => {
                contract.data = response.data.data;
                contract.isComplete = response.data.complete;
                toast.success(t('contractSigned'));
            })
            .catch(error => {
                toast.error(t(error.response?.data?.message))
            })
        setShowSigningModal(false);
        setSignaturePassword('');
    }

    const handleCloseModal = () => {
        setShowSigningModal(false);
        setSignaturePassword('')
    }

    const handleClick = () => {
        setIsDisplay(!isDisplay);
    }

    return (
        <div className={'row'}>
            <div className="col-12 bg-white rounded pt-1">
                <div className="row">
                    <div className="col-12 d-flex justify-content-around align-items-baseline">
                        <h5 className="text-dark fw-light pt-1" data-testid="job-title">{contract.jobOfferName}</h5>
                        <h5 className="text-dark fw-light mb-3" data-testid="student-name">{t(contract.studentName)}</h5>
                        <h5 className="text-dark fw-light mb-3" data-testid="employer-name">{t(contract.jobOfferCompany)}</h5>
                        <button onClick={handleClick} className="btn btn-outline-ose btn-sm" data-testid="preview-btn">{t('preview')}</button>
                        {
                            contract.complete ?
                                <div className="text-success fw-bold">{t('complete')}</div>
                                :
                            user.role === 'ROLE_EMPLOYER' &&
                            contract.employerSignature !== null ?
                                <div className="text-success fw-bold">{t('signed')}</div>
                                :
                                user.role === 'ROLE_MANAGER' &&
                                contract.managerSignature !== null ?
                                    <div className="text-success fw-bold">{t('signed')}</div>
                                    :
                                    user.role === 'ROLE_STUDENT' &&
                                    contract.studentSignature !== null ?
                                        <div className="text-success fw-bold">{t('signed')}</div>
                                        :
                                <button onClick={handleSignClick} className="btn btn-primary btn-sm" data-testid="sign-btn">{t('sign')}</button>
                        }
                    </div>
                </div>
                {
                    isDisplay ? (
                            <PDFPreview file={CvFile.readBytes(contract.data)} setIsDisplay={setIsDisplay} contractComplete={contract.isComplete}/>
                        ) : null
                }
            </div>

            {showSigningModal && (
                <div className="modal show" tabIndex="-1" style={{ display: 'block' }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">{t('signContract')}</h5>
                                <button type="button" className="btn-close" onClick={handleCloseModal}></button>
                            </div>
                            <div className="modal-body">
                                <div className="mb-3">
                                    <label className="form-label">{t('fullName') + " : "}</label>
                                    <p>{user.firstName + " " + user.lastName}</p>
                                </div>
                                <div className="mb-3">
                                    <div className="mb-3">
                                        <label htmlFor="password" className="form-label">{t('password') + " : "}</label>
                                        <input id="password" type="password" className="form-control" value={signaturePassword} onChange={handleSignaturePasswordChange} />
                                    </div>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" onClick={handleCloseModal}>
                                    {t('close')}
                                </button>
                                <button type="button" className="btn btn-primary" onClick={handleSignatureSubmit}>
                                    {t('submit')}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}

            {showSigningModal && <div className="modal-backdrop fade show"></div>}

        </div>
    );
}

export default ShortContract;
