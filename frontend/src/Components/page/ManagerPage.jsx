import JobOffers from "../manager/JobOffers";
import {useEffect, useState} from "react";
import Cvs from "../manager/Cvs";
import {axiosInstance} from "../../App";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {useSession} from "../util/SessionContext";
import ContractList from "../user/ContractList";
import Contract from "../../model/Contract";

const ManagerPage = ({user}) => {
    const {selectedSessionIndex} = useSession();
    const {t} = useTranslation();
    const [tab, setTab] = useState('stages');
    const [cvs, setCvs] = useState([{id: 1, fileName: "test"}]);
    const [offers, setOffers] = useState([{id: 1, title: "test", description: "test", date: "test", duration: "test", salary: "test", manager: "test", status: "test"}]);
    const [contracts, setContracts] = useState([new Contract()]);
    const navigate = useNavigate();

    useEffect(() => {
        if (!user?.isLoggedIn) navigate('/');

        getAllCvs().then(r => r);
        getAllOffers().then(r => r);
        getAllContracts().then(r => r);
    }, [user.isLoggedIn]);

    useEffect(() => {
        handleSessionChange();
    }, [selectedSessionIndex]);

    const handleSessionChange = () => {
        setCvs([])
        setOffers([]);
        setContracts([]);
        getAllCvs().then(r => r);
        getAllOffers().then(r => r);
        getAllContracts().then(r => r);
    }

    const getAllOffers = async () => {
        await axiosInstance.get('manager/jobOffers/all',
        ).then((response) => {
            setOffers(response.data);
            return response.data;
        }).catch((error) => {
            if (error.response?.status === 401) {
                return;
            }
            toast.error(t('fetchError') + t(error));
        });
    }
    const getAllCvs = async () => {
        await axiosInstance.get('manager/cvs/all',
        ).then((response) => {
            setCvs(response.data);
            return response.data;
        }).catch((error) => {
            if (error.response?.status === 401) {
                return;
            }
            toast.error(t('fetchError') + t(error));
        });
    }

    const getAllContracts = async () => {
        await axiosInstance.get('manager/contracts',
        ).then((response) => {
            setContracts(response.data)
        }).catch((error) => {
            if (error.response?.status === 401) {
                return;
            }
            toast.error(t('fetchError') + t(error));
        });
    }

    const updateJobOfferList = () => {
        setOffers(offers);
    }

    const updateJobOfferListAfterApprovalOrRefusal = (action, updatedJobOffer) => {
        const updatedOffers = offers.map(offer =>
            offer.id === updatedJobOffer.id
                ? { ...offer, jobOfferState: action }
                : offer
        );
        setOffers(updatedOffers);
    }


    const updateCvList = () => {
        setCvs(cvs);
    }

    const handleTabClick = async (tabName) => {
        setTab(tabName);
        switch(tabName) {
            case 'stages':
                await getAllOffers();
                break;
            case 'cvs':
                await getAllCvs();
                break;
            case 'contracts':
                await getAllContracts();
                break;
        }
    };

    return (
        <div className="container">
            <div>
                <div className="tabs btn-group my-2 mx-auto col-12">
                    <button className={`col-6 btn btn-outline-ose ${tab === 'stages' ? 'active' : ''}`} onClick={() => handleTabClick('stages')}>{t('internship')}</button>
                    <button className={`col-6 btn btn-outline-ose ${tab === 'cvs' ? 'active' : ''}`} onClick={() => handleTabClick('cvs')}>CVs</button>
                    <button className={`col-6 btn btn-outline-ose ${tab === 'contracts' ? 'active' : ''}`} onClick={() => handleTabClick('contracts')}>{t('contracts')}</button>
                </div>
                {tab === 'stages' && <JobOffers offers={offers} updateJobOfferList={updateJobOfferList} updateJobOfferListAfterApprovalOrRefusal={updateJobOfferListAfterApprovalOrRefusal}/>}
                {tab === 'cvs' && <Cvs cvs={cvs} updateCvList={updateCvList} getAllCvs={getAllCvs} />}
                {tab === 'contracts' && <ContractList contracts={contracts} user={user} />}
            </div>
        </div>
    )
}

export default ManagerPage
