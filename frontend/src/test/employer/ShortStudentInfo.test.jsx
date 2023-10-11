import React from 'react';
import { render, fireEvent, waitFor, screen } from '@testing-library/react';
import ShortStudentInfo from '../../Components/employer/ShortStudentInfo';

describe("ShortStudentInfo", () => {

    const mockStudent = {
        firstName: "John",
        lastName: "Doe",
        email: "john.doe@example.com",
        jobApplications: [1],
        cvFile: {
            fileData: "mockData"
        }
    };

    it("renders correctly", () => {
        const { getByText } = render(<ShortStudentInfo student={mockStudent} />);
        expect(getByText("John Doe - john.doe@example.com")).toBeInTheDocument();
    });

    it("handles preview button click", () => {
        const { getByText } = render(<ShortStudentInfo student={mockStudent} />);
        fireEvent.click(getByText('preview'));
        expect(screen.getByTestId("pdf-preview-mock-element")).toBeInTheDocument();
    });

});
