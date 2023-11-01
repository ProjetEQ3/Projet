import React from 'react'
import {render, screen, fireEvent, act} from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import {MemoryRouter} from 'react-router-dom'
import RegisterEmployerForm from './RegisterEmployerForm'
import {axiosInstance} from "../../App"

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

describe('<RegisterEmployerForm />', () => {
	beforeEach(() => {
		jest.clearAllMocks()
		render(
			<MemoryRouter>
				<RegisterEmployerForm />
			</MemoryRouter>
		)
	})

	it('renders without crashing', () => {
		expect(screen.getByPlaceholderText('placeHolderFirstName')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderLastName')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderEmail')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderOrganisationName')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderOrganisationPhone')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderPassword')).toBeInTheDocument()
		expect(screen.getByPlaceholderText('placeHolderPasswordConfirm')).toBeInTheDocument()
	})

	it('submits the form with valid data', async () => {
		axiosInstance.post.mockResolvedValueOnce({ data: {} })
		userEvent.type(screen.getByPlaceholderText('placeHolderFirstName'), 'John')
		userEvent.type(screen.getByPlaceholderText('placeHolderLastName'), 'Doe')
		userEvent.type(screen.getByPlaceholderText('placeHolderEmail'), 'john.doe@example.com')
		userEvent.type(screen.getByPlaceholderText('placeHolderOrganisationName'), 'JD Corp')
		userEvent.type(screen.getByPlaceholderText('placeHolderOrganisationPhone'), '+1 123-456-7890')
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
		expect(screen.getByText('organisationNameRequired')).toBeInTheDocument()
		expect(screen.getByText('organisationPhoneRequired')).toBeInTheDocument()
		expect(screen.getByText('passwordRequired')).toBeInTheDocument()
		userEvent.type(screen.getByPlaceholderText('placeHolderPassword'), 'Password123')
		userEvent.type(screen.getByPlaceholderText('placeHolderPasswordConfirm'), 'Password456')
		await act(async () => {fireEvent.click(screen.getByText(/registerSubmit/i))})
		expect(screen.getByText('passwordConfirmInvalid')).toBeInTheDocument()
	})
})




