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

const StudentPage = ({user, setUser}) => {
  const {selectedSessionIndex} = useSession();
  const {t} = useTranslation();
  const [tab, setTab] = useState('home');
  const [jobOffers, setJobOffers] = useState([new JobOffer()]);
  const tabs = [
	  { id: 'home', label: 'home' },
	  { id: 'stages', label: 'jobOffers' },
	  { id: 'my_applications', label: 'myApplications' },
	  { id: 'cv', label: 'CV' },
	  { id: 'contract', label: 'contracts' }
  ];
  const navigate = useNavigate();
  const [contracts, setContracts] = useState([new Contract()]);

	async function fetchStudentJobOffers() {
		if (!user?.isLoggedIn) navigate('/');
		await axiosInstance.get(`/student/jobOffers/open/${user.department}`)
			.then((response) => {
				setJobOffers(response.data)
			}).catch((error) => {
				if (error.response?.status === 401) return;

				toast.error(t('fetchError') + t(error.response?.data.message))
			});
	}

	async function getContracts() {
		await axiosInstance.get(`student/contracts/${user.id}`)
			.then((response) => {
				setContracts(response.data);
			})
			.catch((error) => {
				toast.error(error.response?.data?.message)
			});
	}

	useEffect(() => {
		fetchStudentJobOffers();
	}, []);

	useEffect(() => {
		handleSessionChange();
	}, [selectedSessionIndex]);

	const handleSessionChange = () => {
	  setJobOffers([]);
	  fetchStudentJobOffers();
	}

	const setCv = (cv) => {
		user.cvFile = cv
		setUser(user)
	}

	return (
		<div className="container-fluid px-lg-5 px-2 py-2">
			<div>
				<div className="tabs btn-group my-2 mx-auto col-12">
					{tabs.map(tabItem => (
						<button
							key={tabItem.id}
							className={`col-md-3 btn btn-outline-ose ${tab === tabItem.id ? 'active' : ''}`}
							onClick={() => {
								setTab(tabItem.id)
								if (tabItem.id === 'contract') getContracts();
							}}
						>
							{t(tabItem.label)}
						</button>
					))}
				</div>
				{tab === 'home' && <h3>{t('home')}</h3>}
				{tab === 'stages' && <JobOfferList user={user} jobOffers={jobOffers} setJobOffers={setJobOffers}/>}
				{tab === 'my_applications' && <MyApplications user={user}/>}
				{tab === 'cv' && <Cv user={user} setCv={setCv}/>}
				{tab === 'contract' && <ContractList contracts={contracts} />}
			</div>
		</div>
	)
}

export default StudentPage