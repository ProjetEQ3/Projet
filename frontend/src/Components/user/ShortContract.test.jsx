import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import ShortContract from "./ShortContract";

describe('ShortContract', () => {
    const contractMock = {
        jobOfferName: 'Software Engineer',
        studentName: 'Sam Chan',
        jobOfferCompany: 'CAL',
        data: 'pdfData',
        isComplete: false,
    };

    const userMock = {
        firstName: 'Sam',
        lastName: 'Chan',
        role : 'ROLE_TEST'
    };

    const setup = () => render(<ShortContract contract={contractMock} user={userMock} />);

    test('renders job title, student name, and employer name', () => {
        setup();
        expect(screen.getByTestId('job-title')).toHaveTextContent(contractMock.jobOfferName);
        expect(screen.getByTestId('student-name')).toHaveTextContent(contractMock.studentName);
        expect(screen.getByTestId('employer-name')).toHaveTextContent(contractMock.jobOfferCompany);
    });

    test('toggles PDF preview on button click', () => {
        setup();
        const previewButton = screen.getByTestId('preview-btn');
        fireEvent.click(previewButton);
        expect(screen.getByText(/Mock PDF Viewer/i)).toBeInTheDocument();
        fireEvent.click(previewButton);
        expect(screen.queryByText(/Mock PDF Viewer/i)).toBeNull();
    });

    test('opens signing modal on sign button click', () => {
        setup();
        fireEvent.click(screen.getByTestId('sign-btn'));
        expect(screen.getByText(/signContract/i)).toBeInTheDocument();
    });

    test('closes signing modal on close button click', () => {
        setup();
        fireEvent.click(screen.getByTestId('sign-btn'));
        fireEvent.click(screen.getByText('close'));
        expect(screen.queryByText(/signContract/i)).toBeNull();
    });

    test('handles password input change', () => {
        setup();
        fireEvent.click(screen.getByTestId('sign-btn'));
        const passwordInput = screen.getByLabelText(/password/i);
        fireEvent.change(passwordInput, { target: { value: 'myPassword' } });
        expect(passwordInput.value).toBe('myPassword');
    });

    test('submits signature', () => {
        setup();
        fireEvent.click(screen.getByTestId('sign-btn'));
        fireEvent.click(screen.getByText('submit'));
        expect(screen.queryByText(/signContract/i)).toBeNull();
    });
});
