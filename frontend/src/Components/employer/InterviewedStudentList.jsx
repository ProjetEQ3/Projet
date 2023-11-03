import React, {useEffect, useState} from 'react'
import ShortInterviewedStudentInfo from "./ShortInterviewedStudentInfo"
import {toast} from "react-toastify"
import {axiosInstance} from "../../App"
import {useTranslation} from "react-i18next"

const InterviewedStudentList = ({user}) => {
	const {t} = useTranslation()
	const [studentList, setStudentList] = useState([])

	async function fetchStudentList(userClicked){
		await axiosInstance
			.get('employer/waitingStudents', {params: {employerId: user.id}})
			.then(res => {
				if(res.data.length === 0){
					if (userClicked)
					{
						setStudentList([])
						return
					}
					toast.info(t('noStudentsConvoked'))
					setStudentList([])
					return
				}
				fetchStudentsJobTitles(res.data)
			})
			.catch(err => {
				toast.error(err.message)
			})
	}

	async function fetchStudentsJobTitles(fetchedStudentList){
		for(let student of fetchedStudentList){
			axiosInstance
				.get('employer/offerByApplication', {params: {applicationId: student.jobApplications[0]}})
				.then(res => {student.jobTitle = res.data.title})
				.catch(err => {toast.error(err.message)})
		}
		setStudentList(fetchedStudentList)
	}

	useEffect(() => {
		fetchStudentList().then(r => console.log('Student list fetched'))
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
