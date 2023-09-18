import JobOffers from "../manager/JobOffers";
import {useEffect, useState} from "react";
import Cvs from "../manager/Cvs";
import {axiosInstance} from "../../App";

const ManagerPage = ({user}) => {
    const [tab, setTab] = useState('stages');
    const cvsDTO = [{
    }];
    const [offers, setOffers] = useState([{id: 1, title: "test", description: "test", date: "test", duration: "test", salary: "test", manager: "test", status: "test"}]);

    useEffect(() => {
        const getAllOffers = async () => {
            await axiosInstance.get('manager/jobOffers/all',
                // {headers: {Authorization: 'Bearer ' + localStorage.getItem('token')}}
            ).then((response) => {
                setOffers(response.data);
                return response.data;
            }).catch((error) => {
                console.log(error);
            });
        }

        getAllOffers()
    }, []);

    return (
        <div className="container">
            <div>
                <div className="tabs btn-group my-2 mx-auto col-12">
                    <button className={`btn btn-outline-ose ${tab === 'stages' ? 'active' : ''}`}
                            onClick={() => setTab('stages')}>Stages</button>
                    <button className={`btn btn-outline-ose ${tab === 'cvs' ? 'active' : ''}`}
                            onClick={() => setTab('cvs')}>CVs</button>
                </div>
                {tab === 'stages' && <JobOffers offers={offers}/>}
                {tab === 'cvs' && <Cvs cvs={cvsDTO} />}
            </div>
        </div>
    )
}

export default ManagerPage
