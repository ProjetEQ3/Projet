import {useState} from "react";
import {useTranslation} from "react-i18next";
const ShortJobOffer = ({jobOffer}) => {
    const {t} = useTranslation()
    const [isHovered, setIsHovered] = useState(false);

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    return (
        <div className={`row ${!isHovered? 'm-2':'m-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-12">
                        <h5 className="text-dark fw-light pt-1">{jobOffer.title}</h5>
                        <p className="text-dark fw-light mb-3">{t(jobOffer.department)}</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ShortJobOffer;