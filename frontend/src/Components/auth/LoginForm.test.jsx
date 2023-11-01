import React from 'react'
import { render, fireEvent, screen } from '@testing-library/react'
import LoginForm from './LoginForm'
import {MemoryRouter} from "react-router"

describe('LoginForm Component', () => {
    it('should render the GlucOSE title', () => {
        render(
            <MemoryRouter>
                <LoginForm />
            </MemoryRouter>
        )
        const titleElement = screen.getByText('GlucOSE')
        expect(titleElement).toBeInTheDocument()
    })

    it('should display email input field', () => {
        render(
            <MemoryRouter>
                <LoginForm />
            </MemoryRouter>
        )
        const emailInput = screen.getByPlaceholderText('placeHolderEmail')
        expect(emailInput).toBeInTheDocument()
    })

    it('should display password input field', () => {
        render(
            <MemoryRouter>
                <LoginForm />
            </MemoryRouter>
        )
        const passwordInput = screen.getByPlaceholderText('placeHolderPassword')
        expect(passwordInput).toBeInTheDocument()
    })

    it('should display the login button', () => {
        render(
            <MemoryRouter>
                <LoginForm />
            </MemoryRouter>
        )
        const loginButton = screen.getByText('loginSubmit')
        expect(loginButton).toBeInTheDocument()
    })

    it('should display error messages on invalid form submission', () => {
        render(
            <MemoryRouter>
                <LoginForm />
            </MemoryRouter>
        )
        const emailInput = screen.getByPlaceholderText('placeHolderEmail')
        const passwordInput = screen.getByPlaceholderText('placeHolderPassword')
        const loginButton = screen.getByText('loginSubmit')
        fireEvent.change(emailInput, { target: { value: 'invalidEmail' } })
        fireEvent.change(passwordInput, { target: { value: 'invalidPassword' } })
        fireEvent.click(loginButton)
        const emailError = screen.getByText('wrongEmail')
        const passwordError = screen.getByText('wrongPassword')
        expect(emailError).toBeInTheDocument()
        expect(passwordError).toBeInTheDocument()
    })
})