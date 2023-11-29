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
		if (axiosInstance.defaults.params['season'] === undefined) return;
		if (!user?.id) return;
		await axiosInstance.get(`/student/jobOffers/open`)
			.then((response) => {
				setJobOffers(response.data)
			}).catch((error) => {
				if (error.response?.status === 401) return;
				toast.error(t('fetchError') + t(error.response?.data.message))
			});
	}
	const fetchAppointments = async () => {
		if (axiosInstance.defaults.params['season'] === undefined) return;
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
		if (axiosInstance.defaults.params['season'] === undefined) return;
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
		if (axiosInstance.defaults.params['season'] === undefined) return;
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
		if (axiosInstance.defaults.params['season'] === undefined) return;
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
		if (axiosInstance.defaults.params['season'] === undefined) return;
		return jobOffers.map((jobOffer) => {
			myApplications.map((application) => {
				if (application.id === jobOffer.id)
					jobOffer.hasApplied = true;
			});
		});
	}
	function filterViewedFromJobOffers() {
		return jobOffers.map((jobOffer) => {
			viewedJobOfferList.map((viewedJobOffer) => {
				if (viewedJobOffer === jobOffer.id)
					jobOffer.isViewed = true;
			});
		});
	}

	async function handleViewJobOffer(jobOffer) {
		if (axiosInstance.defaults.params['season'] === undefined) return;
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

	const refreshCvState = () => {
		if (!user?.id) return;
		if (!user.cvFile || !user.cvFile.id) return;
		if (axiosInstance.defaults.params['season'] === undefined) return;
		axiosInstance
			.get(`/student/cv/${user.id}`)
			.then((response) => {
				setCv(response.data)
			})
			.catch((error) => {
			})
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
		filterViewedFromJobOffers();
	}, [viewedJobOfferList, jobOffers]);

	useEffect(() => {
		getNotificationsCounts();
	}, [viewedJobOfferList, jobOffers, myApplications, contracts, appointments, user]);

	useEffect(() => {
		refreshCvState()
	}, [user])

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
		refreshCvState();
		getNotificationsCounts();
	}

	const setCv = (cv) => {
		user.cvFile = cv
		setUser(user)
	}

	function getJobOfferNotifCount() {
		return jobOffers.length - viewedJobOfferList.length;
	}

	function getNotificationsCounts() {

		updateNotifications('home', { green: 0, gray: 0, red: 0 });

		let redNotificationsStages = getJobOfferNotifCount();
		updateNotifications('stages',
			{green: 0, gray: 0, red: redNotificationsStages});

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
		let grayNotificationsCv = 0;

		if (user.cvFile === undefined) {
			redNotificationsCv = -1;
		} else if (user.cvFile.cvState === 'SUBMITTED') {
			grayNotificationsCv = -1;
		} else if (user.cvFile.cvState === 'REFUSED') {
			redNotificationsCv = -1;
		}

		updateNotifications('cv', { green: 0, gray: grayNotificationsCv, red: redNotificationsCv });


		let redNotificationsContract = contracts.filter((contract) => (
			contract.studentSignature === null)).length;

		updateNotifications('contract', { green: 0, gray: 0, red: redNotificationsContract});
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
				{tab === 'home' && <Home cv={user.cvFile} setTab={setTab} setIdElement={setIdElement} jobOffers={jobOffers} handleViewJobOffer={handleViewJobOffer} contracts={contracts} applications={myApplications}/>}
				{tab === 'stages' && <JobOfferList user={user} jobOffers={jobOffers} setJobOffers={setJobOffers} selectedById={idElement} handleViewJobOffer={handleViewJobOffer}/>}
				{tab === 'my_applications' && <MyApplications user={user} myApplications={myApplications} setMyApplications={setMyApplications} fetchMyApplications={fetchMyApplications}/>}
				{tab === 'cv' && <Cv user={user} setCv={setCv} getNotificationsCount={getNotificationsCounts}/>}
				{tab === 'contract' && <ContractList contracts={contracts} user={user} reloadContracts={fetchContracts}/>}
			</div>
		</div>
	)
}

export default StudentPage