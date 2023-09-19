import React from "react";
import ShortCv from "./ShortCv";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";

const Cvs = ({cvs}) => {
	const updateCv = (cv, state, reason) => {
		axiosInstance
			.post(`/manager/cv/update/${cv.id}?newCvState=${state}&reason=${reason}`)
			.then((response) => {
				toast.success("CV est mis à jour avec succès, state: " + state)
			})
			.catch((error) => {
				toast.error("Erreur lors de la mis à jour du CV: " + error.message)
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