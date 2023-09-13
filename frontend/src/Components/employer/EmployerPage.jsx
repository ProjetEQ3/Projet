import Header from "../layout/Header";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import ShortJobOffer from "./ShortJobOffer";

const EmployerPage = () => {
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
            description: "Description de l'offre de stage 1",
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
                                    testOffre.map((offre, index) => (
                                        <ShortJobOffer key={index} jobOffer={offre}/>
                                    ))
                                }
                                <div className="row m-2">
                                    <button className="btn btn-outline-ose col-12"><FontAwesomeIcon icon={faPlus} /></button>
                                </div>
                            </div>
                            <div className="col-6">
                                <div className="row m-2">
                                    <div className="col-12 border border-danger">
                                        <h4 className="text-dark fw-light">Offre de stage 2</h4>
                                        <p className="text-dark fw-light">Description de l'offre de stage 2</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployerPage;