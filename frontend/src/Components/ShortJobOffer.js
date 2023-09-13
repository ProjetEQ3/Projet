import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faTrash } from '@fortawesome/free-solid-svg-icons';

const ShortJobOffer = ({ jobOffer }) => {
    return (
        <div className="row m-2">
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-8">
                        <h4 className="text-dark fw-light">{jobOffer.title}</h4>
                        <p className="text-dark fw-light">{jobOffer.department}</p>
                    </div>
                    <div className="col-4 my-auto d-flex justify-content-between">
                        <div className="mx-auto">
                            <div className={`border rounded px-2 
                            ${jobOffer.state === 'Open' ? 'border-success text-success':
                                jobOffer.state === 'Pending' ? 'border-warning text-warning':
                                    jobOffer.state === 'Expired' ? 'border-danger text-danger' :
                                        jobOffer.state === 'Taken' ? 'border-primary text-primary': 'border-secondary text-secondary'} `}>
                                {jobOffer.state}
                            </div>
                        </div>
                        <FontAwesomeIcon icon={faTrash} className="trash-btn me-2 fa-lg"/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ShortJobOffer;