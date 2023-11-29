import {useState} from "react"
import FullJobOffer from "./FullJobOffer"
import State from "./State"
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";

const ShortJobOffer = ({ jobOffer, updateJobOfferList, index, updateJobOfferListAfterApprovalOrRefusal }) => {
    const {t} = useTranslation()
    const [isHovered, setIsHovered] = useState(false)

    const { darkMode } = useDarkMode();

    const handleMouseEnter = () => {
        setIsHovered(true)
    }

    const handleMouseLeave = () => {
        setIsHovered(false)
    }

    return (
        <div data-testid="shadowElement" className={`row clickable ${!isHovered? 'm-2':'m-1 shadow'}`} data-bs-toggle="modal" data-bs-target={"#fullViewModal" + index}  onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <div className={`col-12 ${darkMode ? 'bg-light-dark' : 'bg-white'} rounded`}>
                <div className="row">
                    <div className="col-6">
                        <h4 data-testid="shortTitle" className={`${darkMode ? 'text-light' : 'text-dark'} ${jobOffer.jobOfferState === 'SUBMITTED' ? '' : 'fw-light'} pt-1`}>{jobOffer.title}</h4>
                        <p data-testid="shortDepartment" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light mb-3`}>{t(jobOffer.department)}</p>
                        <p data-testid="shortCandidate" className={`${darkMode ? 'text-light' : 'text-dark'} fw-light float-sm-end`}>{t('nbOfCandidates')} <span><strong>{jobOffer.nbOfCandidates}</strong></span></p>
                    </div>
                    <div className="col-6 my-auto d-flex justify-content-around">
                        <div className="d-none d-lg-block my-auto col-6 text-center">
                            <State state={jobOffer.jobOfferState}/>
                        </div>
                        <button className="btn btn-outline-ose my-auto" data-bs-toggle="modal" data-bs-target={"#fullViewModal" + index}>{t('internshipDetails')}</button>
                        <div id={"fullViewModal" + index} className="modal modal-lg">
                            <FullJobOffer jobOffer={jobOffer} updateJobOfferList={updateJobOfferList} updateJobOfferListAfterApprovalOrRefusal={updateJobOfferListAfterApprovalOrRefusal}/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ShortJobOffer
