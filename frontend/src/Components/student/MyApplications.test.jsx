import {render, screen} from '@testing-library/react';
import MyApplications from "./MyApplications";
import React from "react";
import {MemoryRouter} from "react-router";

describe('MyApplications', () => {
    const mockData = [
        {id: 1, title: "Serving Purpose", description: "test", date: "test", duration: "test", salary: "test"},
        {id: 2, title: "Bring Copilot", description: "test", date: "test", duration: "test", salary: "test"},
    ];

    const mockStudent = {
        firstName: "John",
        lastName: "Doe",
        email: "john.doe@example.com",
        cvFile: {
            fileData: "mockData"
        }
    }

    test('renders MyApplications with no offers', () => {
        render(
            <MemoryRouter>
                <MyApplications user={mockStudent}/>
            </MemoryRouter>
        );
        const title = screen.getByText('noJobOffers');
        expect(title).toBeInTheDocument();
    });
});