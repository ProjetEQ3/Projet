import React from 'react'
import { render, fireEvent } from '@testing-library/react'
import FAQStudent from './FAQStudent'

describe('FAQStudent', () => {
	let renderedComponent

	beforeEach(() => {
		renderedComponent = render(<FAQStudent />)
	})

	test('renders FAQStudent component and toggles FAQ answer', () => {
		const { getByText, queryByText } = renderedComponent
		const firstQuestion = getByText('Comment puis-je trouver des offres de stage?')
		expect(firstQuestion).toBeInTheDocument()

		fireEvent.click(firstQuestion)
		const firstAnswer = queryByText(/Vous pouvez chercher sur notre site/)
		expect(firstAnswer).toBeInTheDocument()

		fireEvent.click(firstQuestion)
		const toggledOffFirstAnswer = queryByText(/Vous pouvez chercher sur notre site/)
		expect(toggledOffFirstAnswer).not.toBeInTheDocument()
	})

	test('all FAQs are initially closed', () => {
		const { queryAllByText } = renderedComponent
		const answers = queryAllByText(/Vous pouvez chercher sur notre site/)
		answers.forEach(answer => {
			expect(answer).not.toBeInTheDocument()
		})
	})

	test('can toggle an individual FAQ', () => {
		const { getByText, queryByText } = renderedComponent

		const question = getByText('Comment puis-je trouver des offres de stage?')
		fireEvent.click(question)
		let answer = queryByText(/Vous pouvez chercher sur notre site/)
		expect(answer).toBeInTheDocument()

		fireEvent.click(question)
		answer = queryByText(/Vous pouvez chercher sur notre site/)
		expect(answer).not.toBeInTheDocument()
	})

	test('toggles different FAQ answers and ensures only one can be open at a time', () => {
		const { getAllByText, queryByText } = renderedComponent
		const questions = getAllByText(/Quels types de stages sont disponibles?/)

		fireEvent.click(questions[0])
		const firstAnswer = queryByText(/Nous offrons une variété de stages/)
		expect(firstAnswer).toBeInTheDocument()

		const secondQuestions = getAllByText(/Y a-t-il des critères spécifiques pour postuler?/)
		fireEvent.click(secondQuestions[0])
		expect(firstAnswer).not.toBeInTheDocument()

		const secondAnswer = queryByText(/chaque offre de stage peut avoir ses propres critères/)
		expect(secondAnswer).toBeInTheDocument()
	})

})
