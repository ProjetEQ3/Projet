import React from "react";
import { useTranslationContext } from "./TranslationContext";
import {useDarkMode} from "../../context/DarkModeContext";

const LngSelector = () => {
    const { langue, changeLanguage } = useTranslationContext();

    const { darkMode, toggleDarkMode } = useDarkMode();

    const handleLanguageChange = (e) => {
        const selectedLanguage = e.target.value;
        changeLanguage(selectedLanguage);
    };

    const maxWidthStyle = {
        'max-width' : "50px",
    }

    return (
        <form style={maxWidthStyle} className="w-25">
            <div>
                <select className={`btn btn-light p-1 ${darkMode ? "dark-input" : ""}`} id="lng-selector" onChange={handleLanguageChange} value={langue}>
                    <option className="clickable" value="fr">FR</option>
                    <option className="clickable" value="en">EN</option>
                </select>
            </div>
        </form>
    );
};

export default LngSelector;
