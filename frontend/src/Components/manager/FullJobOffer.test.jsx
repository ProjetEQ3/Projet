import React from 'react';
import {fireEvent, render, screen} from '@testing-library/react';
import FullJobOffer from "./FullJobOffer";

describe('FullJobOffer', () => {
    const mockOffer = {
        id: 1,
        title: "testTitre",
        location: "testLocation",
        description: "_420B0",
        expirationDate: new Date(),
        startDate: new Date(),
        duration: 2,
        salary: 20.5,
        manager: "testManager",
        status: "testStatus",
        hoursPerWeek: 1,
        nbOfCandidates: 1
    }
    test('renders FullJobOffer modal title is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const title = screen.getByText('authorizeOffer');
        expect(title).toBeInTheDocument();
        expect(title).toHaveTextContent(/authorizeOffer/i);
    });

    test('renders FullJobOffer X is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const x = screen.getByTestId('faX');
        expect(x).toBeInTheDocument();
    });

    test('renders FullJobOffer X is clickable before refuse', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const x = screen.getByTestId('faX');
        fireEvent.click(x);

        const modal = screen.getByText('authorizeOffer');
        expect(modal).toBeInTheDocument();
    });

    test('renders FullJobOffer X is clickable after refuse', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        fireEvent.click(refuse);

        const x = screen.getByTestId('faX');
        fireEvent.click(x);

        const modal = screen.getByText('authorizeOffer');
        expect(modal).toBeInTheDocument();
    });

    test('renders FullJobOffer title is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const title = screen.getByTestId('fullTitle');
        expect(title).toBeInTheDocument();
        expect(title).toHaveTextContent(mockOffer.title);
    });

    test('renders FullJobOffer candidate is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const candidate = screen.getByTestId('fullCandidate');
        expect(candidate).toBeInTheDocument();
        expect(candidate).toHaveTextContent(mockOffer.nbOfCandidates);
    });

    test('renders FullJobOffer department is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const department = screen.getByTestId('fulldepartment');
        expect(department).toBeInTheDocument();
    });

    test('renders FullJobOffer location is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const location = screen.getByTestId('fullLocation');
        expect(location).toBeInTheDocument();
        expect(location).toHaveTextContent(mockOffer.location);
    });

    test('renders FullJobOffer startDate is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const startDate = screen.getByTestId('fullStartDate');
        expect(startDate).toBeInTheDocument();
        expect(startDate).toHaveTextContent(mockOffer.startDate);
    });

    test('renders FullJobOffer duration is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const duration = screen.getByTestId('fullDuration');
        expect(duration).toBeInTheDocument();
        expect(duration).toHaveTextContent(mockOffer.duration);
    });

    test('renders FullJobOffer estimate is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const estimate = screen.getByTestId('fullEstimate');
        expect(estimate).toBeInTheDocument();
    });

    test('renders FullJobOffer expiration is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const expiration = screen.getByTestId('fullExpiration');
        expect(expiration).toBeInTheDocument();
        expect(expiration).toHaveTextContent(mockOffer.expirationDate);
    });

    test('renders FullJobOffer salary is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const salary = screen.getByTestId('fullSalary');
        expect(salary).toBeInTheDocument();
        expect(salary).toHaveTextContent(mockOffer.salary);
    });

    test('renders FullJobOffer hoursPerWeek is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const hoursPerWeek = screen.getByTestId('fullHoursPerWeek');
        expect(hoursPerWeek).toBeInTheDocument();
        expect(hoursPerWeek).toHaveTextContent(mockOffer.hoursPerWeek);
    });

    test('renders FullJobOffer description is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const description = screen.getByTestId('fullDescription');
        expect(description).toBeInTheDocument();
        expect(description).toHaveTextContent(mockOffer.description);
    });

    test('renders FullJobOffer accept button is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const accept = screen.getByTestId('acceptButton');
        expect(accept).toBeInTheDocument();
        expect(accept).toHaveTextContent(/accept/i);
    });

    test('renders FullJobOffer refuse button is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        expect(refuse).toBeInTheDocument();
        expect(refuse).toHaveTextContent(/refuse/i);
    });

    test('renders FullJobOffer accept button is clickable', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const accept = screen.getByTestId('acceptButton');
        fireEvent.click(accept);

        const modal = screen.getByText('authorizeOffer');
        expect(modal).toBeInTheDocument();
    });

    test('renders FullJobOffer refuse button is clickable', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        fireEvent.click(refuse);

        const modal = screen.getByText('confirmRefusal');
        expect(modal).toBeInTheDocument();

    });

    test('renders FullJobOffer footer modal label is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        fireEvent.click(refuse);

        const title = screen.getByTestId('footerLabel');
        expect(title).toBeInTheDocument();
        expect(title).toHaveTextContent(/confirmRefusal/i);
    });

    test('renders FullJobOffer refusal input is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        fireEvent.click(refuse);

        const input = screen.getByTestId('refusalInput');
        expect(input).toBeInTheDocument();
        expect(input).toHaveAttribute('placeholder', 'refusalReason');
    });

    test('renders FullJobOffer cancel button is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        fireEvent.click(refuse);

        const cancel = screen.getByTestId('cancelButton');
        expect(cancel).toBeInTheDocument();
        expect(cancel).toHaveTextContent(/cancel/i);
    });

    test('renders FullJobOffer cancel button is clickable', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        fireEvent.click(refuse);

        const cancel = screen.getByTestId('cancelButton');
        fireEvent.click(cancel);

        const modal = screen.getByText('authorizeOffer');
        expect(modal).toBeInTheDocument();
    });

    test('renders FullJobOffer confirm button is present', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        fireEvent.click(refuse);

        const confirm = screen.getByTestId('confirmButton');
        expect(confirm).toBeInTheDocument();
        expect(confirm).toHaveTextContent(/confirm/i);
    });

    test('renders FullJobOffer confirm button is clickable without refusal reason', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        fireEvent.click(refuse);

        const confirm = screen.getByTestId('confirmButton');
        fireEvent.click(confirm);

        const modal = screen.getByText('authorizeOffer');
        expect(modal).toBeInTheDocument();
    });

    test('renders FullJobOffer confirm button with refusal reason', () => {
        render(<FullJobOffer jobOffer={mockOffer} index={1}/>);
        const refuse = screen.getByTestId('refuseButton');
        fireEvent.click(refuse);

        const refusalInput = screen.getByTestId('refusalInput');
        fireEvent.change(refusalInput, {target: {value: 'test'}});

        const confirm = screen.getByTestId('confirmButton');
        fireEvent.click(confirm);

        const modal = screen.getByText('authorizeOffer');
        expect(modal).toBeInTheDocument();
    });
});