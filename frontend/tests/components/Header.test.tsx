
import { render, screen, fireEvent } from '@testing-library/react';
import Header from '../../src/components/Header/Header'// Adjust the import path to your project structure
// import { vi } from 'vitest';
// import { it, describe, expect } from "vitest";
import { render, screen } from "@testing-library/react";


describe('Header Component', () => {
    const mockLogout = vi.fn();
    const mockSetLanguage = vi.fn();

    const defaultProps = {
        userName: 'John Doe',
        logout: mockLogout,
        language: 'Spanish',
        setLanguage: mockSetLanguage,
    };

    it('renders the app name', () => {
        render(<Header {...defaultProps} />);
        expect(screen.getByText('Wordio')).toBeInTheDocument();
    });
    //
    // it('displays the language select dropdown when userName and language are provided', () => {
    //     render(<Header {...defaultProps} />);
    //     const select = screen.getByRole('combobox');
    //     expect(select).toBeInTheDocument();
    //     expect(select).toHaveValue('Spanish');
    // });

    // it('calls setLanguage when a new language is selected', () => {
    //     render(<Header {...defaultProps} />);
    //     const select = screen.getByRole('combobox');
    //     fireEvent.change(select, { target: { value: 'French' } });
    //     expect(mockSetLanguage).toHaveBeenCalledWith('French');
    // });
    //
    // it('displays the logout button when userName is provided', () => {
    //     render(<Header {...defaultProps} />);
    //     const logoutButton = screen.getByText('logout');
    //     expect(logoutButton).toBeInTheDocument();
    // });

    // it('calls logout when the logout button is clicked', () => {
    //     render(<Header {...defaultProps} />);
    //     const logoutButton = screen.getByText('logout');
    //     fireEvent.click(logoutButton);
    //     expect(mockLogout).toHaveBeenCalled();
    // });
    //
    // it('does not display the language select dropdown if userName or language is missing', () => {
    //     const { rerender } = render(<Header {...defaultProps} userName="" />);
    //     expect(screen.queryByRole('combobox')).not.toBeInTheDocument();
    //
    //     rerender(<Header {...defaultProps} language="" />);
    //     expect(screen.queryByRole('combobox')).not.toBeInTheDocument();
    // });
});
