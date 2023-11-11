import React from 'react'
import { render, fireEvent } from '@testing-library/react'
import FAQEmployer from './FAQEmployer'

describe('FAQEmployer', () => {
	let renderedComponent

	beforeEach(() => {
		renderedComponent = render(<FAQEmployer />)
	})

	test('renders FAQEmployer component and toggles FAQ answer', () => {
		const { getByText, queryByText } = renderedComponent
		const firstQuestion = getByText('Comment puis-je offrir des stages dans mon entreprise?')
		expect(firstQuestion).toBeInTheDocument()

		fireEvent.click(firstQuestion)
		const firstAnswer = queryByText(/Pour offrir des stages/)
		expect(firstAnswer).toBeInTheDocument()

		fireEvent.click(firstQuestion)
		const toggledOffFirstAnswer = queryByText(/Pour offrir des stages/)
		expect(toggledOffFirstAnswer).not.toBeInTheDocument()
	})

	test('all FAQs are initially closed', () => {
		const { queryAllByText } = renderedComponent
		const answers = queryAllByText(/Pour offrir des stages/)
		answers.forEach(answer => {
			expect(answer).not.toBeInTheDocument()
		})
	})

	test('can toggle an individual FAQ', () => {
		const { getByText, queryByText } = renderedComponent

		const question = getByText('Comment puis-je offrir des stages dans mon entreprise?')
		fireEvent.click(question)
		let answer = queryByText(/Pour offrir des stages/)
		expect(answer).toBeInTheDocument()

		fireEvent.click(question)
		answer = queryByText(/Pour offrir des stages/)
		expect(answer).not.toBeInTheDocument()
	})

	test('toggles different FAQ answers and ensures only one can be open at a time', () => {
		const { getAllByText, queryByText } = renderedComponent
		const questions = getAllByText(/Quels sont les avantages/, { exact: false })

		fireEvent.click(questions[0])
		const firstAnswer = queryByText(/Les stagiaires peuvent apporter de nouvelles perspectives/)
		expect(firstAnswer).toBeInTheDocument()

		const secondQuestions = getAllByText(/Quelles sont les obligations légales/, { exact: false })
		fireEvent.click(secondQuestions[0])
		expect(firstAnswer).not.toBeInTheDocument()

		const secondAnswer = queryByText(/Les obligations varient selon le pays et la région/)
		expect(secondAnswer).toBeInTheDocument()
	})

})
