import HeadTemplate from "./HeadTemplate";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faPlus } from '@fortawesome/free-solid-svg-icons';

const EmployerPage = () => {
    const user = "foo";
    return (
        <div className="bg-light">
            <HeadTemplate/>
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
                                <div className="row">
                                    <div className="col-12 border border-danger">
                                        <h4 className="text-dark fw-light">Offre de stage 1</h4>
                                        <p className="text-dark fw-light">Description de l'offre de stage 1</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <button className="btn btn-outline-ose col-12"><FontAwesomeIcon icon={faPlus} /></button>
                                </div>
                            </div>
                            <div className="col-6">
                                <div className="row">
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