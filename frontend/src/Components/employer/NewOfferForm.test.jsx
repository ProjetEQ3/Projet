import React from 'react'
import {render, screen, fireEvent} from '@testing-library/react'
import NewOfferForm from './NewOfferForm'
import {BrowserRouter as Router} from 'react-router-dom'

describe('NewOfferForm', () => {

	it('renders the form and checks for fields', () => {
		render(
			<Router>
				<NewOfferForm isLoading={false}/>
			</Router>
		)
		expect(screen.getByPlaceholderText('internshipTitle')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('location')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('nbOfCandidatesPlaceHolder')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('internshipDescription')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('salary')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('hoursPerWeek')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('startDate')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('duration')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('expirationDate')).toBeInTheDocument()
		expect(screen.getByText('newInternshipSubmit')).toBeInTheDocument()
	})

	it('shows validation warnings when fields are wrong', async() => {
		const {
			getByRole,
			findByText
		} = render(
			<Router>
				<NewOfferForm/>
			</Router>
		)
		const nbOfCandidatesInput = getByRole('spinbutton', {name: /nbOfCandidates/i})
		fireEvent.change(nbOfCandidatesInput, {target: {value: '-1'}})
		fireEvent.click(getByRole('button', {name: /newInternshipSubmit/i}))
		const titleWarning = await findByText('titleRequired')
		expect(titleWarning).toBeInTheDocument()
		const departmentWarning = await findByText('departmentRequired')
		expect(departmentWarning).toBeInTheDocument()
		const locationWarning = await findByText('locationRequired')
		expect(locationWarning).toBeInTheDocument()
		const nbOfCandidatesWarning = await findByText('minimumNbOfCandidates')
		expect(nbOfCandidatesWarning).toBeInTheDocument()
		const descriptionWarning = await findByText('descriptionRequired')
		expect(descriptionWarning).toBeInTheDocument()
		const salaryWarning = await findByText('salaryRequired')
		expect(salaryWarning).toBeInTheDocument()
		const hoursPerWeekWarning = await findByText('hoursPerWeekRequired')
		expect(hoursPerWeekWarning).toBeInTheDocument()
		const startDateWarning = await findByText('startDateRequired')
		expect(startDateWarning).toBeInTheDocument()
		const durationWarning = await findByText('durationRequired')
		expect(durationWarning).toBeInTheDocument()
		const expirationDateWarning = await findByText('endDateRequired')
		expect(expirationDateWarning).toBeInTheDocument()
	})

})
