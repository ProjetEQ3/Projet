import React, { forwardRef, useEffect, useRef, useState } from 'react'
import { useTranslation } from "react-i18next"
import FAQStudent from "../student/FAQStudent"
import FAQManager from "../manager/FAQManager"
import FAQEmployer from "../employer/FAQEmployer"
import 'bootstrap/dist/css/bootstrap.min.css';

const FAQModal = forwardRef(({ role }, ref) => {
	const { i18n } = useTranslation()
	const [faqs, setFaqs] = useState([])
	const faqRef = useRef(null)
	const [isModalOpen, setIsModalOpen] = useState(false)

	useEffect(() => {
		if (isModalOpen) {
			faqRef.current?.scrollIntoView({ behavior: 'smooth' })
		}
		setFaqs(selectFaqData())
	}, [isModalOpen])

	const handleCloseClick = (e) => {
		e.stopPropagation()
		closeModal()
	}

	const toggleFAQ = index => {
		setFaqs(faqs.map((faq, i) => ({ ...faq, open: i === index ? !faq.open : false })))
	}

	const closeModal = () => setIsModalOpen(false)

	const openModal = () => setIsModalOpen(true)

	const currentLang = i18n.language

	function selectFaqData() {
		switch (role) {
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
			<button className="btn outline btn-primary my-3 p-1 faq-button" onClick={openModal}>{i18n.t("CONSULT_FAQ")}</button>
			<div className={`modal fade ${isModalOpen ? 'show' : ''}`} style={{ display: isModalOpen ? 'block' : 'none' }} ref={faqRef}>
				<div className="modal-dialog modal-lg modal-dialog-centered">
					<div className="modal-content">
						<div className="modal-header">
							<h5 className="modal-title">{i18n.t("FAQ_TITLE")}</h5>
							<button type="button" className="btn-close" onClick={handleCloseClick}></button>
						</div>
						<div className="modal-body">
							{
								faqs.length > 0 ?
									faqs.map((faq, index) => (
										<div className={`faq mb-3 ${faq.open ? 'show' : ''}`} key={index}>
											<div className="faq-question clickable my-3 p-3 bg-ose text-white" onClick={() => toggleFAQ(index)}>
												{faq.question[currentLang] ?? i18n.t('QUESTION_UNAVAILABLE')}
											</div>
											<div className={`faq-answer collapse mb-3 px-3 ${faq.open ? 'show' : ''}`}>
												<p>{faq.answer[currentLang] ?? i18n.t('ANSWER_UNAVAILABLE')}</p>
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
		</>
	)
})

export default FAQModal
