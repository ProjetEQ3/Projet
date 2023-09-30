import {useEffect, useState} from "react"
import Cv from "../student/Cv"
import {useNavigate} from "react-router-dom"
import JobOfferList from "../jobOffer/JobOfferList";
import {axiosInstance} from "../../App";
import JobOffer from "../../model/JobOffer";
import {toast} from "react-toastify";

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
					  jobOffers.push(newJobOffer);
				  });
				  // setJobOffers(response.data);
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

	const applyForJobOffer = (jobOffer) => {
		axiosInstance.post(`/student/jobOffers/apply/${user.id}/${jobOffer.id}`)
			.then((response) => {
				toast.success("Candidature envoyée")
			}).catch((error) => {
				toast.error("Erreur lors de l'envoi de la candidature. " + error.response.data.message)
			})
	}

	return (
		<div className="container">
			<div>
				<div className="tabs btn-group my-2 mx-auto col-12">
					<button
						className={`btn btn-outline-ose ${tab === 'home' ? 'active' : ''}`}
						onClick={() => setTab('home')}
					>
						Accueil
					</button>
					<button
						className={`btn btn-outline-ose ${tab === 'stages' ? 'active' : ''}`}
						onClick={() => setTab('stages')}
					>
						Stages
					</button>
					<button
						className={`btn btn-outline-ose ${tab === 'cv' ? 'active' : ''}`}
						onClick={() => setTab('cv')}
					>
						CV
					</button>
				</div>
				{tab === 'home' && <h3>Home</h3>}
				{tab === 'stages' && <JobOfferList user={user} jobOffers={jobOffers} setJobOffers={setJobOffers} applyForJobOffer={applyForJobOffer}/>}
				{tab === 'cv' && <Cv user={user} setCv={setCv}/>}
			</div>
		</div>
	)
}

export default StudentPage
