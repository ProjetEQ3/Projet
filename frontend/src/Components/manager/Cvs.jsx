import React from "react";
import ShortCv from "./ShortCv";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";

const Cvs = ({cvs}) => {
	const accepterCv = (cv) => {
		axiosInstance
			.post(`/manager/cv/accept/${cv.id}`)
			.then((response) => {
				toast.success("CV accepté")
			})
			.catch((error) => {
				toast.error("Erreur lors de l'acceptation du CV" + error.message)
			})
	}

	const refuserCv = (cv) => {
		axiosInstance
			.post(`/manager/cv/refuse/${cv.id}`)
			.then((response) => {
				toast.success("CV refusé")
			})
			.catch((error) => {
				toast.error("Erreur lors du refus du CV" + error.message)
			})
	}

	return (
		<div className="row">
			<div className="col-12">
				<h3 className="text-dark fw-light my-5">Les CVs des étudiants :</h3>
				<div className="row justify-content-around">
					<div className="col-12">
						{
							cvs.map((cv, index) => (
								<div key={index} onClick={() => (cv)}>
									<ShortCv cv={cv}/>
								</div>
							))
						}
					</div>
				</div>
			</div>
		</div>
	);
}
export default Cvs;