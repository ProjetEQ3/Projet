import {axiosInstance} from "../../App"
import {toast} from "react-toastify"
import {useEffect} from "react"
import {useTranslation} from "react-i18next";

const FullJobOffer = ({user, jobOffer, updatedOffer}) => {
	const {t} = useTranslation()

	useEffect(() => {
		console.log("user", user)
		console.log("jobOffer", jobOffer)
	}, [])

	const applyForJobOffer = (jobOfferID, studentId) => {
		axiosInstance
			.post(`/student/applyJobOffer/${studentId}/${jobOfferID}`)
			.then((response) => {
				updatedOffer(response.data)
				toast.success(t('appliedJobOffer'))}
			)
			.catch((err) => {
				console.log("err: ", err)
				toast.error(t('pushingError') + t(err.response.data.error))}
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
							<button className={"btn btn-outline-ose"} onClick={
								() => applyForJobOffer(jobOffer.id, user.id)}>{t('apply')}</button>
						</div>
					</div>
				</div>
				<div className="row">
					<div className="col-12">
						<h5 className="text-dark fw-light mb-3">{jobOffer.department}</h5>
						<h5 className="text-dark fw-light mb-3">{jobOffer.location}</h5>
						<h6 className="text-dark fw-light mb-3">{t('startDate') + jobOffer.startDate}</h6>
						<h6 className="text-dark fw-light mb-3">{t('duration') + jobOffer.duration + t('week')}</h6>
						<h6 className="text-dark fw-light mb-3">{jobOffer.salary}$/h</h6>
						<h6 className="text-dark fw-light mb-3">{jobOffer.hoursPerWeek}h/{t('week')}</h6>
						<p className="text-dark fw-light mb-3">{jobOffer.description}</p>
					</div>
				</div>
			</div>
		</div>
	);
}

export default FullJobOffer;