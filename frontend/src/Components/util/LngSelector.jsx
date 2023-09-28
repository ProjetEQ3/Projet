import React from "react";
import {useTranslation} from "react-i18next";

const LngSelector = () => {
    const {i18n} = useTranslation()
    const changeLanguage = (lng) => {
        i18n.changeLanguage(lng)
    }
    return (
        <form className="w-25" action="">
            <div className="form-group">
                <select className="form-control" id="lng-selector" onChange={(e) => changeLanguage(e.target.value)}>
                    <option value="en">EN</option>
                    <option value="fr">FR</option>
                </select>
            </div>
        </form>
    )
}

export default LngSelector;