import React from 'react';
import {render, fireEvent, screen, waitFor} from "@testing-library/react";
import ShortJobOffer from "../../Components/student/ShortJobOffer";

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

    it('should render the appointment button when there are appointments', () => {
        const mockAppointments = ['2023-01-01T10:00:00', '2023-01-02T14:30:00'];


        render(<ShortJobOffer jobOffer={mockJobOffer} />);

        expect(screen.getByTestId("appointment-button")).toBeInTheDocument();
    });

    it('should handle appointment selection', async () => {
        const mockAppointments = ['2023-01-01T10:00:00', '2023-01-02T14:30:00'];

        render(<ShortJobOffer jobOffer={mockJobOffer} appointments={mockAppointments} />);

        fireEvent.click(screen.getByTestId("appointment-button"));
        fireEvent.click(screen.getByText(dateTimeToShortString(mockAppointments[0])));

        await waitFor(() => {
            expect(screen.getByText(dateTimeToShortString(mockAppointments[0])).toBeInTheDocument());
        });

        expect(screen.getByTestId('appointment-checkbox')).toBeChecked();
    });

    it('should render the no appointment button when there are appointments', () => {
        render(<ShortJobOffer jobOffer={mockJobOffer} />);

        expect(screen.getByText('noAppointments')).toBeInTheDocument();
        expect(screen.getByTestId("no-appointment-button-testid")).toBeInTheDocument();
    });
});
