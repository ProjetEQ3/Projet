import {act, fireEvent, render, screen} from '@testing-library/react';
import React from "react";
import StudentPage from "./StudentPage";
import axiosInstance from "axios";
import MockAdapter from "axios-mock-adapter";
import {MemoryRouter} from "react-router";
import {SessionProvider} from "../util/SessionContext";
describe('StudentPage', () => {
    const mock = new MockAdapter(axiosInstance);
    const sessionData = { selectedSessionIndex: 0 };

    test('renders StudentPage internship button is present', () => {
        render(
            <MemoryRouter>
                <SessionProvider value={sessionData}>
                    <StudentPage />
                </SessionProvider>
            </MemoryRouter>
        );

        const internshipButton = screen.getByText('jobOffers');
        expect(internshipButton).toBeInTheDocument();
    });

    test('renders StudentPage internship button is clickable', () => {
        render(
            <MemoryRouter>
                <SessionProvider value={sessionData}>
                    <StudentPage />
                </SessionProvider>
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
                <SessionProvider value={sessionData}>
                    <StudentPage />
                </SessionProvider>
            </MemoryRouter>
        );

        const internshipButton = screen.getByText('myApplications');
        expect(internshipButton).toBeInTheDocument();
    });
    test('renders StudentPage applications button is clickable', () => {
        render(
            <MemoryRouter>
                <SessionProvider value={sessionData}>
                    <StudentPage />
                </SessionProvider>
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
                <SessionProvider value={sessionData}>
                    <StudentPage />
                </SessionProvider>
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
                <SessionProvider value={sessionData}>
                    <StudentPage  user={mockStudent}/>
                </SessionProvider>
            </MemoryRouter>
        );

        const internshipButton = screen.getByText('jobOffers');
        const cvButton = screen.getByText('CV');
        const applicationsButton = screen.getByText('myApplications');
        fireEvent.click(cvButton);
    });
});
