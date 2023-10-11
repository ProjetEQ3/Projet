import {render, screen} from '@testing-library/react';
import React from "react";
import ManagerPage from "../../Components/page/ManagerPage";

test('renders ManagerPage internship button', () => {
    render(<ManagerPage />);
    const linkElement = screen.getById('internship');
    const text = screen.getByText('internship');
    expect(text).toBeInTheDocument();
});

test('renders ManagerPage CVs button', () => {
    render(<ManagerPage />);

    const linkElement = screen.getByText('CVs');
    expect(linkElement).toBeInTheDocument();
});
