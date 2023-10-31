import {render, screen} from '@testing-library/react';
import JobOffers from "./JobOffers";
import React from "react";


describe('JobOffers', () => {
    test('renders JobOffers title is present', () => {
        render(<JobOffers/>);
        const title = screen.getByText('allInternship');
        expect(title).toBeInTheDocument();
    });

    test('render', async () => {
        const mockData = [
            {id: 1, title: "test1", description: "test", date: "test", duration: "test", salary: "test", manager: "test", status: "test"},
            {id: 2, title: "test2", description: "test", date: "test", duration: "test", salary: "test", manager: "test", status: "test"},
        ];

        render(<JobOffers offers={mockData}/>);
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