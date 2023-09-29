import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

function MyApplications({user}) {

    const [myApplications, setMyApplications] = useState([])

    const navigate = useNavigate();

    useEffect(() => {
        if(!user?.isLoggedIn){
            navigate('/');
        }

        async function fetchMyApplications() {
            // TODO : waiting for backend
        }

        fetchMyApplications()

    }, [user, navigate]);

    return(
        <div>
            {myApplications.length === 0 ?
                <p>Vous n'avez pas d'applications.</p>
                :
                <p></p> // TODO : render applications
            }
        </div>
    )

}

export default MyApplications