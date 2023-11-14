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
import Home from "../employer/Home";
import {useDarkMode} from "../../context/DarkModeContext";

const EmployerPage = ({user}) => {
	const navigate = useNavigate();
	const [t] = useTranslation();
	const [tab, setTab] = useState('home');
	const tabs = [
		{ id: 'home', label: 'home' },
		{ id: 'stages', label: 'jobOffers' },
		{ id: 'interviewed', label: 'convokedStudents' },
		{ id: 'contract', label: 'contracts' }
	];
	const [contracts, setContracts] = useState([new Contract()]);
	const [studentList, setStudentList] = useState([])
	const [nbPostulations, setNbPostulations] = useState(0);
	const [idElement, setIdElement] = useState(null);
	const [offersWithApplications, setOffersWithApplications] = useState([])
	const [notifications, setNotifications] = useState({
		home: { red: 0, green: 0, gray: 0 },
		stages: { red: 0, green: 0, gray: 0 },
		interviewed: { red: 0, green: 0, gray: 0 },
		contract: { red: 0, green: 0, gray: 0 },
	});
	const [offers, setOffers] = useState([])
	const [refusedCount, setRefusedCount] = useState(0)
	const [refusedOffers, setRefusedOffers] = useState([]);

	const { darkMode } = useDarkMode();

	const getOffers = () => {
		axiosInstance
			.get('/employer/offer/all', {params: {employerId: user.id}})
			.then((response) => {
				setOffers(response.data)
				const refusedOffers = response.data.filter(offer => offer.jobOfferState === "REFUSED");
				setRefusedOffers(refusedOffers);
				setRefusedCount(refusedOffers.length)

			})
			.catch((error) => {
				if(error.response?.status === 401){
					return
				}
				toast.error(t('fetchError') + t(error.response?.data.message))
			})
	}

	const getOffersWithSubmittedApplications = () => {
		if (!user?.id) return;
		axiosInstance
			.get(`/employer/offer/submittedApplications/${user.id}`)
			.then((response) => {
				setOffersWithApplications(response.data);
			})
			.catch((error) => {
				toast.error(t('getOffersWithSubmittedApplicationsError') + t(error.response?.data.message))
			});
		fetchStudentList(true);
	}

	async function getNbPostulations() {
		if (!user?.id) return;
		await axiosInstance.get(`employer/nbApplications/${user.id}`)
			.then((response) => {
				setNbPostulations(response.data);
			})
			.catch((error) => {
				toast.error(t(error.response?.data?.message))
			});
		fetchStudentList(true);
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

	async function fetchStudentList(userClicked){
		if (!user?.id) return;
		await axiosInstance
			.get('employer/waitingStudents', {
				params: {employerId: user.id}
			})
			.then(response => {
				if(response.data.length === 0){
					setStudentList([])
					if (userClicked) return
					toast.info(t('noStudentsConvoked'))
					return
				}
				fetchStudentsJobTitles(response.data)
			})
			.catch(error => {
				toast.error(error.message)
			})
		getContracts()
	}

	async function fetchStudentsJobTitles(fetchedStudentList){
		for(let student of fetchedStudentList){
			axiosInstance
				.get('employer/offerByApplication', {
					params: {
						applicationId: student.jobApplications[0]
					}
				})
				.then(response => {
					student.jobTitle = response.data.title
				})
				.catch(error => {
					toast.error(error.message)
				})
		}
		setStudentList(fetchedStudentList)
	}

	useEffect(() => {
		setNotifications(notifications => ({
			...notifications,
			stages: { ...notifications.stages, red: nbPostulations + refusedCount },
			interviewed: { ...notifications.interviewed, red: studentList.length },
			contract: { ...notifications.contract, red: contracts.filter(contract => contract.employerSignature === null).length, green: contracts.filter(contract => contract.complete).length },
		}));
	}, [nbPostulations, studentList, refusedCount, contracts]);



	useEffect(() => {
		getOffers();
		getNbPostulations();
		getOffersWithSubmittedApplications();
	}, [tab]);

	useEffect(() => {
		if (!user?.isLoggedIn) navigate('/');
		getOffers();
		getContracts();
		fetchStudentList(true);
		getNbPostulations();
	}, [user])

	return (
		<div className={`${darkMode ? 'bg-dark' : 'bg-light'}`}>
				<div className="row text-center m-0 p-0">
					<div className="col-12 my-3">
						<h2 className={`${darkMode ? 'text-light' : 'text-dark'} fw-light`}>{t('hello') + user.firstName + " " + user.lastName} !</h2>
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
					{tab === 'home' && <Home setTab={setTab} setIdElement={setIdElement} fetchStudentList={fetchStudentList}
											 jobOffers={offersWithApplications} studentList={studentList} contracts={contracts} refusedOffers={refusedOffers} />}
					{tab === 'stages' && <JobOfferList user={user} getNbPostulations={getNbPostulations} offersWithApplications={offersWithApplications}
													   getOffersWithSubmittedApplications={getOffersWithSubmittedApplications}
													   selectedById={idElement} setSelectedById={setIdElement}
													   setRefusedCount={setRefusedCount} getOffers={getOffers} offers={offers} setOffers={setOffers}/>}
					{tab === 'interviewed' && <InterviewedStudentList user={user} fetchStudentList={fetchStudentList} studentList={studentList}/>}
					{tab === 'contract' && <ContractList user={user} contracts={contracts} reloadContracts={getContracts}/>}
			</div>
		</div>
	)
}

export default EmployerPage;