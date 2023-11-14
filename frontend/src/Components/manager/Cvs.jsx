import React from "react";
import ShortCv from "./ShortCv";
import FilterObjectList from "../util/FilterObjectList";
import {useTranslation} from "react-i18next";
import {useDarkMode} from "../../context/DarkModeContext";

const Cvs = ({cvs, updateCvList, getAllCvs, selectedById }) => {
	const {t} = useTranslation();

	const { darkMode } = useDarkMode();

	const renderFilteredCvs = (filteredCvs) => {
		return (
			<div className="col-12">
				{
					filteredCvs.length !== 0 ?
						filteredCvs.map((cv, index) => (
							<div key={index} onClick={() => (cv)}>
								<ShortCv cv={cv} updateCvList={updateCvList} index={index} getAllCvs={getAllCvs} />
							</div>)) :
						<div className="col-12 text-center">
							<h4 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light`}>{t('noCV')}</h4>
						</div>
				}
			</div>
		)
	}

	return (
		<div className="row">
			<div className="col-12">
				<h3 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light my-5`}>{t('studentCV')}</h3>
				<div className="row justify-content-around">
					<FilterObjectList
						items={cvs}
						attributes={['fileName:' + t('fileName'),'cvState.select:Status']}
						renderItem={renderFilteredCvs}
						selectOptions={{cvState: ['SUBMITTED', 'ACCEPTED', 'REFUSED']}}
						defaultSelectSubmitted={selectedById === 'cv' ? 'cvState.select' : ''}
					/>
				</div>
			</div>
		</div>
	)

}

export default Cvs
