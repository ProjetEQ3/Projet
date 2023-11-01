import React from 'react'
import { render, screen, fireEvent } from '@testing-library/react'
import RegistrationForm from './RegistrationForm'

jest.mock('../../Components/auth/RegisterStudentForm', () => {
	return function MockedRegisterStudentForm() {
		return <div>RegisterStudentForm</div>
	}
})

jest.mock('../../Components/auth/RegisterEmployerForm', () => {
	return function MockedRegisterEmployerForm() {
		return <div>RegisterEmployerForm</div>
	}
})

jest.mock('react-i18next', () => ({
	useTranslation: () => ({
		t: (key) => key,
	}),
}))

describe('<RegistrationForm />', () => {
	beforeEach(() => {
		render(<RegistrationForm />)
	})

	it('renders without crashing', () => {
		expect(screen.getByText('student')).toBeInTheDocument()
		expect(screen.getByText('employer')).toBeInTheDocument()
	})

	it('shows RegisterStudentForm by default', () => {
		expect(screen.getByText('RegisterStudentForm')).toBeInTheDocument()
		expect(screen.queryByText('RegisterEmployerForm')).not.toBeInTheDocument()
	})

	it('switches to RegisterEmployerForm on clicking the employer button', () => {
		fireEvent.click(screen.getByText('employer'))
		expect(screen.getByText('RegisterEmployerForm')).toBeInTheDocument()
		expect(screen.queryByText('RegisterStudentForm')).not.toBeInTheDocument()
	})

	it('switches back to RegisterStudentForm on clicking the student button', () => {
		fireEvent.click(screen.getByText('employer'))
		fireEvent.click(screen.getByText('student'))
		expect(screen.getByText('RegisterStudentForm')).toBeInTheDocument()
		expect(screen.queryByText('RegisterEmployerForm')).not.toBeInTheDocument()
	})
})
