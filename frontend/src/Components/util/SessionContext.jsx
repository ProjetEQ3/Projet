// SessionContext.js
import React, {createContext, useState, useContext, useEffect} from 'react';
import {axiosInstance} from "../../App";

const SessionContext = createContext();

export const SessionProvider = ({ user, children }) => {
    const [sessions, setSessions] = useState([]);
    const [selectedSessionIndex, setSelectedSessionIndex] = useState(0);

    useEffect(() => {
        const fetchSessions = async () => {
            axiosInstance
                .get("/user/semesters")
                .then((response) => {
                    setSessions(response.data);
                    axiosInstance.defaults.params['season'] = response.data[0].season;
                    axiosInstance.defaults.params['year'] = response.data[0].year;
                    setSelectedSessionIndex(1);
                })
                .catch((error) => {
                    if (error.response?.status === 401) return;
                });
        }
        fetchSessions();
    }, [user]);

    const updateSession = (index) => {
        setSelectedSessionIndex(index);

        axiosInstance.defaults.params['season'] = sessions[index].season;
        axiosInstance.defaults.params['year'] = sessions[index].year;
    };

    return (
        <SessionContext.Provider value={{ sessions, selectedSessionIndex, updateSession }}>
            {children}
        </SessionContext.Provider>
    );
};

export const useSession = () => {
    return useContext(SessionContext);
};
