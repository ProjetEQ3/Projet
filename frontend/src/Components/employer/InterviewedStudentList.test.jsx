import React from 'react'
import {render, screen} from '@testing-library/react'
import StudentList from './StudentList'

jest.mock('../../Components/employer/ShortInterviewedStudentInfo', () => {
    return function MockedShortInterviewedStudentInfo({student}) {
        return <div>{student.firstName} {student.lastName}</div>
    }
})

jest.mock('react-toastify', () => ({
    toast: {
        info: jest.fn(),
        error: jest.fn(),
    }
}))

jest.mock('react-i18next', () => ({
    useTranslation: () => ({
        t: (key) => key,
        i18n: {
            on: jest.fn(),
            off: jest.fn(),
        }
    })
}))

jest.mock('../../App', () => ({
    axiosInstance: {
        get: jest.fn()
    }
}))

describe('<StudentList />', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    })

    it('renders without crashing', () => {
        const mockOffer = {
            students: [
                {id: '1', firstName: 'John', lastName: 'Doe', jobApplications: ['application1']},
                {id: '2', firstName: 'Jane', lastName: 'Smith', jobApplications: ['application2']}
            ]
        }
        render(<StudentList offer={mockOffer}/>)
        expect(screen.getByText(/John\s+Doe/)).toBeInTheDocument()
        expect(screen.getByText(/Jane\s+Smith/)).toBeInTheDocument()
    })
})
