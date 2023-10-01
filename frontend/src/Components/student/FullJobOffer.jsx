import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import {useEffect} from "react"

const FullJobOffer = ({jobOffer, user, updatedOffer}) => {

	useEffect(() => {
		console.log("user", user)
		console.log("jobOffer", jobOffer)
	}, [])

	const applyForJobOffer = (jobOfferID, studentId) => {
		axiosInstance
			.post(`/student/applyJobOffer/${studentId}/${jobOfferID}`)
			.then((ressponse) => {
				updatedOffer(ressponse.data)
				toast.success("Vous avez postulé avec succès")}
			)
			.catch((err) => {
				console.log("err: ", err)
				toast.error(err.response.data.message)}
			)
	}

	return (
		<div className="row my-2">
			<div className="col-12 bg-white rounded">
				<div className="row justify-content-between">
					<div className="col-9">
						<h2 className="text-dark fw-light pt-1">{jobOffer.title}</h2>
					</div>
					<div className="col-3 my-auto text-center">
						<div className="col-3 my-auto text-center">
							<button className={"btn btn-primary"} onClick={!user.cvFile.isApproved ?
								() => toast.error("CV pas encore approuvé") :
								() => applyForJobOffer(jobOffer.id, user.id)}>Apply</button>

							{/*{user.cvFile.isApproved ? (*/}
							{/*	<button className={"btn btn-primary"} onClick={() => applyForJobOffer(jobOffer.id, user.id)}>Apply</button>*/}
							{/*) : (*/}
							{/*	<p>CV pas encore approuvé</p>*/}
							{/*)}*/}
						</div>
					</div>
				</div>
				<div className="row">
					<div className="col-12">
						<h5 className="text-dark fw-light mb-3">{jobOffer.department}</h5>
						<h5 className="text-dark fw-light mb-3">{jobOffer.location}</h5>
						<h6 className="text-dark fw-light mb-3">Date de début: {jobOffer.startDate.split("T")[0]}</h6>
						<h6 className="text-dark fw-light mb-3">Durée: {jobOffer.duration} semaines</h6>
						<h6 className="text-dark fw-light mb-3">{jobOffer.salary}$/h</h6>
						<h6 className="text-dark fw-light mb-3">{jobOffer.hoursPerWeek}h/semaine</h6>
						<p className="text-dark fw-light mb-3">{jobOffer.description}</p>
					</div>
				</div>
			</div>
		</div>
	);
}

export default FullJobOffer;