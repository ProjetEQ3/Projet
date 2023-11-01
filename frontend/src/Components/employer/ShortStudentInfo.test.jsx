import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import ShortStudentInfo from './ShortStudentInfo';

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

    it("handles convoke button click", () => {
        const { getByText } = render(<ShortStudentInfo student={mockStudent} />);
        fireEvent.click(getByText('convoke'));
        expect(screen.getByTestId("convoke-mock-element")).toBeInTheDocument();
        expect(screen.getByTestId("convoke-mock-element")).toHaveTextContent("convoke");
        expect(screen.getByTestId("convokeInput0")).toBeInTheDocument();
        expect(screen.getByTestId("convokeInput1")).toBeInTheDocument();
        expect(screen.getByTestId("convokeInput2")).toBeInTheDocument();
    });

    it("handles add convoke date button click", () => {
        const { getByText } = render(<ShortStudentInfo student={mockStudent} />);
        fireEvent.click(getByText('convoke'));
        fireEvent.click(screen.getByTestId("add-convoke-date-button"));
        expect(screen.getByTestId("convokeInput3")).toBeInTheDocument();
    });

    it("handles sub convoke date button click", () => {
       const { getByText } = render(<ShortStudentInfo student={mockStudent} />);
       fireEvent.click(getByText('convoke'));
       fireEvent.click(screen.getByTestId("add-convoke-date-button"));
       fireEvent.click(screen.getByTestId("sub-convoke-date-button"));
       expect(screen.queryByTestId("convokeInput3")).not.toBeInTheDocument();
    });
});
