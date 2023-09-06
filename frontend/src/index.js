import React from 'react';
import {createRoot} from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import RegistrationForm from "./Components/RegistrationForm";
import 'bootstrap/dist/css/bootstrap.css';

const router = createBrowserRouter([
    {
        path: "/",
        element: (
            <App/>
        ),
    },
    {
        path: "inscription",
        element: <RegistrationForm/>,
    },
    {
        path: "connexion",
        element: <div>Connexion</div>,
    }
]);

createRoot(document.getElementById("root")).render(
    <RouterProvider router={router} />
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
