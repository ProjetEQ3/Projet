import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faTrash } from '@fortawesome/free-solid-svg-icons';

const ShortJobOffer = ({ jobOffer }) => {
    return (
        <div className="row m-2">
            <div className="col-12 bg-white rounded">
                <div className="row">
                    <div className="col-10">
                        <h4 className="text-dark fw-light">{jobOffer.title}</h4>
                        <p className="text-dark fw-light">{jobOffer.department}</p>
                    </div>
                    <div className="col-2 my-auto text-end d-flex justify-content-between">
                        <div className={'border rounded px-2 ${jobOffer.state}'}>
                            {jobOffer.state}
                        </div>
                        <FontAwesomeIcon icon={faTrash} className="text-ose me-2 fa-lg"/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ShortJobOffer;