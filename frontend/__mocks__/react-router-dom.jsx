const reactRouterDom = jest.genMockFromModule('react-router-dom');

reactRouterDom.useNavigate = () => jest.fn();

module.exports = {
    ...reactRouterDom,
}