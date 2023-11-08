import {useEffect, useState} from "react"
import Cv from "../student/Cv"
import {useNavigate} from "react-router-dom"
import JobOfferList from "../student/JobOfferList";
import {axiosInstance} from "../../App";
import JobOffer from "../../model/JobOffer";
import {toast} from "react-toastify";
import MyApplications from "../student/MyApplications";
import {useTranslation} from "react-i18next";
import {useSession} from "../util/SessionContext";
import ContractList from "../user/ContractList";
import Contract from "../../model/Contract";
import Home from "../student/Home";
import NotificationBadge from '../notification/NotificationBadge';
import Application from "../../model/Application";
import Appointment from "../../model/Appointment";
import cv from "../student/Cv";

const StudentPage = ({user, setUser}) => {
  const {selectedSessionIndex} = useSession();
  const {t} = useTranslation();
  const navigate = useNavigate();
  const [tab, setTab] = useState('home');
  const tabs = [
	  { id: 'home', label: 'home' },
	  { id: 'stages', label: 'jobOffers' },
	  { id: 'my_applications', label: 'myApplications' },
	  { id: 'cv', label: 'CV' },
	  { id: 'contract', label: 'contracts' }
  ];
  const [jobOffers, setJobOffers] = useState([new JobOffer()]);
  const [myApplications, setMyApplications] = useState([new Application()]);
  const [contracts, setContracts] = useState([new Contract()]);
  const [appointments, setAppointments] = useState([new Appointment()]);
  const [viewedJobOfferList, setViewedJobOfferList] = useState([]);

  const [idElement, setIdElement] = useState(null);

  const [notifications, setNotifications] = useState({
		home: { green: 0, gray: 0, red: 0 },
		stages: { green: 0, gray: 0, red: 0 },
		my_applications: { green: 0, gray: 0, red: 0 },
		cv: { green: 0, gray: 0, red: 0 },
		contract: { green: 0, gray: 0, red: 0 },
  });

	async function fetchStudentJobOffers() {
		if (!user?.id) return;
		await axiosInstance.get(`/student/jobOffers/open/${user.department}`)
			.then((response) => {
				setJobOffers(response.data)
			}).catch((error) => {
				if (error.response?.status === 401) return;
				toast.error(t('fetchError') + t(error.response?.data.message))
			});
	}
	const fetchAppointments = async () => {
		myApplications.map((application) => {
			if (application.id === 0) return;
			axiosInstance.get(`/student/appointmentsByJobOfferIdAndStudentId/${application.id}/${user.id}`)
				.then((response) => {
					const newAppointments = response.data.map((appointment) => {
						const newAppointment = new Appointment();
						newAppointment.init(appointment);
						return newAppointment;
					});
					newAppointments.sort((a, b) => {
						return new Date(a.appointmentDate) - new Date(b.appointmentDate);
					}, []);
					application.appointments = newAppointments;
					setAppointments(newAppointments);
				})
				.catch((error) => {
					console.error("Error fetching appointments:", error);
				});
		});
	};

	async function fetchContracts() {
		if (!user?.id) return;
		await axiosInstance.get(`student/contracts/${user.id}`)
			.then((response) => {
				setContracts(response.data);
			})
			.catch((error) => {
				toast.error(t(error.response?.data?.message))
			});
	}

	async function fetchMyApplications() {
		if (!user?.id) return;
		await axiosInstance.get(`/student/appliedJobOffer/${user.id}`)
			.then((response) => {
				setMyApplications(response.data);
			})
			.catch((error) => {
				toast.error(t('fetchError') + t(error.response?.data.message));
			});
	}

	async function fetchViewedJobOfferList() {
		if (!user?.id) return;
		await axiosInstance.get(`/student/viewedJobOffers/${user.id}`)
			.then((response) => {
				setViewedJobOfferList(response.data)
			})
			.catch((error) => {
				toast.error(t('fetchError') + t(error.response?.data.message));
			});
	}

	function filterApplicationsFromJobOffers() {
		return jobOffers.map((jobOffer) => {
			myApplications.map((application) => {
				if (application.id === jobOffer.id)
					jobOffer.hasApplied = true;
			});
		});
	}

	async function handleViewJobOffer(jobOffer) {
		if (!user?.id) return;
		await axiosInstance.put(`/student/jobOffer/view/${user.id}/${jobOffer.id}`)
			.then((response) => {
				if (!viewedJobOfferList.includes(jobOffer.id)){
					setViewedJobOfferList((viewedJobOfferList) => [...viewedJobOfferList, jobOffer.id]);
				}
			})
			.catch((error) => {
				toast.error(t('fetchError') + t(error.response?.data.message));
			});
	}

	useEffect(() => {
		if (!user?.isLoggedIn) navigate('/');
		fetchStudentJobOffers()
		fetchViewedJobOfferList()
		fetchMyApplications()
	}, [user]);

	useEffect(() => {
		fetchAppointments();
	}, [myApplications]);

	useEffect(() => {
		handleSessionChange();
	}, [selectedSessionIndex]);

	useEffect(() => {
		fetchMyApplications();
	}, [jobOffers]);

	useEffect(() => {
		filterApplicationsFromJobOffers();
	}, [myApplications, jobOffers]);

	useEffect(() => {
		getNotificationsCounts();
	}, [viewedJobOfferList, jobOffers, myApplications, contracts, appointments, user]);

	const handleSessionChange = () => {
	  handleRefresh()
	}

	const handleRefresh = () => {
		setJobOffers([]);
		setMyApplications([]);
		setContracts([]);
		setAppointments([]);
		fetchStudentJobOffers();
		fetchMyApplications();
		fetchViewedJobOfferList();
		fetchContracts();
		getNotificationsCounts();
	}

	const setCv = (cv) => {
		user.cvFile = cv
		setUser(user)
	}

	function getJobOfferNotifCount() {
		fetchViewedJobOfferList();
		return jobOffers.length - viewedJobOfferList.length;
	}

	function getNotificationsCounts() {
		updateNotifications('home', { green: 0, gray: 0, red: 0 });

		let greenNotificationsStages = 0;
		let grayNotificationsStages = 0;
		let redNotificationsStages = getJobOfferNotifCount();
		updateNotifications('stages',
			{green: greenNotificationsStages, gray: grayNotificationsStages, red: redNotificationsStages});

		let greenNotificationsApplication = 0;
		let grayNotificationsApplication = 0;
		let redNotificationsApplication = 0;
		myApplications.forEach((application) => {
			if (application.appointments === undefined) return;
			if (application.appointments.length === 1) {
				greenNotificationsApplication++;
			} else if (application.appointments.length === 0) {
				grayNotificationsApplication++;
			}
			else {
				redNotificationsApplication++;
			}
		});
		updateNotifications('my_applications', { green: greenNotificationsApplication, gray: grayNotificationsApplication, red: redNotificationsApplication });

		let redNotificationsCv = 0;
		let greenNotificationsCv = 0;
		let grayNotificationsCv = 0;

		if (user.cvFile === undefined) {
			redNotificationsCv = -1;
		} else if (user.cvFile.cvState === 'SUBMITTED') {
			grayNotificationsCv = -1;
		} else if (user.cvFile.cvState === 'REFUSED') {
			redNotificationsCv = -1;
		}
		updateNotifications('cv', { green: greenNotificationsCv, gray: grayNotificationsCv, red: redNotificationsCv });
		updateNotifications('contract', { green: 0, gray: 0, red: 0 });
	}

	const updateNotifications = (tabId, { green, gray, red }) => {
		setNotifications(prevNotifications => ({
			...prevNotifications,
			[tabId]: { green, gray, red }
		}));
	};

	return (
		<div className="container-fluid px-lg-5 px-2 py-2">
			<div>
				<div className="tabs btn-group my-2 mx-auto col-12">
					{tabs.map(tabItem => (
						<button
							key={tabItem.id}
							className={`col-md-3 btn btn-outline-ose mx-2 ${tab === tabItem.id ? 'active' : ''}`}
							onClick={() => {
								setTab(tabItem.id)
								handleRefresh();
								
							}}
							style={{ position: 'relative' }}
						>
							{t(tabItem.label)}
							<NotificationBadge notifications={notifications[tabItem.id]} tab={tabItem} setTab={setTab} titleInfos={tabItem.label}/>
						</button>
					))}
				</div>
				{tab === 'home' && <Home cv={user.cvFile} setTab={setTab} setIdElement={setIdElement} jobOffers={jobOffers} applications={myApplications}/>}
				{tab === 'stages' && <JobOfferList user={user} jobOffers={jobOffers} setJobOffers={setJobOffers} selectedById={idElement} handleViewJobOffer={handleViewJobOffer}/>}
				{tab === 'my_applications' && <MyApplications user={user} myApplications={myApplications} setMyApplications={setMyApplications} fetchMyApplications={fetchMyApplications}/>}
				{tab === 'cv' && <Cv user={user} setCv={setCv} getNotificationsCount={getNotificationsCounts}/>}
				{tab === 'contract' && <ContractList contracts={contracts} user={user} />}
			</div>
		</div>
	)
}

export default StudentPage