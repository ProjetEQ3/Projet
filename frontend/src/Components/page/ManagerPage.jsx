import JobOffers from "../manager/JobOffers";
import {useState} from "react";
import Cvs from "../manager/Cvs";

const ManagerPage = ({user}) => {
    const [tab, setTab] = useState('stages');
    const cvsDTO = [{
    }];
    const offers = [{
        id: 1,
        title: "Offre de stage 1",
        state: "Submitted",
        department: "Informatique",
        location: "Montréal",
        description: "Description de l'offre de stage 1",
        salary: "20",
        hourPerWeek: "40",
        startDate: "2023-01-01",
        duration: "12 semaines",
        expireDate: "2022-12-31",
    },
        {
            id: 2,
            title: "Offre de stage 2",
            state: "Submitted",
            department: "Informatique",
            location: "Montréal",
            description: "Description de l'offre de stage 1",
            salary: "20",
            hourPerWeek: "40",
            startDate: "2023-01-01",
            duration: "12 semaines",
            expireDate: "2022-12-31",
        },
        {
            id: 3,
            title: "Offre de stage 3",
            state: "Submitted",
            department: "Informatique",
            location: "Montréal",
            description: "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits logiciels.",
            salary: "20",
            hourPerWeek: "40",
            startDate: "2023-01-01",
            duration: "12 semaines",
            expireDate: "2022-12-31",
        },
        {
            id: 4,
            title: "Offre de stage 4",
            state: "Submitted",
            department: "Informatique",
            location: "Montréal",
            description: "Description de l'offre de stage 1",
            salary: "20",
            hourPerWeek: "40",
            startDate: "2023-01-01",
            duration: "12 semaines",
            expireDate: "2022-12-31",
        },
        {
            id: 5,
            title: "Offre de stage 5",
            state: "Submitted",
            department: "Informatique",
            location: "Montréal",
            description: "Description de l'offre de stage 1",
            salary: "20",
            hourPerWeek: "40",
            startDate: "2023-01-01",
            duration: "12 semaines",
            expireDate: "2022-12-31",
        }];
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
