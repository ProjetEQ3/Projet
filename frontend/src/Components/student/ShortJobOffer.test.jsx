import React from 'react';
import {render, fireEvent, screen, waitFor} from "@testing-library/react";
import ShortJobOffer from "./ShortJobOffer";

describe("ShortJobOffer", () => {
    const mockJobOffer = {
        title: "Software Engineer",
        department: "Engineering",
        nbOfCandidates: 5,
        jobOfferState: "ACTIVE",
        id: "123"
    };
    beforeEach(() => {
        render(<ShortJobOffer jobOffer={mockJobOffer} />);
    });

    it('should render the job title', () => {
        expect(screen.getByTestId('job-title')).toHaveTextContent(mockJobOffer.title);
    });

    it('should render the department', () => {
        expect(screen.getByTestId('job-department')).toHaveTextContent(mockJobOffer.department);
    });
    it('should initially not render the appointment button when there are no appointments', () => {
        expect(screen.queryByTestId('appointment-button')).toBeNull();
    });
});
