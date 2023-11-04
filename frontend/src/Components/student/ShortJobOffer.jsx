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
        <div className={`row clickable ${!isHovered? 'm-2':'m-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-12">
                        <h5 data-testid="job-title" className={`text-dark pt-1 ${jobOffer.hasApplied? 'fw-light':''}`}>{jobOffer.title}</h5>
                        <p data-testid="job-department" className="text-dark fw-light mb-3">{t(jobOffer.department)}</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ShortJobOffer;