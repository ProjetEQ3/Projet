import React from "react";
import ShortCv from "./ShortCv";
import FilterObjectList from "../util/FilterObjectList";

const Cvs = ({cvs, updateCvList}) => {

	const renderFilteredCvs = (filteredCvs) => {
		return (
			<div className="col-12">
				{filteredCvs.map((cv, index) => (
					<div key={index} onClick={() => (cv)}>
						<ShortCv cv={cv} updateCvList={updateCvList} index={index}/>
					</div>
				))}
			</div>
		)
	}

	return (
		<div className="row">
			<div className="col-12">
				<h3 className="text-dark fw-light my-5">Les CVs des étudiants :</h3>
				<div className="row justify-content-around">
					<FilterObjectList
						items={cvs}
						attributes={['fileName:Nom de fichier','cvState.select:Status']}
						renderItem={renderFilteredCvs}
						selectOptions={{cvState: ['SUBMITTED', 'ACCEPTED', 'REFUSED']}}
					/>
				</div>
			</div>
		</div>
	)

}

export default Cvs

/*	return (
		<div className="row">
			<div className="col-12">
				<h3 className="text-dark fw-light my-5">Les CVs des étudiants :</h3>
				<div className="row justify-content-around">
					<div className="col-12">
						{
							cvs.map((cv, index) => (
								<div key={index} onClick={() => (cv)}>
									<ShortCv cv={cv} updateCvList={updateCvList} index={index}/>
								</div>
							))
						}
					</div>
				</div>
			</div>
		</div>
	);*/