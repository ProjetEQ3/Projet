import React from 'react';
import { render, fireEvent, screen } from "@testing-library/react";
import ShortJobOffer from "../../Components/employer/ShortJobOffer";

describe("ShortJobOffer", () => {

    const mockJobOffer = {
        title: "Software Engineer",
        department: "Engineering",
        nbOfCandidates: 5,
        jobOfferState: "ACTIVE",
        id: "123"
    };

    const mockDeleteOffer = jest.fn();

    it("renders job title correctly", () => {
        render(<ShortJobOffer jobOffer={mockJobOffer} deleteOffer={mockDeleteOffer} />);
        const jobTitles = screen.getAllByText("Software Engineer");
        expect(jobTitles).toHaveLength(2);
    });

    it("renders number of candidates correctly", () => {
        render(<ShortJobOffer jobOffer={mockJobOffer} deleteOffer={mockDeleteOffer} />);
        expect(screen.getByText("nbOfCandidates")).toBeInTheDocument();
        expect(screen.getByText((_, element) =>
            element.tagName.toLowerCase() === 'strong' && element.textContent === '5'
        )).toBeInTheDocument();
    });

    it('should display the delete confirmation modal when trash icon is clicked', () => {
        const { getByTestId } = render(<ShortJobOffer jobOffer={mockJobOffer} deleteOffer={mockDeleteOffer} />);
        const trashIcon = getByTestId('trash-icon');
        fireEvent.click(trashIcon);
        const deleteModalTitle = screen.getByRole('heading', { name: /delete/i });
        expect(deleteModalTitle).toBeInTheDocument();
    });

    it('should call deleteOffer function when delete button is clicked', () => {
        const { getByTestId } = render(<ShortJobOffer jobOffer={mockJobOffer} deleteOffer={mockDeleteOffer} />);
        const deleteModalButton = getByTestId('delete-modal-button');
        fireEvent.click(deleteModalButton);
        expect(mockDeleteOffer).toHaveBeenCalledTimes(1);
    });
});
