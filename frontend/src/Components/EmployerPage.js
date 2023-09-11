import HeadTemplate from "./HeadTemplate";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faPlus } from '@fortawesome/free-solid-svg-icons';

const EmployerPage = () => {
    const user = "foo";
    return (
        <div>
            <HeadTemplate/>
            <div className="container">
                <div className="row text-center">
                    <div className="col-12">
                        <h2 className="text-dark fw-light">Bonjour {user}!</h2>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <h3 className="text-dark fw-light">Vos offres de stages</h3>
                        <div className="row">
                            <button className="btn btn-outline-ose"><FontAwesomeIcon icon={faPlus} /></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployerPage;