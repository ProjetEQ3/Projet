import React from 'react';
import {render, fireEvent} from '@testing-library/react';
import FAQManager from './FAQManager'

describe('FAQManager', () => {
	let renderedComponent

	beforeEach(() => {
		renderedComponent = render(<FAQManager />)
	})

	test('renders FAQManager component and toggles FAQ answer', () => {
		const { getByText, queryByText } = render(<FAQManager />)
		const firstQuestion = getByText('Quelles sont les étapes pour créer un programme de stage réussi?')
		expect(firstQuestion).toBeInTheDocument()
		fireEvent.click(firstQuestion)
		const firstAnswer = queryByText(/Un programme de stage réussi commence par/)
		expect(firstAnswer).toBeInTheDocument()
		fireEvent.click(firstQuestion)
		const toggledOffFirstAnswer = queryByText(/Un programme de stage réussi commence par/)
		expect(toggledOffFirstAnswer).not.toBeInTheDocument()
	})

	test('all FAQs are initially closed', () => {
		const { queryAllByText } = renderedComponent
		const answers = queryAllByText(/Un programme de stage réussi commence par/)
		answers.forEach(answer => {
			expect(answer).not.toBeInTheDocument()
		})
	})

	test('can toggle an individual FAQ', () => {
		const { getByText, queryByText } = renderedComponent;

		const question = getByText('Quelles sont les étapes pour créer un programme de stage réussi?');
		fireEvent.click(question);
		let answer = queryByText(/Un programme de stage réussi commence par/);
		expect(answer).toBeInTheDocument();

		fireEvent.click(question);
		answer = queryByText(/Un programme de stage réussi commence par/);
		expect(answer).not.toBeInTheDocument();
	})

	test('toggles different FAQ answers and ensures only one can be open at a time', () => {
		const { getAllByText, queryByText } = render(<FAQManager />)
		const firstQuestion = getAllByText('Quelles sont les étapes pour créer un programme de stage réussi?')[0]
		fireEvent.click(firstQuestion)
		const firstAnswer = queryByText(/Un programme de stage réussi commence par/)
		expect(firstAnswer).toBeInTheDocument()
		const secondQuestion = getAllByText('Comment puis-je m’assurer que le stage est bénéfique à la fois pour l’étudiant et pour l’entreprise?')[0]
		fireEvent.click(secondQuestion)
		expect(firstAnswer).not.toBeInTheDocument()
		const secondAnswer = queryByText(/Offrez des projets significatifs qui favorisent l'apprentissage/)
		expect(secondAnswer).toBeInTheDocument()
	})

})
