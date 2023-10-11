import {fireEvent, render, screen} from '@testing-library/react';
import React from "react";
import ManagerPage from "../../Components/page/ManagerPage";
import axiosInstance from "axios";
import MockAdapter from "axios-mock-adapter";
import {act} from "react-dom/test-utils";
import JobOffers from "../../Components/manager/JobOffers";

describe('ManagerPage', () => {
    const mock = new MockAdapter(axiosInstance);
    test('renders ManagerPage internship button is present', () => {
        render(<ManagerPage />);

        const internshipButton = screen.getByText('internship');
        expect(internshipButton).toBeInTheDocument();
        expect(internshipButton).toHaveClass('col-6 btn btn-outline-ose active');
        expect(internshipButton).toHaveTextContent(/internship/i);
    });

    test('renders ManagerPage internship button is clickable', () => {
        render(<ManagerPage />);
        const cvButton = screen.getByText('CVs');
        const internshipButton = screen.getByText('internship');
        fireEvent.click(internshipButton);
        expect(internshipButton).toHaveClass('col-6 btn btn-outline-ose active');
        expect(cvButton).toHaveClass('col-6 btn btn-outline-ose');
    });

    test('renders ManagerPage CVs button is present', () => {
        render(<ManagerPage />);
        const cvButton = screen.getByText('CVs');
        expect(cvButton).toBeInTheDocument();
        expect(cvButton).toHaveClass('col-6 btn btn-outline-ose');
        expect(cvButton).toHaveTextContent(/CVs/i);
    });

    test('renders ManagerPage CVs button is clickable', () => {
       render(<ManagerPage />);
        const internshipButton = screen.getByText('internship');
        const cvButton = screen.getByText('CVs');
        fireEvent.click(cvButton);
        expect(cvButton).toHaveClass('col-6 btn btn-outline-ose active');
        expect(internshipButton).toHaveClass('col-6 btn btn-outline-ose');
    });

    test('getAlloffers is called', async () => {
        const mockData = [
            {id: 1, title: "test1", description: "test", date: "test", duration: "test", salary: "test", manager: "test", status: "test"},
            {id: 2, title: "test2", description: "test", date: "test", duration: "test", salary: "test", manager: "test", status: "test"},
        ];

        mock.onGet('manager/jobOffers/all').reply(200, mockData);

        render(<JobOffers offers={mockData}/>);
        await act(async () => {
            const offer1 = await screen.findAllByText('test1');
            const offer2 = await screen.findAllByText('test2');

            for (let i = 0; i < offer1.length; i++) {
                expect(offer1[i]).toBeInTheDocument();
            }
            for (let i = 0; i < offer2.length; i++) {
                expect(offer2[i]).toBeInTheDocument();
            }
        });
    });
});
