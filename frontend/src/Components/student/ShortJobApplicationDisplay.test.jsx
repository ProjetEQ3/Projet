import { render, screen } from '@testing-library/react';
import ShortJobApplicationDisplay from "./ShortJobApplicationDisplay";

describe('ShortJobApplicationDisplay', () => {
    const mockApplication = {
        title: 'Test Title',
        department: 'Test Department',
        appointments: [
            {
                appointmentDate: new Date().toISOString(),
            },
        ],
    };

    it('displays job title and department', () => {
        render(<ShortJobApplicationDisplay application={mockApplication} />);
        expect(screen.getByTestId('job-title')).toHaveTextContent('Test Title');
        expect(screen.getByTestId('job-department')).toHaveTextContent('Test Department');
    });

    it('displays chosen appointment when there is only one appointment', () => {
        render(<ShortJobApplicationDisplay application={mockApplication} />);
        expect(screen.getByTestId('waiting-appointment-button-testid')).toHaveTextContent('chosenAppointment');
    });

    it('displays choose appointment when there are multiple appointments', () => {
        const multipleAppointmentsApplication = {
            ...mockApplication,
            appointments: [
                {
                    appointmentDate: new Date().toISOString(),
                },
                {
                    appointmentDate: new Date().toISOString(),
                },
            ],
        };
        render(<ShortJobApplicationDisplay application={multipleAppointmentsApplication} />);
        expect(screen.getByTestId('waiting-appointment-button-testid')).toHaveTextContent('chooseAppointment');
    });

    it('does not render when there are no appointments', () => {
        const noAppointmentsApplication = {
            ...mockApplication,
            appointments: [],
        };
        const { container } = render(<ShortJobApplicationDisplay application={noAppointmentsApplication} />);
        // eslint-disable-next-line testing-library/no-node-access
        expect(container.firstChild).toBeNull();
    });
});