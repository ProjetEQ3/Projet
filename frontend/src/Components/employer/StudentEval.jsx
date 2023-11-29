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
    const [formData, setFormData] = useState({
        a1: "",
        b1: "",
        c1: "",
        d1: "",
        e1: "",
        comment1: "",
        a2: "",
        b2: "",
        c2: "",
        d2: "",
        e2: "",
        comment2: "",
        a3: "",
        b3: "",
        c3: "",
        d3: "",
        e3: "",
        f3: "",
        comment3: "",
        a4: "",
        b4: "",
        c4: "",
        d4: "",
        e4: "",
        f4: "",
        comment4: "",
        globalAppreciationIntern: "",
        discussedWithStudent: "",
        takeItAgain: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((fData) => ({
            ...fData,
            [name]: value,
        }));
    }

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

    console.log(jobApplication)
    console.log(student)
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
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">a) {t('a1')}</label>
                            <div className="col-1"><input type="radio" name="a1"/></div>
                            <div className="col-1"><input type="radio" name="a1"/></div>
                            <div className="col-1"><input type="radio" name="a1"/></div>
                            <div className="col-1"><input type="radio" name="a1"/></div>
                            <div className="col-1"><input type="radio" name="a1"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">b) {t('b1')}</label>
                            <div className="col-1"><input type="radio" name="b1"/></div>
                            <div className="col-1"><input type="radio" name="b1"/></div>
                            <div className="col-1"><input type="radio" name="b1"/></div>
                            <div className="col-1"><input type="radio" name="b1"/></div>
                            <div className="col-1"><input type="radio" name="b1"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">c) {t('c1')}</label>
                            <div className="col-1"><input type="radio" name="c1"/></div>
                            <div className="col-1"><input type="radio" name="c1"/></div>
                            <div className="col-1"><input type="radio" name="c1"/></div>
                            <div className="col-1"><input type="radio" name="c1"/></div>
                            <div className="col-1"><input type="radio" name="c1"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">d) {t('d1')}</label>
                            <div className="col-1"><input type="radio" name="d1"/></div>
                            <div className="col-1"><input type="radio" name="d1"/></div>
                            <div className="col-1"><input type="radio" name="d1"/></div>
                            <div className="col-1"><input type="radio" name="d1"/></div>
                            <div className="col-1"><input type="radio" name="d1"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">e) {t('e1')}</label>
                            <div className="col-1"><input type="radio" name="e1"/></div>
                            <div className="col-1"><input type="radio" name="e1"/></div>
                            <div className="col-1"><input type="radio" name="e1"/></div>
                            <div className="col-1"><input type="radio" name="e1"/></div>
                            <div className="col-1"><input type="radio" name="e1"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea name="comment1" placeholder={t('comments')}/>
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
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">a) {t('a2')}</label>
                            <div className="col-1"><input type="radio" name="a2"/></div>
                            <div className="col-1"><input type="radio" name="a2"/></div>
                            <div className="col-1"><input type="radio" name="a2"/></div>
                            <div className="col-1"><input type="radio" name="a2"/></div>
                            <div className="col-1"><input type="radio" name="a2"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">b) {t('b2')}</label>
                            <div className="col-1"><input type="radio" name="b2"/></div>
                            <div className="col-1"><input type="radio" name="b2"/></div>
                            <div className="col-1"><input type="radio" name="b2"/></div>
                            <div className="col-1"><input type="radio" name="b2"/></div>
                            <div className="col-1"><input type="radio" name="b2"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">c) {t('c2')}</label>
                            <div className="col-1"><input type="radio" name="c2"/></div>
                            <div className="col-1"><input type="radio" name="c2"/></div>
                            <div className="col-1"><input type="radio" name="c2"/></div>
                            <div className="col-1"><input type="radio" name="c2"/></div>
                            <div className="col-1"><input type="radio" name="c2"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">d) {t('d2')}</label>
                            <div className="col-1"><input type="radio" name="d2"/></div>
                            <div className="col-1"><input type="radio" name="d2"/></div>
                            <div className="col-1"><input type="radio" name="d2"/></div>
                            <div className="col-1"><input type="radio" name="d2"/></div>
                            <div className="col-1"><input type="radio" name="d2"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">e) {t('e2')}</label>
                            <div className="col-1"><input type="radio" name="e2"/></div>
                            <div className="col-1"><input type="radio" name="e2"/></div>
                            <div className="col-1"><input type="radio" name="e2"/></div>
                            <div className="col-1"><input type="radio" name="e2"/></div>
                            <div className="col-1"><input type="radio" name="e2"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea name="comment2" placeholder={t('comments')}/>
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
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">a) {t('a3')}</label>
                            <div className="col-1"><input type="radio" name="a3"/></div>
                            <div className="col-1"><input type="radio" name="a3"/></div>
                            <div className="col-1"><input type="radio" name="a3"/></div>
                            <div className="col-1"><input type="radio" name="a3"/></div>
                            <div className="col-1"><input type="radio" name="a3"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">b) {t('b3')}</label>
                            <div className="col-1"><input type="radio" name="b3"/></div>
                            <div className="col-1"><input type="radio" name="b3"/></div>
                            <div className="col-1"><input type="radio" name="b3"/></div>
                            <div className="col-1"><input type="radio" name="b3"/></div>
                            <div className="col-1"><input type="radio" name="b3"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">c) {t('c3')}</label>
                            <div className="col-1"><input type="radio" name="c3"/></div>
                            <div className="col-1"><input type="radio" name="c3"/></div>
                            <div className="col-1"><input type="radio" name="c3"/></div>
                            <div className="col-1"><input type="radio" name="c3"/></div>
                            <div className="col-1"><input type="radio" name="c3"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">d) {t('d3')}</label>
                            <div className="col-1"><input type="radio" name="d3"/></div>
                            <div className="col-1"><input type="radio" name="d3"/></div>
                            <div className="col-1"><input type="radio" name="d3"/></div>
                            <div className="col-1"><input type="radio" name="d3"/></div>
                            <div className="col-1"><input type="radio" name="d3"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">e) {t('e3')}</label>
                            <div className="col-1"><input type="radio" name="e3"/></div>
                            <div className="col-1"><input type="radio" name="e3"/></div>
                            <div className="col-1"><input type="radio" name="e3"/></div>
                            <div className="col-1"><input type="radio" name="e3"/></div>
                            <div className="col-1"><input type="radio" name="e3"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">e) {t('f3')}</label>
                            <div className="col-1"><input type="radio" name="f3"/></div>
                            <div className="col-1"><input type="radio" name="f3"/></div>
                            <div className="col-1"><input type="radio" name="f3"/></div>
                            <div className="col-1"><input type="radio" name="f3"/></div>
                            <div className="col-1"><input type="radio" name="f3"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea name="comment3" placeholder={t('comments')}/>
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
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">a) {t('a4')}</label>
                            <div className="col-1"><input type="radio" name="a4"/></div>
                            <div className="col-1"><input type="radio" name="a4"/></div>
                            <div className="col-1"><input type="radio" name="a4"/></div>
                            <div className="col-1"><input type="radio" name="a4"/></div>
                            <div className="col-1"><input type="radio" name="a4"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">b) {t('b4')}</label>
                            <div className="col-1"><input type="radio" name="b4"/></div>
                            <div className="col-1"><input type="radio" name="b4"/></div>
                            <div className="col-1"><input type="radio" name="b4"/></div>
                            <div className="col-1"><input type="radio" name="b4"/></div>
                            <div className="col-1"><input type="radio" name="b4"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">c) {t('c4')}</label>
                            <div className="col-1"><input type="radio" name="c4"/></div>
                            <div className="col-1"><input type="radio" name="c4"/></div>
                            <div className="col-1"><input type="radio" name="c4"/></div>
                            <div className="col-1"><input type="radio" name="c4"/></div>
                            <div className="col-1"><input type="radio" name="c4"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">d) {t('d4')}</label>
                            <div className="col-1"><input type="radio" name="d4"/></div>
                            <div className="col-1"><input type="radio" name="d4"/></div>
                            <div className="col-1"><input type="radio" name="d4"/></div>
                            <div className="col-1"><input type="radio" name="d4"/></div>
                            <div className="col-1"><input type="radio" name="d4"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-7 text-start">e) {t('e4')}</label>
                            <div className="col-1"><input type="radio" name="e4"/></div>
                            <div className="col-1"><input type="radio" name="e4"/></div>
                            <div className="col-1"><input type="radio" name="e4"/></div>
                            <div className="col-1"><input type="radio" name="e4"/></div>
                            <div className="col-1"><input type="radio" name="e4"/></div>
                        </div>
                        <div className="row py-2">
                            <label className="col-7 text-start">e) {t('f4')}</label>
                            <div className="col-1"><input type="radio" name="f4"/></div>
                            <div className="col-1"><input type="radio" name="f4"/></div>
                            <div className="col-1"><input type="radio" name="f4"/></div>
                            <div className="col-1"><input type="radio" name="f4"/></div>
                            <div className="col-1"><input type="radio" name="f4"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea name="comment4" placeholder={t('comments')}/>
                        </div>
                    </div>
                    <div className="col-12 border border-ose rounded p-3 my-2">
                        <h3 className="fw-light text-start">{t('globaleAppreciationIntern')}</h3>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
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
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
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
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
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