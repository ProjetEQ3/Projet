import Header from "../layout/Header";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import ShortJobOffer from "../employer/ShortJobOffer";
import FullJobOffer from "../employer/FullJobOffer";
import {useState} from "react";

const EmployerPage = () => {
    const [selectedOffer, setSelectedOffer] = useState(null);
    const handleSelectOffer = (offer) => {
        setSelectedOffer(offer);
    }

    const testOffre = [{
        title: "Offre de stage 1",
        state: "Open",
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
            title: "Offre de stage 2",
            state: "Pending",
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
            title: "Offre de stage 4",
            state: "Expired",
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
            title: "Offre de stage 5",
            state: "Taken",
            department: "Informatique",
            location: "Montréal",
            description: "Description de l'offre de stage 1",
            salary: "20",
            hourPerWeek: "40",
            startDate: "2023-01-01",
            duration: "12 semaines",
            expireDate: "2022-12-31",
        }]

    const user = "foo";
    return (
        <div className="bg-light">
            <Header/>
            <div className="container-fluid px-5 py-2">
                <div className="row text-center">
                    <div className="col-12">
                        <h2 className="text-dark fw-light">Bonjour {user}!</h2>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <h3 className="text-dark fw-light">Vos offres de stages</h3>
                        <div className="row justify-content-around">
                            <div className="col-6">
                                {
                                    testOffre.map((offer, index) => (
                                        <ShortJobOffer key={index} jobOffer={offer} onClick={handleSelectOffer(offer)}/>
                                    ))
                                }
                                <div className="row m-2">
                                    <button className="btn btn-outline-ose col-12"><FontAwesomeIcon icon={faPlus} /></button>
                                </div>
                            </div>
                            <div className="col-6">
                                <FullJobOffer jobOffer={testOffre[2]}/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployerPage;