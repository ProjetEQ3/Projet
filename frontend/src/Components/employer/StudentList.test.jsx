import React from 'react';
import { render } from '@testing-library/react';
import StudentList from './StudentList';

describe('StudentList', () => {

    const mockOffer = {
        students: [
            { id: 1, name: 'Sam Sam' },
            { id: 2, name: 'Gab Gab' }
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
