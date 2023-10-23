import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import FullJobOffer from '../../Components/employer/FullJobOffer';

describe('FullJobOffer', () => {
    const mockJobOffer = {
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
    };

    it('renders job offer when provided', () => {
        render(<FullJobOffer isLoading={false} jobOffer={mockJobOffer} />);

        screen.debug()

        expect(screen.getByText('Software Developer')).toBeInTheDocument();
        expect(screen.getByText('IT')).toBeInTheDocument();
        expect(screen.getByText('Montreal')).toBeInTheDocument();
        expect(screen.getByText(/startDate2023-01-01/)).toBeInTheDocument();
        expect(screen.getByText(/duration4week/)).toBeInTheDocument();
        expect(screen.getByText(/expirationDate\s+2023-02-01/)).toBeInTheDocument();
        expect(screen.getByText('25$/h')).toBeInTheDocument();
        expect(screen.getByText(/40h\/week/)).toBeInTheDocument();
        expect(screen.getByText('sheesh')).toBeInTheDocument();
    });

    it('opens the modal when "Edit" button is clicked', () => {
        render(<FullJobOffer isLoading={false} jobOffer={mockJobOffer} />);
        fireEvent.click(screen.getByTestId('edit-button'));
        expect(screen.getByText('Software Developer')).toBeInTheDocument();
    });

});
