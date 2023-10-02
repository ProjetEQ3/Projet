import React from "react";
import { useTranslationContext } from "./TranslationContext";

const LngSelector = () => {
    const { langue, changeLanguage } = useTranslationContext();

    const handleLanguageChange = (e) => {
        const selectedLanguage = e.target.value;
        changeLanguage(selectedLanguage);
    };

    return (
        <form className="w-25">
            <div className="form-group">
                <select className="form-control" id="lng-selector" onChange={handleLanguageChange} value={langue}>
                    <option value="en">EN</option>
                    <option value="fr">FR</option>
                </select>
            </div>
        </form>
    );
};

export default LngSelector;
