import {act, fireEvent, render, screen} from '@testing-library/react';
import React from "react";
import ManagerPage from "./ManagerPage";
import axiosInstance from "axios";
import MockAdapter from "axios-mock-adapter";
import JobOffers from "../manager/JobOffers";
import {SessionProvider} from "../util/SessionContext";
import {MemoryRouter} from "react-router";
describe('ManagerPage', () => {
    const mock = new MockAdapter(axiosInstance);
    const sessionData = { selectedSessionIndex: 0 };
    const mockManager = {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "John@Doe.ca",
        "role": "MANAGER",
        "isLoggedIn": true,
    }
    test('renders ManagerPage internship button is present', () => {
        render(
            <MemoryRouter>
                <SessionProvider value={sessionData}>
                    <ManagerPage user={mockManager} />
                </SessionProvider>
            </MemoryRouter>
        );

        const internshipButton = screen.getByText('internship');
        expect(internshipButton).toBeInTheDocument();
        expect(internshipButton).toHaveClass('col-6 btn btn-outline-ose active');
        expect(internshipButton).toHaveTextContent(/internship/i);
    });

    test('renders ManagerPage internship button is clickable', () => {
        render(
            <MemoryRouter>
                <SessionProvider value={sessionData}>
                    <ManagerPage user={mockManager} />
                </SessionProvider>
            </MemoryRouter>
        );

        const cvButton = screen.getByText('CVs');
        const internshipButton = screen.getByText('internship');
        fireEvent.click(internshipButton);
        expect(internshipButton).toHaveClass('col-6 btn btn-outline-ose active');
        expect(cvButton).toHaveClass('col-6 btn btn-outline-ose');
    });

    test('renders ManagerPage CVs button is present', () => {
        render(
            <MemoryRouter>
                <SessionProvider value={sessionData}>
                    <ManagerPage user={mockManager} />
                </SessionProvider>
            </MemoryRouter>
        );

        const cvButton = screen.getByText('CVs');
        expect(cvButton).toBeInTheDocument();
        expect(cvButton).toHaveClass('col-6 btn btn-outline-ose');
        expect(cvButton).toHaveTextContent(/CVs/i);
    });

    test('renders ManagerPage CVs button is clickable', () => {
       render(
           <MemoryRouter>
               <SessionProvider value={sessionData}>
                   <ManagerPage user={mockManager} />
               </SessionProvider>
           </MemoryRouter>
       );

        const internshipButton = screen.getByText('internship');
        const cvButton = screen.getByText('CVs');
        fireEvent.click(cvButton);
        expect(cvButton).toHaveClass('col-6 btn btn-outline-ose active');
        expect(internshipButton).toHaveClass('col-6 btn btn-outline-ose');
    });
});
