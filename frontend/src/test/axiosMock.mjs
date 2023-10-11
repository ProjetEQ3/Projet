import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import '@testing-library/jest-dom';

const axiosMock = new MockAdapter(axios);

axiosMock.onPost('/user/login').reply(200, {
    user: {
        id: 1,
        email: 'bob@bob.ca',
        password: 'password',
        firstName: 'Bob',
        lastName: 'Bobson',
        cvFile: {
            id: 1,
            name: 'Bobson_CV.pdf',
            fileData: 'sfljksflkdsjlkfjsp',
        },
        role: 'ROLE_STUDENT',
        department: '_420B0'
    }
});

export default axios;