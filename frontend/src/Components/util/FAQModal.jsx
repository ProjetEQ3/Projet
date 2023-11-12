import React, {forwardRef, useEffect, useRef, useState} from 'react'
import {useTranslation} from "react-i18next"
import 'bootstrap/dist/css/bootstrap.min.css'

const FAQModal = forwardRef(({role}, ref) => {
	const {t, i18n} = useTranslation()
	const [faqs, setFaqs] = useState([])
	const faqRef = useRef(null)
	const [isModalOpen, setIsModalOpen] = useState(false)

	useEffect(() => {
		if(isModalOpen){
			faqRef.current?.scrollIntoView({behavior: 'smooth'})
		}
		selectFaqData()
	}, [isModalOpen, role, i18n.language])

	const handleCloseClick = (e) => {
		e.stopPropagation()
		closeModal()
	}

	const toggleFAQ = index => {
		setFaqs(faqs.map((faq, i) => ({...faq, open: i === index ? !faq.open : false})))
	}

	const closeModal = () => setIsModalOpen(false)

	const openModal = () => setIsModalOpen(true)

	function selectFaqData(){
		const faqKey = role ? `FAQ_LIST.${role.replace('ROLE_', 'FAQ_')}` : ''
		const faqDataObject = faqKey ? t(faqKey, {returnObjects: true}) : {}

		// Convert object to array
		const faqDataArray = Object.keys(faqDataObject).map(
			key => ({...faqDataObject[key], id: key, open: false})
		)
		setFaqs(faqDataArray)
	}


	return (<>
		<button className="btn btn-outline-ose my-3 p-1 faq-button" onClick={openModal}>{t("CONSULT_FAQ")}</button>
		<div className={`modal fade ${isModalOpen ? 'show' : ''}`} style={{display: isModalOpen ? 'block' : 'none'}}
		     ref={faqRef}>
			<div className="modal-dialog modal-lg modal-dialog-centered">
				<div className="modal-content">
					<div className="modal-header">
						<h5 className="modal-title">{t("FAQ_TITLE")}</h5>
						<button type="button" className="btn-close faq-close-btn" onClick={handleCloseClick}></button>
					</div>
					<div className="modal-body">
						{
							faqs && faqs.length > 0 ?
								faqs.map((faq, index) => (
									<div className={`faq mb-3 ${faq.open ? 'show' : ''}`} key={index}>
										<div className="faq-question clickable my-3 p-3 bg-ose btn-outline-ose" onClick={() => toggleFAQ(index)}>
											{faq.QUESTION}
										</div>
										<div className={`faq-answer collapse mb-3 px-3 ${faq.open ? 'show' : ''}`}>
											<p>{faq.ANSWER}</p>
										</div>
									</div>
								))
								:
								<div className="faq">
									<div className="faq-question">{t("NO_FAQ")}</div>
								</div>
						}
					</div>
				</div>
			</div>
		</div>
	</>)
})

export default FAQModal
