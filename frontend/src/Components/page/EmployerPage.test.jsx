import { render, screen } from '@testing-library/react';
import React from "react";
import EmployerPage from "./EmployerPage";
import axiosInstance from "axios";
import MockAdapter from "axios-mock-adapter";
import { SessionProvider } from "../util/SessionContext";
import { MemoryRouter } from "react-router";
import User from '../../model/User'
import Contract from '../../model/Contract'

describe('EmployerPage', () => {

    const mock = new MockAdapter(axiosInstance);
    const sessionData = { selectedSessionIndex: 0 };
    const mockEmployerData = {
        "id": '1',
        "firstName": "Sam",
        "lastName": "Chan"
    }
    const mockEmployer = new User();
    mockEmployer.init(mockEmployerData);

    const renderComponent = (user) => {
        return render(
            <MemoryRouter>
                <SessionProvider value={sessionData}>
                    <EmployerPage user={user} />
                </SessionProvider>
            </MemoryRouter>
        );
    };

    afterEach(() => {
        mock.reset();
    });

    test('renders greeting message with user’s full name', () => {
        renderComponent(mockEmployer);
        const greetingMessage = screen.getByRole('heading', { name: /Sam Chan/i });
        expect(greetingMessage).toBeInTheDocument();
    });

});
