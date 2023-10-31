import {render, screen} from '@testing-library/react';
import JobOfferList from "./JobOfferList";
import React from "react";

describe('JobOfferList', () => {
    const mockData = [
        {id: 1, title: "Serving Purpose", description: "test", date: "test", duration: "test", salary: "test"},
        {id: 2, title: "Bring Copilot", description: "test", date: "test", duration: "test", salary: "test"},
    ];

    const mockStudent = {
        firstName: "John",
        lastName: "Doe",
        email: "john.doe@example.com",
        jobApplications: [1],
        cvFile: {
            fileData: "mockData"
        }
    }

    test('renders jobOfferList with no offers', () => {
        render(<JobOfferList jobOffers={[]}/>);
        const title = screen.getByText('noOpenInternship');
        expect(title).toBeInTheDocument();
    });

    test('render', async () => {
        render(<JobOfferList jobOffers={mockData} user={mockStudent}/>);
        expect(screen.getByText('selectInternship')).toBeInTheDocument();
    });
});