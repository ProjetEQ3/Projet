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
    const navigate = useNavigate();
    const { darkMode } = useDarkMode();

    const handleBack = () => {
        navigate(-1);
    };

    const notFilled = (id) => {
        toast.error(t('pleaseAnswerAllQuestions'));
        console.log(id)
        document.querySelector(`#${id}`).scrollIntoView({behavior: 'smooth'})
        //Quand on change la couleur avec ca fuck le theme
        //document.querySelector(`#${id}`).style.color = 'red';
        document.querySelector(`#${id}`).style.fontWeight = 'bold';
        document.querySelector(`#${id}`).style.textDecoration = 'underline';
    }

    const resetLabels = () => {
        const labels = document.querySelectorAll('label');
        labels.forEach(label => {
            label.style.fontWeight = 'normal';
            label.style.textDecoration = 'none';

        })
    }



    const sendEvaluation = (e) => {
        e.preventDefault();

        resetLabels();
        if (!document.querySelector('input[name="a1"]:checked')) {
            notFilled('a1')
            return;
        }
        if (!document.querySelector('input[name="b1"]:checked')) {
            notFilled('b1')
            return;
        }
        if (!document.querySelector('input[name="c1"]:checked')) {
            notFilled('c1')
            return;
        }
        if (!document.querySelector('input[name="d1"]:checked')) {
            notFilled('d1')
            return;
        }
        if (!document.querySelector('input[name="e1"]:checked')) {
            notFilled('e1')
            return;
        }

        if (!document.querySelector('input[name="a2"]:checked')) {
            notFilled('a2')
            return;
        }
        if (!document.querySelector('input[name="b2"]:checked')) {
            notFilled('b2')
            return;
        }
        if (!document.querySelector('input[name="c2"]:checked')) {
            notFilled('c2')
            return;
        }
        if (!document.querySelector('input[name="d2"]:checked')) {
            notFilled('d2')
            return;
        }
        if (!document.querySelector('input[name="e2"]:checked')) {
            notFilled('e2')
            return;
        }

        if (!document.querySelector('input[name="a3"]:checked')) {
            notFilled('a3')
            return;
        }
        if (!document.querySelector('input[name="b3"]:checked')) {
            notFilled('b3')
            return;
        }
        if (!document.querySelector('input[name="c3"]:checked')) {
            notFilled('c3')
            return;
        }
        if (!document.querySelector('input[name="d3"]:checked')) {
            notFilled('d3')
            return;
        }
        if (!document.querySelector('input[name="e3"]:checked')) {
            notFilled('e3')
            return;
        }
        if (!document.querySelector('input[name="f3"]:checked')) {
            notFilled('f3')
            return;
        }

        if (!document.querySelector('input[name="a4"]:checked')) {
            notFilled('a4')
            return;
        }
        if (!document.querySelector('input[name="b4"]:checked')) {
            notFilled('b4')
            return;
        }
        if (!document.querySelector('input[name="c4"]:checked')) {
            notFilled('c4')
            return;
        }
        if (!document.querySelector('input[name="d4"]:checked')) {
            notFilled('d4')
            return;
        }
        if (!document.querySelector('input[name="e4"]:checked')) {
            notFilled('e4')
            return;
        }
        if (!document.querySelector('input[name="f4"]:checked')) {
            notFilled('f4')
            return;
        }

        if (!document.querySelector('input[name="globalAppreciationIntern"]:checked')) {
            notFilled('globalAppreciationIntern')
            return;
        }
        const preciseYourAppreciation = document.querySelector('textarea[name="preciseYourAppreciation"]');
        if (preciseYourAppreciation.value.trim() === '') {
            notFilled('preciseYourAppreciation')
            return;
        }
        if (!document.querySelector('input[name="discussedWithStudent"]:checked')) {
            notFilled('discussedWithStudent')
            return;
        }

        if (!document.querySelector('input[name="takeItAgain"]:checked')) {
            notFilled('takeItAgain')
            return;
        }
        const sufficientToCarryOut = document.querySelector('textarea[name="sufficientToCarryOut"]');
        if (sufficientToCarryOut.value.trim() === '') {
            notFilled('sufficientToCarryOut')
            return;
        }

        let sections = [];
        let section1 = [];
        let section2 = [];
        let section3 = [];
        let section4 = [];
        let section5 = [];
        let section6 = [];
        section1.push(document.querySelector('input[name="a1"]:checked').value);
        section1.push(document.querySelector('input[name="b1"]:checked').value);
        section1.push(document.querySelector('input[name="c1"]:checked').value);
        section1.push(document.querySelector('input[name="d1"]:checked').value);
        section1.push(document.querySelector('input[name="e1"]:checked').value);
        section1.push(document.querySelector('textarea[name="comment1"]').value.trim());
        section2.push(document.querySelector('input[name="a2"]:checked').value);
        section2.push(document.querySelector('input[name="b2"]:checked').value);
        section2.push(document.querySelector('input[name="c2"]:checked').value);
        section2.push(document.querySelector('input[name="d2"]:checked').value);
        section2.push(document.querySelector('input[name="e2"]:checked').value);
        section2.push(document.querySelector('textarea[name="comment2"]').value.trim());
        section3.push(document.querySelector('input[name="a3"]:checked').value);
        section3.push(document.querySelector('input[name="b3"]:checked').value);
        section3.push(document.querySelector('input[name="c3"]:checked').value);
        section3.push(document.querySelector('input[name="d3"]:checked').value);
        section3.push(document.querySelector('input[name="e3"]:checked').value);
        section3.push(document.querySelector('input[name="f3"]:checked').value);
        section2.push(document.querySelector('textarea[name="comment3"]').value.trim());
        section4.push(document.querySelector('input[name="a4"]:checked').value);
        section4.push(document.querySelector('input[name="b4"]:checked').value);
        section4.push(document.querySelector('input[name="c4"]:checked').value);
        section4.push(document.querySelector('input[name="d4"]:checked').value);
        section4.push(document.querySelector('input[name="e4"]:checked').value);
        section4.push(document.querySelector('input[name="f4"]:checked').value);
        section2.push(document.querySelector('textarea[name="comment4"]').value.trim());
        section5.push(document.querySelector('input[name="globalAppreciationIntern"]:checked').value);
        section5.push(preciseYourAppreciation.value.trim());
        section5.push(document.querySelector('input[name="discussedWithStudent"]:checked').value);
        section6.push(document.querySelector('input[name="takeItAgain"]:checked').value);
        section6.push(sufficientToCarryOut.value.trim());

        sections.push(section1);
        sections.push(section2);
        sections.push(section3);
        sections.push(section4);
        sections.push(section5);
        sections.push(section6);

        axiosInstance.post(`/employer/internEvaluation/${jobApplication.id}`, sections)
            .then(() => {
                toast.success(t('evaluationSent'));
                navigate(-1);
            })
            .catch(() => {
                toast.error(t('evaluationNotSent'));
            })

    }

    return (
        <div className="container">
            <button className="mt-3 btn btn-outline-ose" onClick={handleBack}>
                <FontAwesomeIcon icon={faArrowLeft} className="fa-2 me-2"/>
                {t('back')}
            </button>
            <h1 className="text-center fw-light my-2">{t('evaluationOf')} {jobApplication.student.firstName} {jobApplication.student.lastName}</h1>
            <form className="col-10 mx-auto text-center" onSubmit={sendEvaluation}>
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
                            <label id="a1" className="col-7 text-start">a) {t('a1')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="a1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="a1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="a1"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="a1"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="a1"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="b1" className="col-7 text-start">b) {t('b1')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="b1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="b1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="b1"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="b1"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="b1"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label id="c1" className="col-7 text-start">c) {t('c1')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="c1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="c1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="c1"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="c1"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="c1"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="d1" className="col-7 text-start">d) {t('d1')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="d1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="d1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="d1"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="d1"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="d1"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label id="e1" className="col-7 text-start">e) {t('e1')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="e1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="e1"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="e1"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="e1"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="e1"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea className={`${darkMode ? "dark-input" : ""}`} name="comment1" placeholder={t('comments')}/>
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
                            <label id="a2" className="col-7 text-start">a) {t('a2')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="a2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="a2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="a2"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="a2"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="a2"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="b2" className="col-7 text-start">b) {t('b2')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="b2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="b2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="b2"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="b2"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="b2"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label id="c2" className="col-7 text-start">c) {t('c2')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="c2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="c2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="c2"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="c2"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="c2"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="d2" className="col-7 text-start">d) {t('d2')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="d2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="d2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="d2"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="d2"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="d2"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label id="e2" className="col-7 text-start">e) {t('e2')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="e2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="e2"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="e2"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="e2"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="e2"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea className={`${darkMode ? "dark-input" : ""}`} name="comment2" placeholder={t('comments')}/>
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
                            <label id="a3" className="col-7 text-start">a) {t('a3')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="a3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="a3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="a3"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="a3"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="a3"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="b3" className="col-7 text-start">b) {t('b3')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="b3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="b3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="b3"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="b3"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="b3"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label id="c3" className="col-7 text-start">c) {t('c3')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="c3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="c3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="c3"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="c3"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="c3"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="d3" className="col-7 text-start">d) {t('d3')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="d3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="d3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="d3"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="d3"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="d3"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label id="e3" className="col-7 text-start">e) {t('e3')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="e3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="e3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="e3"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="e3"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="e3"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="f3" className="col-7 text-start">e) {t('f3')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="f3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="f3"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="f3"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="f3"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="f3"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea className={`${darkMode ? "dark-input" : ""}`} name="comment3" placeholder={t('comments')}/>
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
                            <label id="a4" className="col-7 text-start">a) {t('a4')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="a4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="a4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="a4"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="a4"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="a4"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="b4" className="col-7 text-start">b) {t('b4')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="b4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="b4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="b4"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="b4"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="b4"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label id="c4" className="col-7 text-start">c) {t('c4')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="c4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="c4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="c4"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="c4"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="c4"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="d4" className="col-7 text-start">d) {t('d4')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="d4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="d4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="d4"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="d4"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="d4"/></div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label id="e4" className="col-7 text-start">e) {t('e4')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="e4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="e4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="e4"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="e4"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="e4"/></div>
                        </div>
                        <div className="row py-2">
                            <label id="f4" className="col-7 text-start">e) {t('f4')}</label>
                            <div className="col-1"><input value='TOTALLY_AGREE' type="radio" name="f4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_AGREE' type="radio" name="f4"/></div>
                            <div className="col-1"><input value='SOMEWHAT_DISAGREE' type="radio" name="f4"/></div>
                            <div className="col-1"><input value='TOTALLY_DISAGREE' type="radio" name="f4"/></div>
                            <div className="col-1"><input value='NOT_APPLICABLE' type="radio" name="f4"/></div>
                        </div>
                        <div className="row mt-2">
                            <label className="text-start">{t('comments')} :</label>
                            <textarea className={`${darkMode ? "dark-input" : ""}`} name="comment4" placeholder={t('comments')}/>
                        </div>
                    </div>
                    <div className="col-12 border border-ose rounded p-3 my-2">
                        <h3 className="fw-light text-start">{t('globaleAppreciationIntern')}</h3>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-6">{t('skillFarExceedExpectations')}</label>
                            <div className="col-6 text-center">
                                <input value='SKILL_FAR_EXCEED' type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className="row py-2">
                            <label className="col-6">{t('skillExceedExpectations')}</label>
                            <div className="col-6 text-center">
                                <input value='SKILL_EXCEED' type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-6">{t('skillMeetsExpectations')}</label>
                            <div className="col-6 text-center">
                                <input value='SKILL_MEET' type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className="row py-2">
                            <label className="col-6">{t('skillPartiallyMeetsExpectations')}</label>
                            <div className="col-6 text-center">
                                <input value='SKILL_PARTIALLY_MEET' type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className={`row ${darkMode ? 'bg-dark-dark' : 'bg-white'} py-2`}>
                            <label className="col-6">{t('skillDoesNotMeetExpectations')}</label>
                            <div className="col-6 text-center">
                                <input value='SKILL_NOT_MEET' type="radio" name="globalAppreciationIntern"/>
                            </div>
                        </div>
                        <div className="row mt-2">
                            <label id='preciseYourAppreciation' className="text-start">{t('preciseYourAppreciation')} :</label>
                            <textarea className={`${darkMode ? "dark-input" : ""}`} name="preciseYourAppreciation" placeholder={t('preciseYourAppreciation')}/>
                        </div>
                        <div className="row mt-2">
                            <p className="col-6 text-end m-0">{t('discussedWithStudent')}</p>
                            <div className="col-3 text-center">
                                <label id='discussedWithStudent' htmlFor="yes" className="me-1">{t('yes')}</label>
                                <input value="YES" type="radio" id='yes' name="discussedWithStudent"/>
                            </div>
                            <div className="col-3 text-start">
                                <label id='discussedWithStudent' htmlFor="no" className="me-1">{t('no')}</label>
                                <input value="NO" type="radio" id='no' name="discussedWithStudent"/>
                            </div>
                        </div>
                    </div>
                    <div className="col-12 border border-ose rounded p-3 my-2">
                        <h4 className="fw-light text-start">{t('enterpriseWillTakeTheIntern')}</h4>
                        <div className="row mt-2">
                            <div className="col-4 text-center">
                                <label id='takeItAgain' htmlFor="yesTake" className="me-1">{t('yes')}</label>
                                <input value="YES" type="radio" id='yesTake' name="takeItAgain"/>
                            </div>
                            <div className="col-4 text-center">
                                <label id='takeItAgain' htmlFor="noTake" className="me-1">{t('no')}</label>
                                <input value="NO" type="radio" id='noTake' name="takeItAgain"/>
                            </div>
                            <div className="col-4 text-center">
                                <label id='takeItAgain' htmlFor="maybe" className="me-1">{t('maybe')}</label>
                                <input value="MAYBE" type="radio" id='maybe' name="takeItAgain"/>
                            </div>
                        </div>
                        <div className="row mt-2">
                            <label id='sufficientToCarryOut' className="text-start">{t('sufficientToCarryOut')} :</label>
                            <textarea className={`${darkMode ? "dark-input" : ""}`} name="sufficientToCarryOut" placeholder={t('sufficientToCarryOut')}/>
                        </div>
                    </div>
                </div>
                <button type="submit" className="btn btn-outline-ose my-auto" >{t('save')}</button>
            </form>
        </div>
    );
}
export default StudentEval;