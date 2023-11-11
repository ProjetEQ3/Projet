import React, { useState } from 'react'
import './FAQEmployer.css'

const FAQEmployer = () => {
	const [faqs, setFaqs] = useState([
		{
			question: 'Comment puis-je offrir des stages dans mon entreprise?',
			answer: 'Pour offrir des stages, créez un compte employeur sur notre site et suivez les étapes pour publier une offre de stage. Vous devrez fournir des détails sur le stage, les exigences et les compétences recherchées.',
			open: false
		},
		{
			question: 'Quels sont les avantages d’accueillir des stagiaires?',
			answer: 'Les stagiaires peuvent apporter de nouvelles perspectives, soutenir des projets clés et vous aider à identifier des talents pour de futurs recrutements. C\'est également une manière de contribuer à la formation des jeunes professionnels.',
			open: false
		},
		{
			question: 'Quelles sont les obligations légales liées à l’accueil de stagiaires?',
			answer: 'Les obligations varient selon le pays et la région. Généralement, cela inclut la mise en place d\'une convention de stage, le respect des durées de travail et, souvent, le paiement d\'une gratification.',
			open: false
		},
		{
			question: 'Dois-je payer les stagiaires?',
			answer: 'La rémunération des stagiaires dépend de la législation locale et de la durée du stage. Dans certains pays, une gratification est obligatoire si le stage dépasse une certaine durée.',
			open: false
		},
		{
			question: 'Comment sélectionner le meilleur candidat pour un stage?',
			answer: 'Évaluez les CV et lettres de motivation, organisez des entretiens pour juger des compétences et de la motivation, et considérez comment le candidat s\'intégrera à votre équipe et culture d\'entreprise.',
			open: false
		},
		{
			question: 'Puis-je proposer un stage à l’étranger?',
			answer: 'Oui, si votre entreprise opère aussi à l\'international, vous pouvez proposer des stages à l\'étranger. Assurez-vous de respecter la législation du pays en matière de stages et d\'immigration.',
			open: false
		},
		// Add more questions and answers as needed
	])

	const toggleFAQ = index => {
		setFaqs(faqs.map((faq, i) => {
			faq.open = i === index ? !faq.open : false
			return faq
		}))
	}

	return (
		<div className="faq-section-employer">
			<h2>FAQ pour les Employeurs sur les Offres de Stage</h2>
			{faqs.map((faq, index) => (
				<div className={`faq ${faq.open ? 'open' : ''}`} key={index}>
					<div className="faq-question" onClick={() => toggleFAQ(index)}>
						{faq.question}
					</div>
					<div className="faq-answer">
						{faq.open && <p>{faq.answer}</p>}
					</div>
				</div>
			))}
		</div>
	)
}

export default FAQEmployer
