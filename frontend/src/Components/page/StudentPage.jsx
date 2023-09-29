import {useEffect, useState} from "react"
import Cv from "../student/Cv"
import {useNavigate} from "react-router-dom"
import JobOfferList from "../jobOffer/JobOfferList";
import {axiosInstance} from "../../App";
import JobOffer from "../../model/JobOffer";

const StudentPage = ({user, setUser}) => {
  const [tab, setTab] = useState('home');
  const [jobOffers, setJobOffers] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
	  if(!user?.isLoggedIn){
		  navigate('/');
	  }

	  async function fetchStudentJobOffers() {
		  await axiosInstance.get(`/student/jobOffers/open/${user.department}`)
			  .then((response) => {
				  response.data.forEach((jobOffer) => {
					  const newJobOffer = new JobOffer();
					  newJobOffer.init(jobOffer)
					  setJobOffers(jobOffers => [...jobOffers, newJobOffer]);
				  });
			  }).catch((error) => {
				  console.log("Fetch error: "+ error);
			  });
	  }

	  fetchStudentJobOffers();
  }, [user, navigate]);

	const setCv = (cv) => {
		user.cvFile = cv
		setUser(user)
	}

	return (
		<div className="container">
			<div>
				<div className="tabs btn-group my-2 mx-auto col-12">
					<button className={`btn btn-outline-ose ${tab === 'home' ? 'active' : ''}`}
						onClick={() => setTab('home')}>Accueil</button>
					<button className={`btn btn-outline-ose ${tab === 'stages' ? 'active' : ''}`}
						onClick={() => setTab('stages')}>Stages</button>
					<button className={`btn btn-outline-ose ${tab === 'cv' ? 'active' : ''}`}
						onClick={() => setTab('cv')}>CV</button>
				</div>
				{tab === 'home' && <h3>Home</h3>}
				{tab === 'stages' && <JobOfferList user={user} jobOffers={jobOffers} />  }
				{tab === 'cv' && <Cv user={user} setCv={setCv} />}
			</div>
		</div>
	)
}

export default StudentPage
