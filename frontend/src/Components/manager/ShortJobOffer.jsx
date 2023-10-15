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
        <div data-testid="shadowElement" className={`row ${!isHovered? 'm-2':'m-1 shadow'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-6">
                        <h4 data-testid="shortTitle" className="text-dark fw-light pt-1">{jobOffer.title}</h4>
                        <p data-testid="shortDepartment" className="text-dark fw-light mb-3">{t(jobOffer.department)}</p>
                        <p data-testid="shortCandidate" className="text-dark fw-light float-sm-end">{t('nbOfCandidates')} <span><strong>{jobOffer.nbOfCandidates}</strong></span></p>
                    </div>
                    <div className="col-6 my-auto d-flex justify-content-around">
                        <div className="d-none d-lg-block my-auto col-6 text-center">
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
