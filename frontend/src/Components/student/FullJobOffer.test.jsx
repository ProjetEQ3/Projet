import React from 'react';
import {fireEvent, render, screen} from '@testing-library/react';
import FullJobOffer from "./FullJobOffer";

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
    }

    const mockStudent = {
        firstName: "John",
        lastName: "Doe",
        email: "john.doe@example.com",
        jobApplications: [1]
    }

    const applyForJobOffer = jest.fn();

    it('renders job offer when provided', () => {
        render(<FullJobOffer user={mockStudent} jobOffer={mockJobOffer}/>);

        screen.debug()

        expect(screen.getByText('Software Developer')).toBeInTheDocument();
        expect(screen.getByText('IT')).toBeInTheDocument();
        expect(screen.getByText('Montreal')).toBeInTheDocument();
        expect(screen.getByText(/startDate2023-01-01/)).toBeInTheDocument();
        expect(screen.getByText(/duration4week/)).toBeInTheDocument();
        expect(screen.getByText('25$/h')).toBeInTheDocument();
        expect(screen.getByText(/40h\/week/)).toBeInTheDocument();
        expect(screen.getByText('sheesh')).toBeInTheDocument();
    });

    it('Apply if student is allowed to apply', () => {
        mockStudent.cvFile = {
            id: 1,
            fileName: 'cv.pdf',
            type: 'application/pdf',
            fileData: 'Mocked file data',
            cvState: 'Approved'
        }
        render(<FullJobOffer user={mockStudent} jobOffer={mockJobOffer} applyForJobOffer={applyForJobOffer} />);
        fireEvent.click(screen.getByTestId('apply-button'));
    });
});