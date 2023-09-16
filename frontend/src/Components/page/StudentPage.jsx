import {useState} from "react"
import CvFile from "../../model/CvFile"
import Cv from "../Student/Cv"
import LoginForm from "../auth/LoginForm";

const StudentPage = ({user, setUser}) => {
  const [tab, setTab] = useState('home');

	const setCv = (cv) => {
		user.cvFile = new CvFile(cv)
		//TODO waiting for API addCv
		console.log("TODO waiting for API addCv")
	}

	return (
		<div className="container">
			{user.isLoggedIn ? (
				<div className="login-form">
					<h1 className="mb-4">Student Page</h1>
					<h2>Welcome {user.name}</h2>
					<div className="tabs">
						<button
							className={`btn btn-primary m-1 ${tab === 'home' ? 'active' : ''}`}
							onClick={() => setTab('home')}
						>
							Home
						</button>
						<button
							className={`btn btn-primary m-1 ${tab === 'stages' ? 'active' : ''}`}
							onClick={() => setTab('stages')}
						>
							Stages
						</button>
						<button
							className={`btn btn-primary m-1 ${tab === 'cv' ? 'active' : ''}`}
							onClick={() => setTab('cv')}
						>
							Cv
						</button>
					</div>
					{tab === 'home' && <h3>Home</h3>}
					{tab === 'stages' && <h3>Stages</h3>}
					{tab === 'cv' && <Cv user={user} />}
				</div>
			) : (
				<LoginForm user={user} setUser={setUser}/>
			)}
		</div>
	)
}

export default StudentPage
