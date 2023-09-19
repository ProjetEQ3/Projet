import {useEffect, useState} from "react"
import CvFile from "../../model/CvFile"
import Cv from "../student/Cv"
import {useNavigate} from "react-router-dom"

const StudentPage = ({user, setUser}) => {
  const [tab, setTab] = useState('home');

  const navigate = useNavigate();

  useEffect(() => {
	  if(!user?.isLoggedIn){
		  navigate('/');
	}}, [user, navigate]);

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
				{tab === 'stages' && <h3>Stages</h3>}
				{tab === 'cv' && <Cv user={user} setCv={setCv} />}
			</div>
		</div>
	)
}

export default StudentPage
