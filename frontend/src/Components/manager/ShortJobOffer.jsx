import {useState} from "react"
import FullJobOffer from "./FullJobOffer"
import State from "../util/State"
import {useTranslation} from "react-i18next";

const ShortJobOffer = ({ jobOffer, updateJobOfferList, index }) => {
    const {t} = useTranslation()
    const [isHovered, setIsHovered] = useState(false)

    const handleMouseEnter = () => {
        setIsHovered(true)
    }

    const handleMouseLeave = () => {
        setIsHovered(false)
    }

    return (
        <div className={`row ${!isHovered? 'm-2':'m-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-8">
                        <h4 className="text-dark fw-light pt-1">{jobOffer.title}</h4>
                        <p className="text-dark fw-light mb-3">{jobOffer.department}</p>
                    </div>
                    <div className="col-4 my-auto d-flex justify-content-end justify-content-lg-between">
                        <div className="d-none d-lg-block mx-auto my-auto">
                            <State state={jobOffer.jobOfferState}/>
                        </div>
                        <div className="btn btn-outline-ose my-auto" data-bs-toggle="modal" data-bs-target={"#fullViewModal" + index}>{t('internshipDetails')}</div>
                        <div id={"fullViewModal" + index} className="modal modal-lg">
                            <FullJobOffer jobOffer={jobOffer} updateJobOfferList={updateJobOfferList}/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ShortJobOffer
