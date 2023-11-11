import React, { useState } from 'react'
import './FAQManager.css' // Assurez-vous de créer et d'utiliser un fichier CSS correspondant

const FAQManager = () => {
	const [faqs, setFaqs] = useState([
		{
			question: 'Quelles sont les étapes pour créer un programme de stage réussi?',
			answer: 'Un programme de stage réussi commence par définir des objectifs clairs, sélectionner des projets pertinents, assurer une supervision adéquate et fournir une évaluation régulière des performances.',
			open: false
		},
		{
			question: 'Comment puis-je m’assurer que le stage est bénéfique à la fois pour l’étudiant et pour l’entreprise?',
			answer: 'Offrez des projets significatifs qui favorisent l\'apprentissage et assurez-vous que le stagiaire peut voir l\'impact de son travail. Encouragez également la collaboration avec les équipes pour une expérience complète.',
			open: false
		},
		{
			question: 'Quels types de soutien et de ressources devrais-je offrir aux stagiaires?',
			answer: 'Les stagiaires devraient avoir accès à des ressources de formation, à un mentor ou superviseur dédié, et à des opportunités de réseautage au sein de l\'entreprise.',
			open: false
		},
		{
			question: 'Comment évaluer les performances des stagiaires?',
			answer: 'Mettez en place des critères d\'évaluation clairs dès le début du stage. Organisez des réunions de suivi régulières et fournissez des retours constructifs.',
			open: false
		},
		{
			question: 'Quelles sont les meilleures pratiques pour intégrer les stagiaires dans les équipes existantes?',
			answer: 'Organisez une orientation complète, désignez des mentors, et impliquez les stagiaires dans des réunions d\'équipe et des activités de l\'entreprise pour favoriser leur intégration.',
			open: false
		},
		{
			question: 'Comment gérer les problèmes de performance ou de comportement avec un stagiaire?',
			answer: 'Adressez les problèmes rapidement et directement. Offrez des opportunités de correction et si nécessaire, consultez les ressources humaines pour des actions appropriées.',
			open: false
		},
		// Ajoutez d'autres questions et réponses selon les besoins
	])

	const toggleFAQ = index => {
		setFaqs(faqs.map((faq, i) => {
			faq.open = i === index ? !faq.open : false
			return faq
		}))
	}

	return (
		<div className="faq-section">
			<h2>FAQ pour les Managers sur la Gestion des Stages</h2>
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

export default FAQManager
