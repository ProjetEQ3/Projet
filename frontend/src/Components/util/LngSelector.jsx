import React from "react";
import { useTranslationContext } from "./TranslationContext";

const LngSelector = () => {
    const { langue, changeLanguage } = useTranslationContext();

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
                <select className="btn btn-light p-1" id="lng-selector" onChange={handleLanguageChange} value={langue}>
                    <option value="fr">FR</option>
                    <option value="en">EN</option>
                </select>
            </div>
        </form>
    );
};

export default LngSelector;
