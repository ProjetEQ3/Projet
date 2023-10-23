const i18next = jest.genMockFromModule('i18next');

i18next.changeLanguage = jest.fn();
i18next.t = jest.fn((key) => key);

module.exports = i18next;
