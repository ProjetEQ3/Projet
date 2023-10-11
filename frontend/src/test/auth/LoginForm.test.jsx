import React from 'react';
import axios from './axiosMock.mjs';
import { render, fireEvent, screen } from '@testing-library/react';
import LoginForm from '../../Components/auth/LoginForm';

describe('LoginForm Component', () => {
    it('should render the GlucOSE title', () => {
        render(<LoginForm />);
        const titleElement = screen.getByText('GlucOSE');
        expect(titleElement).toBeInTheDocument();
    });

    it('should display email input field', () => {
        render(<LoginForm />);
        const emailInput = screen.getByPlaceholderText('Email');
        expect(emailInput).toBeInTheDocument();
    });

    it('should display password input field', () => {
        render(<LoginForm />);
        const passwordInput = screen.getByPlaceholderText('Password'); // Replace with your actual placeholder text
        expect(passwordInput).toBeInTheDocument();
    });

    it('should display the login button', () => {
        render(<LoginForm />);
        const loginButton = screen.getByText('Login'); // Replace with your actual button text
        expect(loginButton).toBeInTheDocument();
    });

    it('should display error messages', () => {
        render(<LoginForm />);
        const emailError = screen.getByText('Email error message'); // Replace with your actual error message text
        const passwordError = screen.getByText('Password error message'); // Replace with your actual error message text
        expect(emailError).toBeInTheDocument();
        expect(passwordError).toBeInTheDocument();
    });
});

// You can add more test cases for other elements and interactions as needed.