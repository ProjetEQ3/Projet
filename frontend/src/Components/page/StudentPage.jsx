import {useEffect, useState} from "react"
import Cv from "../student/Cv"
import {useNavigate} from "react-router-dom"
import JobOfferList from "../student/JobOfferList";
import {axiosInstance} from "../../App";
import JobOffer from "../../model/JobOffer";
import {toast} from "react-toastify";
import MyApplications from "../student/MyApplications";
import {useTranslation} from "react-i18next";

const StudentPage = ({user, setUser}) => {
  const {t} = useTranslation();
  const [tab, setTab] = useState('home');
  const [jobOffers, setJobOffers] = useState([]);
  const tabs = [
	  { id: 'home', label: 'home' },
	  { id: 'stages', label: 'jobOffers' },
	  { id: 'my_applications', label: 'myApplications' },
	  { id: 'cv', label: 'CV' },
  ];
  const navigate = useNavigate();

  useEffect(() => {
	  async function fetchStudentJobOffers() {
		  if (!user?.isLoggedIn) navigate('/');

		  await axiosInstance.get(`/student/jobOffers/open/${user.department}`)
			  .then((response) => {
				  response.data.forEach((jobOffer) => {
					  const newJobOffer = new JobOffer();
					  newJobOffer.init(jobOffer)
					  setJobOffers(jobOffers => [...jobOffers, newJobOffer]);
				  });
			  }).catch((error) => {
				  if (error.response.status === 401) {
					  // console.log(error)
					  return;
				  }
				  toast.error(t('fetchError') + t(error.message))
			  });
	  }

	  fetchStudentJobOffers();
  }, [user, navigate]);

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
							onClick={() => setTab(tabItem.id)}
						>
							{t(tabItem.label)}
						</button>
					))}
				</div>
				{tab === 'home' && <h3>{t('home')}</h3>}
				{tab === 'stages' && <JobOfferList user={user} jobOffers={jobOffers} setJobOffers={setJobOffers}/>}
				{tab === 'my_applications' && <MyApplications user={user}/>}
				{tab === 'cv' && <Cv user={user} setCv={setCv}/>}
			</div>
		</div>
	)
}

export default StudentPage