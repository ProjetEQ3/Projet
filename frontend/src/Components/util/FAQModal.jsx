import React, {forwardRef, useEffect, useRef, useState} from 'react'
import {faCircleQuestion, faX} from "@fortawesome/free-solid-svg-icons";
import {useTranslation} from "react-i18next"
import 'bootstrap/dist/css/bootstrap.min.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const FAQModal = forwardRef(({role}, ref) => {
	const {t, i18n} = useTranslation()
	const [faqs, setFaqs] = useState([])
	const faqRef = useRef(null)

	useEffect(() => {
		selectFaqData()
	}, [role, i18n.language])

	const handleCloseClick = (e) => {
		e.stopPropagation()
	}

	const toggleFAQ = index => {
		setFaqs(faqs.map((faq, i) => ({...faq, open: i === index ? !faq.open : false})))
	}

	function selectFaqData(){
		const faqKey = role ? `FAQ_LIST.${role.replace('ROLE_', 'FAQ_')}` : ''
		const faqDataObject = faqKey ? t(faqKey, {returnObjects: true}) : {}

		// Convert object to array
		const faqDataArray = Object.keys(faqDataObject).map(
			key => ({...faqDataObject[key], id: key, open: false})
		)
		setFaqs(faqDataArray)
	}


	return (
		<>
			<button className="btn btn-outline-ose FAQ-button" data-bs-toggle="modal" data-bs-target="#faqModal">
				{t("CONSULT_FAQ")}
				<FontAwesomeIcon icon={faCircleQuestion} className="ms-2"/>
			</button>
			<div id="faqModal" className="modal fade">
				<div className="modal-dialog modal-lg modal-dialog-centered">
					<div className="modal-content">
						<div className="modal-header">
								<h3 className="modal-title">{t('FAQ_TITLE')}</h3>
								<FontAwesomeIcon data-testid="faX" icon={faX} data-bs-dismiss="modal" className="danger-hover fa-lg pe-2"/>
						</div>
						<div className="modal-body">
							{
								faqs && faqs.length > 0 ?
									faqs.map((faq, index) => (
										<div className={`faq mb-3 bg-light p-0 rounded ${faq.open ? 'show' : ''}`} key={index}>
											<div className="btn btn-outline-ose fw-light col-12 text-start clickable p-3 " onClick={() => toggleFAQ(index)}>
												{faq.QUESTION}
											</div>
											<div className={`collapse mb-3 px-3 ${faq.open ? 'show' : ''}`}>
												<p className="pt-2 pb-1 ">{faq.ANSWER}</p>
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
		</>
	)
})

export default FAQModal