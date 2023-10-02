import React, { createContext, useContext, useState, useEffect } from "react";
import i18n from "i18next";

const TranslationContext = createContext();

export const useTranslationContext = () => {
    return useContext(TranslationContext);
};

export const TranslationProvider = ({ children }) => {
    const [langue, setLangue] = useState(localStorage.getItem("langue") || "fr");
    const location = window.location.href;

    useEffect(() => {
        const init = () => {
            const lng = localStorage.getItem("langue");
            if (lng) {
                i18n.changeLanguage(lng);
                setLangue(lng);
            } else {
                localStorage.setItem("langue", "fr");
                i18n.changeLanguage("fr");
                setLangue("fr");
            }
        };

        init();
    }, [location]);

    const changeLanguage = (lng) => {
        localStorage.setItem("langue", lng);
        i18n.changeLanguage(lng);
        setLangue(lng);
    };

    return (
        <TranslationContext.Provider value={{ langue, changeLanguage }}>
            {children}
        </TranslationContext.Provider>
    );
};
