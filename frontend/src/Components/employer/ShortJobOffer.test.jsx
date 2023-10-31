import React from 'react';
import { render, fireEvent, screen } from "@testing-library/react";
import ShortJobOffer from "./ShortJobOffer";

describe("ShortJobOffer", () => {

    const mockJobOffer = {
        title: "Software Engineer",
        department: "Engineering",
        nbOfCandidates: 5,
        jobOfferState: "ACTIVE",
        id: "123"
    };

    const mockDeleteOffer = jest.fn();

    beforeEach(() => {
        render(<ShortJobOffer jobOffer={mockJobOffer} deleteOffer={mockDeleteOffer} />);
    });

    it('should render the job title', () => {
        expect(screen.getByTestId('job-title')).toHaveTextContent(mockJobOffer.title);
    });

    it('should render the department', () => {
        expect(screen.getByTestId('job-department')).toHaveTextContent(mockJobOffer.department);
    });

    it('should render the number of candidates', () => {
        expect(screen.getByText(new RegExp(mockJobOffer.nbOfCandidates.toString(), 'i'))).toBeInTheDocument();
    });

    it('should display the delete confirmation modal when trash icon is clicked', () => {
        const trashIcon = screen.getByTestId('trash-icon');
        fireEvent.click(trashIcon);
        const deleteModalTitle = screen.getByRole('heading', { name: /delete/i });
        expect(deleteModalTitle).toBeInTheDocument();
    });

    it('should call deleteOffer function when delete button is clicked', () => {
        const deleteModalButton = screen.getByTestId('delete-modal-button');
        fireEvent.click(deleteModalButton);
        expect(mockDeleteOffer).toHaveBeenCalledTimes(1);
    });

});
