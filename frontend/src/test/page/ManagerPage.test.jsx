import {act, fireEvent, render, screen} from '@testing-library/react';
import React from "react";
import ManagerPage from "../../Components/page/ManagerPage";
import axiosInstance from "axios";
import MockAdapter from "axios-mock-adapter";
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
});
