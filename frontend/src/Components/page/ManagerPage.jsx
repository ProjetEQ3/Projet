import FullJobOffer from "../manager/FullJobOffer";
import ShortJobOffer from "../manager/ShortJobOffer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from "@fortawesome/free-solid-svg-icons";

const ManagerPage = ({user}) => {
    const offersDTO = [{
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
        <div className="row">
            <div className="col-12">
                <h3 className="text-dark fw-light my-5">Les offres de stages en attente de votre réponse :</h3>
                <div className="row justify-content-around">
                    <div className="col-12">
                        {
                            offersDTO.map((offer, index) => (
                                <div onClick={() => (offer)}>
                                    <ShortJobOffer jobOffer={offer} key={index}/>
                                </div>
                            ))
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ManagerPage
