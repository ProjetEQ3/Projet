import React, {useEffect, useState} from 'react'
import ShortInterviewedStudentInfo from "./ShortInterviewedStudentInfo"
import {toast} from "react-toastify"
import {axiosInstance} from "../../App"
import {useTranslation} from "react-i18next"

const InterviewedStudentList = ({user, fetchStudentList, studentList}) => {
	const {t} = useTranslation()

	useEffect(() => {
		fetchStudentList().then(r => r)
	}, [user])

	return (
		<div className="container" data-testid="interviewed-student-list">
			<div className="row">
				<div className="col-12">
					{
						studentList.length === 0 ? <h3 className="text-center">{t('noStudentsConvoked')}</h3>
							: studentList.map((student, index) => (
								<div key={index}>
									<ShortInterviewedStudentInfo student={student} fetchStudentList={fetchStudentList}/>
								</div>
							))
					}
				</div>
			</div>
		</div>
	)
}

export default InterviewedStudentList
