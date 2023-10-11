const Router = ({ children }) => <div>{children}</div>;
const Route = ({ children }) => <div>{children}</div>;
const Switch = ({ children }) => <div>{children}</div>;
const Link = ({ children }) => <div>{children}</div>;
const useHistory = () => ({
    push: jest.fn(),
    replace: jest.fn(),
    goBack: jest.fn(),
    goForward: jest.fn(),
    listen: jest.fn(),
    location: {
        pathname: 'mock-pathname',
        search: 'mock-search',
        hash: 'mock-hash',
        state: 'mock-state',
    }
});

}