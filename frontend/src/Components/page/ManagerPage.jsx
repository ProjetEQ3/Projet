import JobOffers from "../manager/JobOffers";
import {useEffect, useState} from "react";
import Cvs from "../manager/Cvs";
import {axiosInstance} from "../../App";

const ManagerPage = ({user}) => {
    const [tab, setTab] = useState('stages');
    const [cvs, setCvs] = useState([{id: 1, fileName: "test"}]);
    const [offers, setOffers] = useState([{id: 1, title: "test", description: "test", date: "test", duration: "test", salary: "test", manager: "test", status: "test"}]);

    useEffect(() => {
        const getAllOffers = async () => {
            await axiosInstance.get('manager/jobOffers/submitted',
                // {headers: {Authorization: 'Bearer ' + localStorage.getItem('token')}}
            ).then((response) => {
                setOffers(response.data);
                return response.data;
            }).catch((error) => {
                console.log(error);
            });
        }
        console.log(offers)
        getAllOffers()
    }, []);

    useEffect(() => {
        const getAllCvs = async () => {
            await axiosInstance.get('manager/cvs/pending',
                // {headers: {Authorization: 'Bearer ' + localStorage.getItem('token')}}
            ).then((response) => {
                setCvs(response.data);
                console.log(response.data);
                return response.data;
            }).catch((error) => {
                console.log(error);
            });
        }

        getAllCvs()
    }, []);

    const updateJobOfferList = (jobOffer) => {
        setOffers(offers.filter((offer) => offer.id !== jobOffer.id));
    }

    return (
        <div className="container">
            <div>
                <div className="tabs btn-group my-2 mx-auto col-12">
                    <button className={`btn btn-outline-ose ${tab === 'stages' ? 'active' : ''}`}
                            onClick={() => setTab('stages')}>Stages</button>
                    <button className={`btn btn-outline-ose ${tab === 'cvs' ? 'active' : ''}`}
                            onClick={() => setTab('cvs')}>CVs</button>
                </div>
                {tab === 'stages' && <JobOffers offers={offers} updateJobOfferList={updateJobOfferList}/>}
                {tab === 'cvs' && <Cvs cvs={cvs} />}
            </div>
        </div>
    )
}

export default ManagerPage
