import {useTranslation} from "react-i18next";
import {useLocation, useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons";
import React, {useState} from "react";
import {useDarkMode} from "../../context/DarkModeContext";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";

const StudentEval = ({ user }) => {
    const { t } = useTranslation();
    const location = useLocation();
    const { jobApplication } = location.state || {};
    const student = jobApplication.student;
    const navigate = useNavigate();
    const { darkMode } = useDarkMode();
    const [signaturePassword, setSignaturePassword] = useState('');

    const handleBack = () => {
        navigate(-1);
    };

    // TODO : use FormData to send the evaluation
    const sendEvaluation = () => {
        let sections = [];
        let section1 = [];
        let section2 = [];
        let section3 = [];
        let section4 = [];
        let section5 = [];
        let section6 = [];
        section1.push(document.querySelector('input[name="1a"]:checked').value);
        section1.push(document.querySelector('input[name="1b"]:checked').value);
        section1.push(document.querySelector('input[name="1c"]:checked').value);
        section1.push(document.querySelector('input[name="1d"]:checked').value);
        section1.push(document.querySelector('input[name="1e"]:checked').value);
        section1.push(document.querySelector('textarea[id="1"].value'));
        section2.push(document.querySelector('input[name="2a"]:checked').value);
        section2.push(document.querySelector('input[name="2b"]:checked').value);
        section2.push(document.querySelector('input[name="2c"]:checked').value);
        section2.push(document.querySelector('input[name="2d"]:checked').value);
        section2.push(document.querySelector('input[name="2e"]:checked').value);
        section2.push(document.querySelector('textarea[id="2"].value'));
        section3.push(document.querySelector('input[name="3a"]:checked').value);
        section3.push(document.querySelector('input[name="3b"]:checked').value);
        section3.push(document.querySelector('input[name="3c"]:checked').value);
        section3.push(document.querySelector('input[name="3d"]:checked').value);
        section3.push(document.querySelector('input[name="3e"]:checked').value);
        section3.push(document.querySelector('input[name="3f"]:checked').value);
        section3.push(document.querySelector('textarea[id="3"].value'));
        section4.push(document.querySelector('input[name="4a"]:checked').value);
        section4.push(document.querySelector('input[name="4b"]:checked').value);
        section4.push(document.querySelector('input[name="4c"]:checked').value);
        section4.push(document.querySelector('input[name="4d"]:checked').value);
        section4.push(document.querySelector('input[name="4e"]:checked').value);
        section4.push(document.querySelector('input[name="4f"]:checked').value);
        section4.push(document.querySelector('textarea[id="4"].value'));
        section5.push(document.querySelector('input[name="globalAppreciationIntern"]:checked').value);
        section5.push(document.querySelector('textarea[id="globalAppreciationIntern"].value'));
        section6.push(document.querySelector('input[name="discussedWithStudent"]:checked').value);
        section6.push(document.querySelector('textarea[id="discussedWithStudent"].value'));
        section6.push(document.querySelector('input[name="takeItAgain"]:checked').value);
        section6.push(document.querySelector('textarea[id="takeItAgain"].value'));
        sections.push(section1);
        sections.push(section2);
        sections.push(section3);
        sections.push(section4);
        sections.push(section5);
        sections.push(section6);

        axiosInstance.post(`/employer/internEvaluation/${jobApplication.id}`, sections)
            .then(() => {
                toast.success(t('evaluationSent'));
            })
            .catch(() => {
                toast.error(t('evaluationNotSent'));
            })
    }

    const handleSignaturePasswordChange = (e) => {
        setSignaturePassword(e.target.value);
    }

    return (
        <div className="container">
            <button className="btn btn-outline-ose" onClick={handleBack}>
                <FontAwesomeIcon icon={faArrowLeft} className="fa-2 me-2"/>
                {t('back')}
            </button>
            <h1 className="text-center fw-light my-2">{t('evaluationOf')} {student.firstName} {student.lastName}</h1>
            <form className="col-10 mx-auto text-center">
                <div className="row">
                    <div className="col-12 text-center border border-ose rounded p-3 my-2">
                        <h3 className="fw-light text-start">1. {t('productivity')}</h3>
                        <p className="fw-light text-start fst-italic mb-1">{t('productivityDescription')}</p>
                        <div className="row">
                            <div className="col-7 text-start fw-bold my-auto">
                                {t('internWasAbleTo')} :
                            </div>
                            <div className="col-1 my-auto">{t('stronglyAgreeing')}</div>
                            <div className="col-1 my-auto">{t('agreeing')}</div>
                            <div className="col-1 my-auto">{t('disagreeing')}</div>
                            <div className="col-1 my-auto">{t('stronglyDisagreeing')}</div>
                            <div className="col-1 my-auto">N/A</div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">a) {t('1a')}</label>
                            <div className="col-1"><input type="radio" name="1a"/></div>
                            <div className="col-1"><input type="radio" name="1a"/></div>
                            <div className="col-1"><input type="radio" name="1a"/></div>
                            <div className="col-1"><input type="radio" name="1a"/></div>
                            <div className="col-1"><input type="radio" name="1a"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">b) {t('1b')}</label>
                            <div className="col-1"><input type="radio" name="1b"/></div>
                            <div className="col-1"><input type="radio" name="1b"/></div>
                            <div className="col-1"><input type="radio" name="1b"/></div>
                            <div className="col-1"><input type="radio" name="1b"/></div>
                            <div className="col-1"><input type="radio" name="1b"/></div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">c) {t('1c')}</label>
                            <div className="col-1"><input type="radio" name="1c"/></div>
                            <div className="col-1"><input type="radio" name="1c"/></div>
                            <div className="col-1"><input type="radio" name="1c"/></div>
                            <div className="col-1"><input type="radio" name="1c"/></div>
                            <div className="col-1"><input type="radio" name="1c"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">d) {t('1d')}</label>
                            <div className="col-1"><input type="radio" name="1d"/></div>
                            <div className="col-1"><input type="radio" name="1d"/></div>
                            <div className="col-1"><input type="radio" name="1d"/></div>
                            <div className="col-1"><input type="radio" name="1d"/></div>
                            <div className="col-1"><input type="radio" name="1d"/></div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">e) {t('1e')}</label>
                            <div className="col-1"><input type="radio" name="1e"/></div>
                            <div className="col-1"><input type="radio" name="1e"/></div>
                            <div className="col-1"><input type="radio" name="1e"/></div>
                            <div className="col-1"><input type="radio" name="1e"/></div>
                            <div className="col-1"><input type="radio" name="1e"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea placeholder={t('comments')}/>
                        </div>
                    </div>
                    <div className="col-12 text-center border border-ose rounded p-3 my-2">
                        <h3 className="fw-light text-start">2. {t('workQuality')}</h3>
                        <p className="fw-light text-start fst-italic mb-1">{t('workQualityDescription')}</p>
                        <div className="row">
                            <div className="col-7 text-start fw-bold my-auto">
                                {t('internWasAbleTo')} :
                            </div>
                            <div className="col-1 my-auto">{t('stronglyAgreeing')}</div>
                            <div className="col-1 my-auto">{t('agreeing')}</div>
                            <div className="col-1 my-auto">{t('disagreeing')}</div>
                            <div className="col-1 my-auto">{t('stronglyDisagreeing')}</div>
                            <div className="col-1 my-auto">N/A</div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">a) {t('2a')}</label>
                            <div className="col-1"><input type="radio" name="2a"/></div>
                            <div className="col-1"><input type="radio" name="2a"/></div>
                            <div className="col-1"><input type="radio" name="2a"/></div>
                            <div className="col-1"><input type="radio" name="2a"/></div>
                            <div className="col-1"><input type="radio" name="2a"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">b) {t('2b')}</label>
                            <div className="col-1"><input type="radio" name="2b"/></div>
                            <div className="col-1"><input type="radio" name="2b"/></div>
                            <div className="col-1"><input type="radio" name="2b"/></div>
                            <div className="col-1"><input type="radio" name="2b"/></div>
                            <div className="col-1"><input type="radio" name="2b"/></div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">c) {t('2c')}</label>
                            <div className="col-1"><input type="radio" name="2c"/></div>
                            <div className="col-1"><input type="radio" name="2c"/></div>
                            <div className="col-1"><input type="radio" name="2c"/></div>
                            <div className="col-1"><input type="radio" name="2c"/></div>
                            <div className="col-1"><input type="radio" name="2c"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">d) {t('2d')}</label>
                            <div className="col-1"><input type="radio" name="2d"/></div>
                            <div className="col-1"><input type="radio" name="2d"/></div>
                            <div className="col-1"><input type="radio" name="2d"/></div>
                            <div className="col-1"><input type="radio" name="2d"/></div>
                            <div className="col-1"><input type="radio" name="2d"/></div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">e) {t('2e')}</label>
                            <div className="col-1"><input type="radio" name="2e"/></div>
                            <div className="col-1"><input type="radio" name="2e"/></div>
                            <div className="col-1"><input type="radio" name="2e"/></div>
                            <div className="col-1"><input type="radio" name="2e"/></div>
                            <div className="col-1"><input type="radio" name="2e"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea placeholder={t('comments')}/>
                        </div>
                    </div>
                    <div className="col-12 text-center border border-ose rounded p-3 my-2">
                        <h3 className="fw-light text-start">3. {t('relationQuality')}</h3>
                        <p className="fw-light text-start fst-italic mb-1">{t('relationQualityDescription')}</p>
                        <div className="row">
                            <div className="col-7 text-start fw-bold my-auto">
                                {t('internWasAbleTo')} :
                            </div>
                            <div className="col-1 my-auto">{t('stronglyAgreeing')}</div>
                            <div className="col-1 my-auto">{t('agreeing')}</div>
                            <div className="col-1 my-auto">{t('disagreeing')}</div>
                            <div className="col-1 my-auto">{t('stronglyDisagreeing')}</div>
                            <div className="col-1 my-auto">N/A</div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">a) {t('3a')}</label>
                            <div className="col-1"><input type="radio" name="3a"/></div>
                            <div className="col-1"><input type="radio" name="3a"/></div>
                            <div className="col-1"><input type="radio" name="3a"/></div>
                            <div className="col-1"><input type="radio" name="3a"/></div>
                            <div className="col-1"><input type="radio" name="3a"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">b) {t('3b')}</label>
                            <div className="col-1"><input type="radio" name="3b"/></div>
                            <div className="col-1"><input type="radio" name="3b"/></div>
                            <div className="col-1"><input type="radio" name="3b"/></div>
                            <div className="col-1"><input type="radio" name="3b"/></div>
                            <div className="col-1"><input type="radio" name="3b"/></div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">c) {t('3c')}</label>
                            <div className="col-1"><input type="radio" name="3c"/></div>
                            <div className="col-1"><input type="radio" name="3c"/></div>
                            <div className="col-1"><input type="radio" name="3c"/></div>
                            <div className="col-1"><input type="radio" name="3c"/></div>
                            <div className="col-1"><input type="radio" name="3c"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">d) {t('3d')}</label>
                            <div className="col-1"><input type="radio" name="3d"/></div>
                            <div className="col-1"><input type="radio" name="3d"/></div>
                            <div className="col-1"><input type="radio" name="3d"/></div>
                            <div className="col-1"><input type="radio" name="3d"/></div>
                            <div className="col-1"><input type="radio" name="3d"/></div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">e) {t('3e')}</label>
                            <div className="col-1"><input type="radio" name="3e"/></div>
                            <div className="col-1"><input type="radio" name="3e"/></div>
                            <div className="col-1"><input type="radio" name="3e"/></div>
                            <div className="col-1"><input type="radio" name="3e"/></div>
                            <div className="col-1"><input type="radio" name="3e"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">e) {t('3f')}</label>
                            <div className="col-1"><input type="radio" name="3f"/></div>
                            <div className="col-1"><input type="radio" name="3f"/></div>
                            <div className="col-1"><input type="radio" name="3f"/></div>
                            <div className="col-1"><input type="radio" name="3f"/></div>
                            <div className="col-1"><input type="radio" name="3f"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea placeholder={t('comments')}/>
                        </div>
                    </div>
                    <div className="col-12 text-center border border-ose rounded p-3 my-2">
                        <h3 className="fw-light text-start">4. {t('personalAbility')}</h3>
                        <p className="fw-light text-start fst-italic mb-1">{t('personalAbilityDescription')}</p>
                        <div className="row">
                            <div className="col-7 text-start fw-bold my-auto">
                                {t('internWasAbleTo')} :
                            </div>
                            <div className="col-1 my-auto">{t('stronglyAgreeing')}</div>
                            <div className="col-1 my-auto">{t('agreeing')}</div>
                            <div className="col-1 my-auto">{t('disagreeing')}</div>
                            <div className="col-1 my-auto">{t('stronglyDisagreeing')}</div>
                            <div className="col-1 my-auto">N/A</div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">a) {t('4a')}</label>
                            <div className="col-1"><input type="radio" name="4a"/></div>
                            <div className="col-1"><input type="radio" name="4a"/></div>
                            <div className="col-1"><input type="radio" name="4a"/></div>
                            <div className="col-1"><input type="radio" name="4a"/></div>
                            <div className="col-1"><input type="radio" name="4a"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">b) {t('4b')}</label>
                            <div className="col-1"><input type="radio" name="4b"/></div>
                            <div className="col-1"><input type="radio" name="4b"/></div>
                            <div className="col-1"><input type="radio" name="4b"/></div>
                            <div className="col-1"><input type="radio" name="4b"/></div>
                            <div className="col-1"><input type="radio" name="4b"/></div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">c) {t('4c')}</label>
                            <div className="col-1"><input type="radio" name="4c"/></div>
                            <div className="col-1"><input type="radio" name="4c"/></div>
                            <div className="col-1"><input type="radio" name="4c"/></div>
                            <div className="col-1"><input type="radio" name="4c"/></div>
                            <div className="col-1"><input type="radio" name="4c"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">d) {t('4d')}</label>
                            <div className="col-1"><input type="radio" name="4d"/></div>
                            <div className="col-1"><input type="radio" name="4d"/></div>
                            <div className="col-1"><input type="radio" name="4d"/></div>
                            <div className="col-1"><input type="radio" name="4d"/></div>
                            <div className="col-1"><input type="radio" name="4d"/></div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-7 text-start">e) {t('4e')}</label>
                            <div className="col-1"><input type="radio" name="4e"/></div>
                            <div className="col-1"><input type="radio" name="4e"/></div>
                            <div className="col-1"><input type="radio" name="4e"/></div>
                            <div className="col-1"><input type="radio" name="4e"/></div>
                            <div className="col-1"><input type="radio" name="4e"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">e) {t('4f')}</label>
                            <div className="col-1"><input type="radio" name="4f"/></div>
                            <div className="col-1"><input type="radio" name="4f"/></div>
                            <div className="col-1"><input type="radio" name="4f"/></div>
                            <div className="col-1"><input type="radio" name="4f"/></div>
                            <div className="col-1"><input type="radio" name="4f"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea placeholder={t('comments')}/>
                        </div>
                    </div>
                    <div className="col-12 border border-ose rounded p-3 my-2">
                        <h3 className="fw-light text-start">{t('globaleAppreciationIntern')}</h3>
                        <div className="row bg-white py-2">
                            <label className="col-6">{t('skillFarExceedExpectations')}</label>
                            <div className="col-6 text-center">
                                <input type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className="row py-2">
                            <label className="col-6">{t('skillExceedExpectations')}</label>
                            <div className="col-6 text-center">
                                <input type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-6">{t('skillMeetsExpectations')}</label>
                            <div className="col-6 text-center">
                                <input type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className="row py-2">
                            <label className="col-6">{t('skillPartiallyMeetsExpectations')}</label>
                            <div className="col-6 text-center">
                                <input type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className="row bg-white py-2">
                            <label className="col-6">{t('skillDoesNotMeetExpectations')}</label>
                            <div className="col-6 text-center">
                                <input type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('preciseYourAppreciation')} :</label>
                            <textarea placeholder={t('preciseYourAppreciation')}/>
                        </div>
                        <div className="row mt-2">
                            <p className="col-6 text-end m-0">{t('discussedWithStudent')}</p>
                            <div className="col-3 text-center">
                                <label htmlFor="yes" className="me-1">{t('yes')}</label>
                                <input type="radio" id='yes' name="discussedWithStudent"/>
                            </div>
                            <div className="col-3 text-start">
                                <label htmlFor="no" className="me-1">{t('no')}</label>
                                <input type="radio" id='no' name="discussedWithStudent"/>
                            </div>
                        </div>
                    </div>
                    <div className="col-12 border border-ose rounded p-3 my-2">
                        <h4 className="fw-light text-start">{t('enterpriseWillTakeTheIntern')}</h4>
                        <div className="row mt-2">
                            <div className="col-4 text-center">
                                <label htmlFor="yesTake" className="me-1">{t('yes')}</label>
                                <input type="radio" id='yesTake' name="takeItAgain"/>
                            </div>
                            <div className="col-4 text-center">
                                <label htmlFor="noTake" className="me-1">{t('no')}</label>
                                <input type="radio" id='noTake' name="takeItAgain"/>
                            </div>
                            <div className="col-4 text-center">
                                <label htmlFor="maybe" className="me-1">{t('maybe')}</label>
                                <input type="radio" id='maybe' name="takeItAgain"/>
                            </div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('sufficientToCarryOut')} :</label>
                            <textarea placeholder={t('sufficientToCarryOut')}/>
                        </div>
                    </div>
                </div>
                <button className="btn btn-outline-ose my-auto" >{t('save')}</button>
            </form>
        </div>
    );
}
export default StudentEval;