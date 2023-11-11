import React, {forwardRef, useEffect, useRef, useState} from 'react'
import {useTranslation} from "react-i18next"
import './FAQModal.css'
import {t} from "i18next"
import FAQStudent from "../student/FAQStudent"
import FAQManager from "../manager/FAQManager"
import FAQEmployer from "../employer/FAQEmployer"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faX} from "@fortawesome/free-solid-svg-icons";

const FAQModal = forwardRef(({role}, ref) => {
	const {i18n} = useTranslation()
	const [faqs, setFaqs] = useState([])
	const faqRef = useRef(null)
	const [isModalOpen, setIsModalOpen] = useState(false)

	useEffect(() => {
		if(isModalOpen){
			faqRef.current?.scrollIntoView({behavior: 'smooth'})
		}
		setFaqs(selectFaqData())
	}, [isModalOpen])

	const toggleFAQ = index => {
		setFaqs(faqs.map((faq, i) => ({...faq, open: i === index ? !faq.open : false})))
	}

	const closeModal = () => setIsModalOpen(false)

	const openModal = () => setIsModalOpen(true)

	const currentLang = i18n.language

	function selectFaqData(){
		switch(role){
			case 'ROLE_STUDENT':
				return FAQStudent
			case 'ROLE_MANAGER':
				return FAQManager
			case 'ROLE_EMPLOYER':
				return FAQEmployer
			default:
				return []
		}
	}

	return (
		<>
			<button className="btn btn-ose text-white faq-button m-3" onClick={openModal}>{i18n.t("FAQ_TITLE")}</button>
			<div className={`modal`} ref={faqRef}>
				<div className="modal-dialog">
					<div className="modal-content">
						<div className="modal-header">
							<FontAwesomeIcon icon={faX} data-dismiss="modal" className="btn-outline-danger"/>
						</div>
						<div className="modal-body">
							<div className="faq-section">
								<h2>{i18n.t("FAQ_TITLE")}</h2>
								{
									faqs.length > 0 ?
										faqs.map((faq, index) => (
											<div className={`faq ${faq.open ? 'open' : ''}`} key={index}>
												<div className="faq-question" onClick={() => toggleFAQ(index)}>
													{faq.question[currentLang] ?? t('QUESTION_UNAVAILABLE')}
												</div>
												<div className="faq-answer">
													{faq.open && <p>{faq.answer[currentLang] ?? t('ANSWER_UNAVAILABLE')}</p>}
												</div>
											</div>
										))
										:
										<div className="faq">
											<div className="faq-question">{i18n.t("NO_FAQ")}</div>
										</div>
								}
							</div>
						</div>
					</div>

				</div>
			</div>
		</>
	)

})

export default FAQModal
