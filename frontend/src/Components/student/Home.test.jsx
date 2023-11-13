import {render, fireEvent, getAllByTestId} from '@testing-library/react';
import Home from "./Home";

describe('Home', () => {
    const mockJobOffers = [
        { id: 1, isViewed: false },
        { id: 2, isViewed: true },
    ];
    const mockApplications = [
        { id: 1 },
        { id: 2 },
    ];
    const mockContracts = [
        { id: 1, studentSignature: null },
        { id: 2, studentSignature: 'signed' },
    ];
    const mockCv = { cvState: 'SUBMITTED' };
    const handleViewJobOffer = jest.fn();
    const setIdElement = jest.fn();
    const setTab = jest.fn();

    it('displays job offers that are not viewed', () => {
        const { getAllByTestId } = render(<Home jobOffers={mockJobOffers} applications={mockApplications} cv={mockCv} contracts={mockContracts} handleViewJobOffer={handleViewJobOffer} setIdElement={setIdElement} setTab={setTab} />);
        const displayedOffers = getAllByTestId('job-offer');
        expect(displayedOffers.length).toBe(1);
    });

    it('displays all applications', () => {
        const { getAllByTestId } = render(<Home jobOffers={mockJobOffers} applications={mockApplications} cv={mockCv} contracts={mockContracts} handleViewJobOffer={handleViewJobOffer} setIdElement={setIdElement} setTab={setTab} />);
        const displayedApplications = getAllByTestId('application');
        expect(displayedApplications.length).toBe(2);
    });

    it('displays contracts that are not signed', () => {
        const { getAllByTestId } = render(<Home jobOffers={mockJobOffers} applications={mockApplications} cv={mockCv} contracts={mockContracts} handleViewJobOffer={handleViewJobOffer} setIdElement={setIdElement} setTab={setTab} />);
        const displayedContracts = getAllByTestId('contract');
        expect(displayedContracts.length).toBe(1);
    });

    it('handles job offer click', () => {
        const { getByTestId } = render(<Home jobOffers={mockJobOffers} applications={mockApplications} cv={mockCv} contracts={mockContracts} handleViewJobOffer={handleViewJobOffer} setIdElement={setIdElement} setTab={setTab} />);
        fireEvent.click(getByTestId('job-offer'));
        expect(handleViewJobOffer).toHaveBeenCalled();
    });

    it('handles application click', () => {
        const { getAllByTestId } = render(<Home jobOffers={mockJobOffers} applications={mockApplications} cv={mockCv} contracts={mockContracts} handleViewJobOffer={handleViewJobOffer} setIdElement={setIdElement} setTab={setTab} />);
        fireEvent.click(getAllByTestId('application')[0]);
        expect(setIdElement).toHaveBeenCalled();
    });

    it('handles contract click', () => {
        const { getByTestId } = render(<Home jobOffers={mockJobOffers} applications={mockApplications} cv={mockCv} contracts={mockContracts} handleViewJobOffer={handleViewJobOffer} setIdElement={setIdElement} setTab={setTab} />);
        fireEvent.click(getByTestId('contract'));
        expect(setTab).toHaveBeenCalledWith('contract');
    });
});