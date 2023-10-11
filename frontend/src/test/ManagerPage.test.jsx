import {render, screen} from '@testing-library/react';
import React from "react";
import ManagerPage from "../Components/page/ManagerPage";

test('renders ManagerPage', () => {
    render(<ManagerPage />);
    const text = screen.getByText('ManagerPage');
    expect(text).toBeInTheDocument();
});