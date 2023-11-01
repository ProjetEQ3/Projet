import { render, screen, waitFor, fireEvent } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import JobOfferList from './JobOfferList'
import { axiosInstance } from '../../App'
import {toast} from "react-toastify";

jest.mock('../../App', () => ({
	axiosInstance: {
		get: jest.fn(),
		put: jest.fn(),
		delete: jest.fn()
	}
}))

jest.mock('react-toastify', () => ({
	toast: {
		error: jest.fn(),
		success: jest.fn()
	}
}))

jest.mock('react-i18next', () => ({
	useTranslation: () => ({
		t: (key) => key,
	})
}))

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useNavigate: () => jest.fn()
}))

jest.mock('../util/SessionContext', () => ({
	useSession: () => ({ selectedSessionIndex: 0 })
}))

describe('<JobOfferList />', () => {
	const userMock = {
		id: 1,
		isLoggedIn: true
	}

	beforeEach(() => {
		jest.clearAllMocks()
	})

	it('renders without crashing', () => {
		render(
			<MemoryRouter>
				<JobOfferList user={userMock} />
			</MemoryRouter>
		)
	})

	it('fetches job offers on mount when user is logged in', async () => {
		const mockOffers = [{ id: '1', title: 'Job A' }, { id: '2', title: 'Job B' }]
		axiosInstance.get.mockResolvedValueOnce({ data: mockOffers })
		render(
			<MemoryRouter>
				<JobOfferList user={userMock} />
			</MemoryRouter>
		)
		await waitFor(() => {
			expect(axiosInstance.get)
				.toHaveBeenCalledWith('/employer/offer/all', { params: { employerId: userMock.id } })
			mockOffers.forEach(offer => {
				expect(screen.getByText(offer.title)).toBeInTheDocument()
			})
		})
	})

	it('handles API error when fetching job offers', async () => {
		axiosInstance.get.mockRejectedValueOnce(new Error('API Error'))
		render(
			<MemoryRouter>
				<JobOfferList user={userMock} />
			</MemoryRouter>
		)
		await waitFor(() => {
			expect(toast.error).toHaveBeenCalledWith('fetchErrorAPI Error')
		})
	})

})
