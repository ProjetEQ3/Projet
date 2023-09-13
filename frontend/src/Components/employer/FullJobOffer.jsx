import {useState} from "react";

const FullJobOffer = ({ jobId }) => {

    return (
        <div className="row my-2">
            <div className="col-12 bg-white rounded">
                <div className="row justify-content-between">
                    <div className="col-9">
                        <h2 className="text-dark fw-light pt-1">{jobOffer.title}</h2>
                    </div>
                    <div className="col-3 my-auto text-center">
                            <div className={`border rounded px-2 
                            ${jobOffer.state === 'Open' ? 'border-success text-success':
                                jobOffer.state === 'Pending' ? 'border-warning text-warning':
                                    jobOffer.state === 'Expired' ? 'border-danger text-danger' :
                                        jobOffer.state === 'Taken' ? 'border-primary text-primary': 'border-secondary text-secondary'} `}>
                                {jobOffer.state}
                            </div>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <h5 className="text-dark fw-light mb-3">{jobOffer.department}</h5>
                        <h5 className="text-dark fw-light mb-3">{jobOffer.location}</h5>
                        <h6 className="text-dark fw-light mb-3">Date de début: {jobOffer.startDate}</h6>
                        <h6 className="text-dark fw-light mb-3">Durée: {jobOffer.duration}</h6>
                        <h6 className="text-dark fw-light mb-3">Date d'expiration: {jobOffer.expireDate}</h6>
                        <h6 className="text-dark fw-light mb-3">{jobOffer.salary}$/h</h6>
                        <h6 className="text-dark fw-light mb-3">{jobOffer.hourPerWeek}h/semaine</h6>
                        <p className="text-dark fw-light mb-3">{jobOffer.description}</p>

                    </div>
                </div>
            </div>
        </div>
    );
}

export default FullJobOffer;