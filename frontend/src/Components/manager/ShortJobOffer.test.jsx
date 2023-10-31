import React from 'react';
import {fireEvent, render, screen} from '@testing-library/react';
import ShortJobOffer from "./ShortJobOffer";

describe('ShortJobOffer', () => {
    const mockOffer = {
        id: 1,
        title: "testTitre",
        description: "_420B0",
        date: "testDate",
        duration: "testDuration",
        salary: "testSalary",
        manager: "testManager",
        status: "testStatus",
        nbOfCandidates: 1
    };

    test('renders ShortJobOffer title is present', () => {
        render(<ShortJobOffer jobOffer={mockOffer} index={1}/>);
        const title = screen.getByTestId('shortTitle');
        expect(title).toBeInTheDocument();
        expect(title).toHaveTextContent(mockOffer.title);
    });

    test('renders ShortJobOffer department is present', () => {
        render(<ShortJobOffer jobOffer={mockOffer} index={1}/>);
        const department = screen.getByTestId('shortDepartment');
        expect(department).toBeInTheDocument();
    });

    test('renders ShortJobOffer number candidate is present', () => {
        render(<ShortJobOffer jobOffer={mockOffer} index={1}/>);
        const nbOfCandidates = screen.getByTestId('shortCandidate');
        expect(nbOfCandidates).toBeInTheDocument();
        expect(nbOfCandidates).toHaveTextContent(mockOffer.nbOfCandidates);
    });

    test('renders ShortJobOffer details button is present', () => {
        render(<ShortJobOffer jobOffer={mockOffer} index={1}/>);
        const detail = screen.getByText('internshipDetails');
        expect(detail).toBeInTheDocument();
    });

    test('renders ShortJobOffer details button is clickable', () => {
        render(<ShortJobOffer jobOffer={mockOffer} index={1}/>);
        const detail = screen.getByText('internshipDetails');
        fireEvent.click(detail);
        const modal = screen.getByText('internshipDetails');
        expect(modal).toBeInTheDocument();
    });

    test('renders ShortJobOffer shadow element is present', () => {
        render(<ShortJobOffer jobOffer={mockOffer} index={1}/>);
        const shadowElement = screen.getByTestId('shadowElement');
        expect(shadowElement).toBeInTheDocument();
    });

    test('renders ShortJobOffer shadow element is shadowable', () => {
        render(<ShortJobOffer jobOffer={mockOffer} index={1}/>);
        const shadowElement = screen.getByTestId('shadowElement');

        fireEvent.mouseEnter(shadowElement);
        expect(shadowElement).toHaveClass('m-1 shadow');

        fireEvent.mouseLeave(shadowElement);
        expect(shadowElement).toHaveClass('m-2');
    });
});