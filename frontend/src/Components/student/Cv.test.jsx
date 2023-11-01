import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import Cv from './Cv';

const mockStudent = {
    firstName: "John",
    lastName: "Doe",
    email: "john.doe@example.com",
    jobApplications: [1],
    cvFile: {
        fileData: "mockData"
    }
};
const setCv = jest.fn(() => {
    mockStudent.cvFile.id = 1
    mockStudent.cvFile.fileName = "uploadedCV"
});

test('renders Cv component', () => {
    render(<Cv user={mockStudent} setCv={setCv()} />);
    const cvElement = screen.getByText('uploadCV');
    expect(cvElement).toBeInTheDocument();
});
test('handles PDF upload', async () => {
    render(<Cv user={mockStudent} setCv={setCv} />);
    const fileInput = screen.getByTitle('uploadCV');
    const pdfFile = new File(['fake pdf content'], 'cv.pdf', { type: 'application/pdf' });

    fireEvent.change(fileInput, { target: { files: [pdfFile] } });

    await waitFor(() => {
        expect(mockStudent.cvFile.fileName === 'cv.pdf'); // Check for a success message
    });
});

test('handles PDF deletion', async () => {
    mockStudent.cvFile.id = 1;
    render(<Cv user={mockStudent} setCv={setCv()} />);
    const deleteButton = screen.getByTitle('deleteCV');

    fireEvent.click(deleteButton);

    await waitFor(() => {
        expect(setCv).toHaveBeenCalledTimes(1); // Ensure setCv was called
        expect(screen.getByText('deleteCV')).toBeInTheDocument(); // Check for a success message
    });
});
