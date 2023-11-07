import React from 'react';
import {fireEvent, render, screen} from '@testing-library/react';
import ShortCv from "./ShortCv";

describe('ShortCv', () => {
    const mockData = {
        id: 1,
        fileName: "test",
        cvState: "test"

    }
    test('renders ShortCv title is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const title = screen.getByTestId('title');
        expect(title).toBeInTheDocument();
        expect(title).toHaveTextContent(mockData.fileName);
    });

    test('renders ShortCv modal button is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const modalButton = screen.getByTestId('modalButton');
        expect(modalButton).toBeInTheDocument();
        expect(modalButton).toHaveTextContent(/probation/i);
    });

    test('renders ShortCv modal button is clickable', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const modalButton = screen.getByTestId('modalButton');

        fireEvent.click(modalButton);

        const modal = screen.getByTestId('headerTitle');
        expect(modal).toBeInTheDocument();
        expect(modal).toHaveTextContent(/cvAuthorisation/i);
    });

    test('renders ShortCv headerTitle is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const headerTitle = screen.getByTestId('headerTitle');
        expect(headerTitle).toBeInTheDocument();
        expect(headerTitle).toHaveTextContent(/cvAuthorisation/i);
    });

    test('renders ShortCv headerX button is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const headerX = screen.getByTestId('headerX');
        expect(headerX).toBeInTheDocument();
    });

    test('renders ShortCv headerX button is clickable', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const headerX = screen.getByTestId('headerX');

        fireEvent.click(headerX);

        const modal = screen.queryByTestId('refusalLabel');
        expect(modal).not.toBeInTheDocument();
    });

    test('renders ShortCv bodyTitle is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const bodyTitle = screen.getByTestId('bodyTitle');
        expect(bodyTitle).toBeInTheDocument();
        expect(bodyTitle).toHaveTextContent(mockData.fileName);
    });

    test('renders ShortCv footerLabel is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const refuseButton = screen.getByTestId('refuseButton');
        fireEvent.click(refuseButton);

        const footerLabel = screen.getByTestId('footerLabel');
        expect(footerLabel).toBeInTheDocument();
        expect(footerLabel).toHaveTextContent(/confirmRefusal/i);
    });

    test('renders ShortCv acceptButton is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const acceptButton = screen.getByTestId('acceptButton');
        expect(acceptButton).toBeInTheDocument();
        expect(acceptButton).toHaveTextContent(/accept/i);
    });

    test('renders ShortCv refuseButton is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const refuseButton = screen.getByTestId('refuseButton');
        expect(refuseButton).toBeInTheDocument();
        expect(refuseButton).toHaveTextContent(/refuse/i);
    });

    test('renders ShortCv acceptButton is clickable', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const acceptButton = screen.getByTestId('acceptButton');

        fireEvent.click(acceptButton);

        const modal = screen.queryByTestId('refusalLabel');
        expect(modal).not.toBeInTheDocument();
    });

    test('renders ShortCv refuseButton is clickable', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const refuseButton = screen.getByTestId('refuseButton');

        fireEvent.click(refuseButton);

        const modal = screen.queryByTestId('refusalLabel');
        expect(modal).not.toBeInTheDocument();
    });

    test('renders ShortCv cancelButton is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const refuseButton = screen.getByTestId('refuseButton');
        fireEvent.click(refuseButton);

        const cancelButton = screen.getByTestId('cancelButton');
        expect(cancelButton).toBeInTheDocument();
        expect(cancelButton).toHaveTextContent(/cancel/i);
    });

    test('renders ShortCv cancelButton is clickable', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const refuseButton = screen.getByTestId('refuseButton');
        fireEvent.click(refuseButton);

        const cancelButton = screen.getByTestId('cancelButton');

        fireEvent.click(cancelButton);

        const modal = screen.queryByTestId('refusalLabel');
        expect(modal).not.toBeInTheDocument();
    });

    test('renders ShortCv confirmButton is present', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const refuseButton = screen.getByTestId('refuseButton');
        fireEvent.click(refuseButton);

        const confirmButton = screen.getByTestId('confirmButton');
        expect(confirmButton).toBeInTheDocument();
        expect(confirmButton).toHaveTextContent(/confirm/i);
    });

    test('renders ShortCv confirmButton is clickable without refusal reason', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const refuseButton = screen.getByTestId('refuseButton');
        fireEvent.click(refuseButton);

        const confirmButton = screen.getByTestId('confirmButton');

        fireEvent.click(confirmButton);

        const modal = screen.queryByTestId('refusalLabel');
        expect(modal).not.toBeInTheDocument();
    });

    test('renders ShortCv confirmButton is clickable with refusal reason', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const refuseButton = screen.getByTestId('refuseButton');
        fireEvent.click(refuseButton);

        const refusalInput = screen.getByTestId('refusalInput');
        fireEvent.change(refusalInput, {target: {value: 'test'}});
        const confirmButton = screen.getByTestId('confirmButton');
        fireEvent.click(confirmButton);

        const modal = screen.queryByTestId('refusalLabel');
        expect(modal).not.toBeInTheDocument();
    });

    test('renders ShortCv OpenCV function work with body title', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const openCV = screen.getByTestId('bodyTitle');
        fireEvent.click(openCV);
    });

    test('renders ShortCv OpenCV function work with modal button', () => {
        render(<ShortCv cv={mockData} index={1}/>);
        const openCV = screen.getByTestId('title');
        fireEvent.click(openCV);
    });
});