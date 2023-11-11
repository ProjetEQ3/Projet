import React, {useRef, useState} from 'react'
import {useTranslation} from "react-i18next"
import FAQModal from '../util/FAQModal'
import FAQStudent from "../student/FAQStudent"
import './Footer.css'
import FAQManager from "../manager/FAQManager";
import FAQEmployer from "../employer/FAQEmployer";

function Footer({role}){
	const {t} = useTranslation()
	const faqRef = useRef(null)
	const [isModalOpen, setIsModalOpen] = useState(false)
	const closeModal = () => setIsModalOpen(false)
	const footerStyle = {
		position: 'absolute',
		bottom: 0,
	}
	const openModal = () => {
		setIsModalOpen(true)
		setTimeout(() => {
			faqRef.current?.scrollIntoView({ behavior: 'smooth' })
		}, 0)
	}

	console.log("role", role)

	function choseFaq(){
		switch(role){
			case 'ROLE_STUDENT':
				return <FAQStudent />
			case 'ROLE_MANAGER':
				return <FAQManager />
			case 'ROLE_EMPLOYER':
				return <FAQEmployer />
			default:
				return <>Aucun FAQ es disponible pour le moment</>
		}
	}

	return (
		<>
			<footer style={footerStyle} className="App-footer col-12">
				<div className="container-fluid">
					<div className="row">
						<div className="col-12 d-none d-md-block text-center">
							<p className="lead">{t('glucoseAcronym')} - {t('glucoseFullAcronym')}</p>
							<p className="lead">{t('copyRights')}</p>
						</div>
						<div className="col-12 d-block d-md-none text-center">
							<p className="lead"><small>{t('glucoseAcronym')} - {t('glucoseFullAcronym')}</small></p>
							<p className="lead"><small>{t('copyRights')}</small></p>
						</div>
						<button className={"faq-button"} onClick={openModal}>Ouvrir FAQ</button>
						<FAQModal ref={faqRef} isOpen={isModalOpen} onClose={closeModal}>
							{choseFaq()}
						</FAQModal>

					</div>
				</div>
			</footer>
		</>
	)
}

export default Footer
