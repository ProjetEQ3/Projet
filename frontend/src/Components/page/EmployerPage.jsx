import {useTranslation} from "react-i18next";
import JobOfferList from "../employer/JobOfferList";
import {useState} from "react";
import InterviewedStudentList from "../employer/InterviewedStudentList";

const EmployerPage = ({user}) => {
	const [t] = useTranslation();
	const [tab, setTab] = useState('stages');
	const tabs = [
		{ id: 'stages', label: 'jobOffers' },
		{ id: 'entrevue', label: 'interviewedStudents' }
	];

	return (
		<div className="bg-light">
				<div className="row text-center m-0 p-0">
					<div className="col-12 my-3">
						<h2 className="text-dark fw-light">{t('hello') + user.firstName + " " + user.lastName} !</h2>
					</div>
				</div>
				<div className="container-fluid col-12 px-lg-5 px-2 py-2">
					<div className="text-center">
						<div className="tabs btn-group my-2 mx-auto col-10">
							{tabs.map(tabItem => (
								<button
									key={tabItem.id}
									className={`col-md-3 btn btn-outline-ose ${tab === tabItem.id ? 'active' : ''}`}
									onClick={() => setTab(tabItem.id)}
								>
									{t(tabItem.label)}
								</button>
							))}
						</div>
					</div>
					{tab === 'stages' && <JobOfferList user={user}/>}
					{tab === 'entrevue' && <InterviewedStudentList user={user}/>}
			</div>
		</div>
	)
}

export default EmployerPage;