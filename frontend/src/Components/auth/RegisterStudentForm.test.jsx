import React from 'react'
import {render, screen, fireEvent, act} from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import {MemoryRouter} from 'react-router-dom'
import {axiosInstance} from "../../App"
import RegisterStudentForm from './RegisterStudentForm'

const mockNavigate = jest.fn()

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useNavigate: () => mockNavigate
}))

jest.mock('react-i18next', () => ({
	useTranslation: () => ({
		t: (key) => key,
		i18n: {
			on: jest.fn(),
			off: jest.fn()
		}
	})
}))

jest.mock('../../App', () => ({
	axiosInstance: {
		post: jest.fn()
	}
}))

describe('<RegisterStudentForm />', () => {
	beforeEach(() => {
		jest.clearAllMocks()
		render(
			<MemoryRouter>
				<RegisterStudentForm />
			</MemoryRouter>
		)
	})

	it('renders without crashing', () => {
		expect(screen.getByPlaceholderText('placeHolderFirstName')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderLastName')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderEmail')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderMatricule')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderPassword')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderPasswordConfirm')).toBeInTheDocument()
		expect(screen.getByText('placeHolderDepartmentStudent')).toBeInTheDocument()
	})

	it('submits the form with valid data', async () => {
		axiosInstance.post.mockResolvedValueOnce({ data: {} })
		userEvent.type(screen.getByPlaceholderText('placeHolderFirstName'), 'John')
		userEvent.type(screen.getByPlaceholderText('placeHolderLastName'), 'Doe')
		userEvent.type(screen.getByPlaceholderText('placeHolderEmail'), 'john.doe@example.com')
		userEvent.type(screen.getByPlaceholderText('placeHolderMatricule'), '123456')
		userEvent.selectOptions(screen.getByRole('combobox', { name: /departmentStudent/i }), '_410B0')
		userEvent.type(screen.getByPlaceholderText('placeHolderPassword'), 'Password123')
		userEvent.type(screen.getByPlaceholderText('placeHolderPasswordConfirm'), 'Password123')
		await act(async () => {fireEvent.click(screen.getByText(/registerSubmit/i))})
		expect(axiosInstance.post).toHaveBeenCalled()
	})

	it('displays validation errors for invalid data', async () => {
		await act(async () => {fireEvent.click(screen.getByText(/registerSubmit/i))})
		expect(screen.getByText('firstNameRequired')).toBeInTheDocument()
		expect(screen.getByText('lastNameRequired')).toBeInTheDocument()
		expect(screen.getByText('emailRequired')).toBeInTheDocument()
		expect(screen.getByText('matriculeRequired')).toBeInTheDocument()
		expect(screen.getByText('departmentStudentRequired')).toBeInTheDocument()
		expect(screen.getByText('passwordRequired')).toBeInTheDocument()
		userEvent.type(screen.getByPlaceholderText('placeHolderPassword'), 'Password123')
		userEvent.type(screen.getByPlaceholderText('placeHolderPasswordConfirm'), 'Password456')
		await act(async () => {fireEvent.click(screen.getByText(/registerSubmit/i))})
		expect(screen.getByText('passwordConfirmInvalid')).toBeInTheDocument()
	})
})
