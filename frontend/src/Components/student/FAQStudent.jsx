import React, {useState} from 'react'
import './FAQStudent.css'

const FAQStudent = () => {
	const [faqs, setFaqs] = useState([
		{
			question: 'Comment puis-je trouver des offres de stage?',
			answer: 'Vous pouvez chercher sur notre site, dans les sections dédiées aux offres de stage, ou vous abonner à notre newsletter.',
			open: false
		},
		{
			question: 'Quels types de stages sont disponibles?',
			answer: 'Nous offrons une variété de stages dans différents domaines, tels que le marketing, l’ingénierie, la finance, etc.',
			open: false
		},
		{
			question: 'Y a-t-il des critères spécifiques pour postuler?',
			answer: 'Oui, chaque offre de stage peut avoir ses propres critères, comme le niveau d’études ou des compétences spécifiques.',
			open: false
		},
		{
			question: 'Est-il possible de réaliser un stage à l’étranger?',
			answer: 'Oui, nous avons des partenariats avec plusieurs entreprises internationales. Vous pouvez trouver les offres de stage à l’étranger dans la section dédiée sur notre site.',
			open: false
		},
		{
			question: 'Quelle est la durée typique d’un stage?',
			answer: 'La durée des stages varie selon l\'offre et l\'entreprise. Généralement, elle est entre 3 et 6 mois, mais certains stages peuvent être plus courts ou plus longs.',
			open: false
		},
		{
			question: 'Les stages sont-ils rémunérés?',
			answer: 'Cela dépend de l\'entreprise et du type de stage. Certaines offres de stage sont rémunérées, tandis que d’autres peuvent offrir d\'autres formes de compensation, comme des avantages en nature.',
			open: false
		},
		{
			question: 'Dois-je être étudiant pour postuler à un stage?',
			answer: 'Oui, nos offres de stage sont destinées principalement aux étudiants. Vous devez être inscrit dans un établissement d\'enseignement pour être éligible.',
			open: false
		},
		{
			question: 'Comment postuler à un stage?',
			answer: 'Pour postuler, sélectionnez l’offre qui vous intéresse sur notre site et suivez les instructions pour soumettre votre candidature, généralement accompagnée de votre CV et lettre de motivation.',
			open: false
		}
	])

	const toggleFAQ = index => {
		setFaqs(faqs.map((faq, i) => {
			if(i === index){
				faq.open = !faq.open
			}else{
				faq.open = false
			}
			return faq
		}))
	}

	return (
		<div className="faq-section-student">
			<h2>FAQ sur les Offres de Stage</h2>
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

export default FAQStudent
