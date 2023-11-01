import React from 'react'
import {render, screen, fireEvent} from '@testing-library/react'
import FullJobOffer from './FullJobOffer'
import userEvent from "@testing-library/user-event"

jest.mock('react-i18next', () => ({
    useTranslation: () => ({
        t: (key) => key,
        i18n: {
            on: jest.fn(),
            off: jest.fn()
        }
    })
}))

jest.mock('../../Components/util/Loading.jsx', () => () => <div data-testid="loading-component">Loading...</div>)

jest.mock('../../Components/util/State.jsx', () => () => <div data-testid="state-component">Active</div>)

describe('FullJobOffer', () => {
    const mockJobOffer = {
        id: 1,
        title: 'Software Developer',
        jobOfferState: 'Active',
        department: 'IT',
        location: 'Montreal',
        startDate: '2023-01-01',
        duration: 4,
        expirationDate: '2023-02-01',
        salary: 25,
        hoursPerWeek: 40,
        description: 'sheesh'
    }

    it('renders job offer when provided', () => {
        render(<FullJobOffer isLoading={false} jobOffer={mockJobOffer}/>)
        expect(screen.getByText('Software Developer')).toBeInTheDocument()
        expect(screen.getByText('IT')).toBeInTheDocument()
        expect(screen.getByText('Montreal')).toBeInTheDocument()
        expect(screen.getByText(/startDate2023-01-01/)).toBeInTheDocument()
        expect(screen.getByText(/duration4week/)).toBeInTheDocument()
        expect(screen.getByText(/expirationDate\s+2023-02-01/)).toBeInTheDocument()
        expect(screen.getByText('25$/h')).toBeInTheDocument()
        expect(screen.getByText(/40h\/week/)).toBeInTheDocument()
        expect(screen.getByText('sheesh')).toBeInTheDocument()
    })

    it('opens the modal when "Edit" button is clicked', () => {
        render(<FullJobOffer isLoading={false} jobOffer={mockJobOffer}/>)
        fireEvent.click(screen.getByTestId('edit-button'))
        expect(screen.getByText('Software Developer')).toBeInTheDocument()
    })

    it('renders job offer title when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText(mockJobOffer.title)).toBeInTheDocument()
    })

    it('renders job offer state when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText(/Active/i)).toBeInTheDocument()
    })

    it('renders job offer department when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText(mockJobOffer.department)).toBeInTheDocument()
    })

    it('renders job offer location when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText(mockJobOffer.location)).toBeInTheDocument()
    })

    it('renders job offer start date when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText('startDate' + mockJobOffer.startDate)).toBeInTheDocument()
    })

    it('renders job offer duration when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText('duration' + mockJobOffer.duration + 'week')).toBeInTheDocument()
    })

    it('renders job offer expiration date when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText('expirationDate ' + mockJobOffer.expirationDate)).toBeInTheDocument()
    })

    it('renders job offer salary when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText(mockJobOffer.salary + '$/h')).toBeInTheDocument()
    })

    it('renders job offer hours per week when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText(/^40/)).toBeInTheDocument()
    })

    it('renders job offer description when provided', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        expect(screen.getByText(mockJobOffer.description)).toBeInTheDocument()
    })

    it('render edit button when the edit modal button is clicked', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        const editModalButton = screen.getByTestId('editModal')
        expect(editModalButton).toBeInTheDocument()
        userEvent.click(editModalButton)
        expect(screen.getByTestId('edit')).toBeInTheDocument()
    })

    it('opens the modal when the edit modal button is clicked', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        const editModalButton = screen.getByTestId('editModal')
        userEvent.click(editModalButton)
        expect(screen.getByTestId('edit')).toBeInTheDocument()
    })

    it('renders the edit button', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        const editButton = screen.getByTestId('edit-button')
        expect(editButton).toBeInTheDocument()
        expect(editButton).toHaveTextContent('edit')
    })

    it('opens the modal when the edit button is clicked', () => {
        render(<FullJobOffer jobOffer={mockJobOffer}/>)
        const editButton = screen.getByTestId('editModal')
        userEvent.click(editButton)
        expect(screen.getByTestId('edit')).toBeInTheDocument()
    })

    it('renders input fields with job offer data', () => {
        render(<FullJobOffer jobOffer={mockJobOffer} />)
        userEvent.click(screen.getByTestId('editModal'))
        expect(screen.getByPlaceholderText('Software Developer')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('Montreal')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('2023-01-01')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('4 semaine(s)')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('2023-02-01')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('25$/h')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('40h/semaine')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('sheesh')).toBeInTheDocument()
    })

})
