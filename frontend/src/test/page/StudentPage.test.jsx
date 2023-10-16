import {act, fireEvent, render, screen} from '@testing-library/react';
import React from "react";
import StudentPage from "../../Components/page/StudentPage";
import axiosInstance from "axios";
import MockAdapter from "axios-mock-adapter";
import {MemoryRouter} from "react-router";
describe('StudentPage', () => {
    const mock = new MockAdapter(axiosInstance);

    test('renders StudentPage internship button is present', () => {
        render(
            <MemoryRouter>
                <StudentPage />
            </MemoryRouter>
        );

        const internshipButton = screen.getByText('jobOffers');
        expect(internshipButton).toBeInTheDocument();
    });
    test('renders StudentPage internship button is clickable', () => {
        render(
            <MemoryRouter>
                <StudentPage />
            </MemoryRouter>
        );

        const cvButton = screen.getByText('CV');
        const internshipButton = screen.getByText('jobOffers');
        const applicationsButton = screen.getByText('myApplications');
        fireEvent.click(internshipButton);
    });

    test('renders StudentPage applications button is present', () => {
        render(
            <MemoryRouter>
                <StudentPage />
            </MemoryRouter>
        );

        const internshipButton = screen.getByText('myApplications');
        expect(internshipButton).toBeInTheDocument();
    });
    test('renders StudentPage applications button is clickable', () => {
        render(
            <MemoryRouter>
                <StudentPage />
            </MemoryRouter>
        );

        const cvButton = screen.getByText('CV');
        const internshipButton = screen.getByText('jobOffers');
        const applicationsButton = screen.getByText('myApplications');
        fireEvent.click(internshipButton);
    });

    test('renders StudentPage CV button is present', () => {
        render(
            <MemoryRouter>
                <StudentPage />
            </MemoryRouter>
        );

        const cvButton = screen.getByText('CV');
        expect(cvButton).toBeInTheDocument();
    });
    test('renders StudentPage CV button is clickable', () => {
        const mockStudent = {
            firstName: "John",
            lastName: "Doe",
            email: "john@doe.com",
            cvFile: {
                fileData: "mockData",
                fileName: "mockFileName",
                id: 1
            }
        }
        render(
            <MemoryRouter>
                <StudentPage user={mockStudent} />
            </MemoryRouter>
        );

        const internshipButton = screen.getByText('jobOffers');
        const cvButton = screen.getByText('CV');
        const applicationsButton = screen.getByText('myApplications');
        fireEvent.click(cvButton);
    });
});
