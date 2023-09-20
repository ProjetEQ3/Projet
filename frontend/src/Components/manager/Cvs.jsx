import React from "react";
import ShortCv from "./ShortCv";

const Cvs = ({cvs}) => {
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