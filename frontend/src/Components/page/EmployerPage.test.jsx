import { render, screen, fireEvent } from '@testing-library/react';
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

    test('renders job offers tab correctly', () => {
        renderComponent(mockEmployer);
        const jobOffersTab = screen.getByRole('button', { name: /jobOffers/i });
        expect(jobOffersTab).toBeInTheDocument();
    });

    test('renders convoked students tab correctly', () => {
        renderComponent(mockEmployer);
        const convokedStudentsTab = screen.getByRole('button', { name: /convokedStudents/i });
        expect(convokedStudentsTab).toBeInTheDocument();
    });

    test('renders contracts tab correctly', () => {
        renderComponent(mockEmployer);
        const contractsTab = screen.getByRole('button', { name: /contracts/i });
        expect(contractsTab).toBeInTheDocument();
    });

    test('renders JobOfferList when jobOffers tab is active', () => {
        renderComponent(mockEmployer);
        const jobOffersList = screen.getByTestId('job-offer-list');
        expect(jobOffersList).toBeInTheDocument();
    });

    test('renders InterviewedStudentList when convokedStudents tab is clicked', () => {
        renderComponent(mockEmployer);
        const convokedStudentsTab = screen.getByRole('button', { name: /convokedStudents/i });
        fireEvent.click(convokedStudentsTab);
        const interviewedStudentList = screen.getByTestId('interviewed-student-list');
        expect(interviewedStudentList).toBeInTheDocument();
    });

    test('renders ContractList and fetches contracts when contracts tab is clicked', async () => {
        mock.onGet(`employer/contracts/${mockEmployer.id}`).reply(200, [new Contract()]);
        renderComponent(mockEmployer);
        const contractsTab = screen.getByRole('button', { name: /contracts/i });
        fireEvent.click(contractsTab);
        const contractList = await screen.findByTestId('contract-list');
        expect(contractList).toBeInTheDocument();
    });

});
