import React from 'react';
import { render } from '@testing-library/react';
import StudentList from '../../Components/employer/StudentList';

describe('StudentList', () => {

    const mockOffer = {
        students: [
            { id: 1, name: 'John Doe' },
            { id: 2, name: 'Jane Smith' }
        ]
    };

    it('renders without crashing', () => {
        render(<StudentList offer={mockOffer} />);
    });

    it('renders correct number of ShortStudentInfo', () => {
        const { getAllByTestId } = render(<StudentList offer={mockOffer} />);
        const studentInfoComponents = getAllByTestId('short-student-info');
        expect(studentInfoComponents).toHaveLength(mockOffer.students.length);
    });

});
