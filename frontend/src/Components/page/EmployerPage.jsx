import {useTranslation} from "react-i18next";
import JobOfferList from "../employer/JobOfferList";
import {useEffect, useState} from "react";
import InterviewedStudentList from "../employer/InterviewedStudentList";
import ContractList from "../user/ContractList";
import Contract from "../../model/Contract";
import {axiosInstance} from "../../App";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import NotificationBadge from "../notification/NotificationBadge";

const EmployerPage = ({user}) => {
	const navigate = useNavigate();
	const [t] = useTranslation();
	const [tab, setTab] = useState('stages');
	const tabs = [
		{ id: 'stages', label: 'jobOffers' },
		{ id: 'entrevue', label: 'convokedStudents' },
		{ id: 'contract', label: 'contracts' }
	];
	const [contracts, setContracts] = useState([new Contract()]);
	const [nbPostulations, setNbPostulations] = useState(0);
	const [notifications, setNotifications] = useState({
		stages: { red: 0, green: 0, gray: 0 },
		entrevue: { red: 0, green: 0, gray: 0 },
		contract: { red: 0, green: 0, gray: 0 },
	});

	useEffect(() => {
		setNotifications(notifications => ({
			...notifications,
			stages: { ...notifications.stages, red: nbPostulations },
		}));
	}, [nbPostulations]);

	useEffect(() => {
		if (tab === 'stages') {
			getNbPostulations();
		}
	}, [tab]);

	async function getNbPostulations() {
		if (!user?.id) return;
		await axiosInstance.get(`employer/nbApplications/${user.id}`)
			.then((response) => {
				setNbPostulations(response.data);
			})
			.catch((error) => {
				toast.error(t(error.response?.data?.message))
			});
	}

	async function getContracts() {
		if (!user?.id) return;
		await axiosInstance.get(`employer/contracts/${user.id}`)
			.then((response) => {
				setContracts(response.data);
			})
			.catch((error) => {
				toast.error(t(error.response?.data?.message))
			});
	}

	useEffect(() => {
		if (!user?.isLoggedIn) navigate('/');
		getContracts();
		getNbPostulations();
	}, [user])

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
									className={`col-md-3 btn btn-outline-ose mx-2 ${tab === tabItem.id ? 'active' : ''}`}
									onClick={() => {
										setTab(tabItem.id)
										if (tabItem.id === 'contract') getContracts();
									}}
								>
									{t(tabItem.label)}
									<NotificationBadge notifications={notifications[tabItem.id]} tab={tabItem} setTab={setTab} titleInfos={`title_${tabItem.id}`} />
								</button>
							))}
						</div>
					</div>
					{tab === 'stages' && <JobOfferList user={user} getNbPostulations={getNbPostulations} />}
					{tab === 'entrevue' && <InterviewedStudentList user={user}/>}
					{tab === 'contract' && <ContractList contracts={contracts} user={user} />}
			</div>
		</div>
	)
}

export default EmployerPage;