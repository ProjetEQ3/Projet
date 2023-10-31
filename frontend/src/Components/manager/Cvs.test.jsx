import React from 'react';
import {render, screen} from '@testing-library/react';
import Cvs from "./Cvs";

describe('Cvs', () => {
    test ('renders Cvs title is present', () => {
        render(<Cvs/>);
        const title = screen.getByText('studentCV');
        expect(title).toBeInTheDocument();
    });

    test('render', async () => {
        const mockData = [
            {id: 1, fileName: "test1", cvState: "test"},
            {id: 2, fileName: "test2", cvState: "test"},
        ];

        render(<Cvs cvs={mockData}/>);
        const cv1 = await screen.findAllByText('test1');
        const cv2 = await screen.findAllByText('test2');

        for (let i = 0; i < cv1.length; i++) {
            expect(cv1[i]).toBeInTheDocument();
        }
        for (let i = 0; i < cv2.length; i++) {
            expect(cv2[i]).toBeInTheDocument();
        }
    });
});