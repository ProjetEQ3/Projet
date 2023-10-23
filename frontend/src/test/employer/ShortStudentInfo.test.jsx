import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import ShortStudentInfo from '../../Components/employer/ShortStudentInfo';

describe("ShortStudentInfo", () => {

    const mockStudent = {
        firstName: "Sam",
        lastName: "Sam",
        email: "sam.sam@example.com",
        jobApplications: [1],
        cvFile: {
            fileData: "mockData"
        }
    };

    it("renders correctly", () => {
        const { getByText } = render(<ShortStudentInfo student={mockStudent} />);
        expect(getByText("Sam Sam - sam.sam@example.com")).toBeInTheDocument();
    });

    it("handles preview button click", () => {
        const { getByText } = render(<ShortStudentInfo student={mockStudent} />);
        fireEvent.click(getByText('preview'));
        expect(screen.getByTestId("pdf-preview-mock-element")).toBeInTheDocument();
    });

});
