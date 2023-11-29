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
import StudentList from "../manager/StudentList";
import Student from "../../model/Student";
import Home from "../manager/Home";
import NotificationBadge from "../notification/NotificationBadge";
import JobOffer from "../../model/JobOffer";
import CvFile from "../../model/CvFile";

const ManagerPage = ({user}) => {
    const {selectedSessionIndex} = useSession();
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [tab, setTab] = useState('home');
    const [cvs, setCvs] = useState([new CvFile()]);
    const [offers, setOffers] = useState([new JobOffer()]);
    const [contracts, setContracts] = useState([new Contract()]);
    const [students, setStudents] = useState([new Student()])
    const [idElement, setIdElement] = useState(null);
    const [offerFilter, setOfferFilter] = useState(null);
    const tabConfig = [
        { key: 'home', label: t('home') },
        { key: 'stages', label: t('internship') },
        { key: 'cvs', label: 'CVs' },
        { key: 'contracts', label: t('contracts') },
        { key: 'students', label: t('students') },
    ];
    const [notifications, setNotifications] = useState({
        home: { green: 0, gray: 0, red: 0 },
        stages: { green: 0, gray: 0, red: 0 },
        cvs: { green: 0, gray: 0, red: 0 },
        contracts: { green: 0, gray: 0, red: 0 },
        students: { green: 0, gray: 0, red: 0 },
    });

    useEffect(() => {
        if (!user?.isLoggedIn) navigate('/');
        getAllCvs().then(r => r);
        getAllOffers().then(r => r);
        getAllContracts().then(r => r);
        getAllStudents().then(r => r);
    }, [user]);

    useEffect(() => {
        handleSessionChange();
    }, [selectedSessionIndex, tab]);

    const handleSessionChange = () => {
        setCvs([])
        setOffers([]);
        setStudents([]);
        getAllCvs().then(r => r);
        getAllOffers().then(r => r);
        getAllContracts().then(r => r);
        getAllStudents().then(r => r);
    }

    const getAllStudents = async () => {
        await axiosInstance.get('manager/students')
            .then((response) => {
                setStudents(response.data);
            }).catch((error) => {
                if (error.response?.status === 401) {
                    return;
                }
                toast.error(t('fetchError') + t(error));
            });
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
        setOfferFilter(null);
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
            case 'students':
                await getAllStudents();
                break;
        }
        setIdElement(null)
    };

    useEffect(() => {
        getNotificationsCounts();
    }, [offers, cvs, contracts, students]);

    function getNotificationsCounts() {
        updateNotifications('home', { green: 0, gray: 0, red: 0 });

        let redStages = offers.filter(offer => offer.jobOfferState === 'SUBMITTED').length;

        updateNotifications('stages', { green: 0, gray: 0, red: redStages });

        let red = cvs.filter(cv => cv.cvState === 'SUBMITTED').length;
        updateNotifications('cvs', { green: 0, gray: 0, red: red });

        let redNotificationsContract = contracts.filter((contract) => (
            contract.managerSignature === null && contract.studentSignature !== null && contract.employerSignature !== null)).length;
        let grayNotificationsContract = contracts.filter((contract) => (
            contract.complete === false && (contract.studentSignature === null || contract.employerSignature === null))).length;

        updateNotifications('contracts', { green: 0, gray: grayNotificationsContract, red: redNotificationsContract});
        updateNotifications('students', { green: 0, gray: 0, red: 0 });
    }


    const updateNotifications = (tabId, { green, gray, red }) => {
        setNotifications(prevNotifications => ({
            ...prevNotifications,
            [tabId]: { green, gray, red }
        }));
    };

    return (
        <div className="container">
            <div>
                <div className="tabs btn-group my-2 mx-auto col-12">
                    {tabConfig.map((item) => (
                        <button
                            key={item.key}
                            className={`col-6 mx-2 btn btn-outline-ose ${tab === item.key ? 'active' : ''}`}
                            onClick={() => handleTabClick(item.key)}
                        >
                            {item.label}
                            <NotificationBadge key={item.label} notifications={notifications[item.key]} tab={item} setTab={setTab} titleInfos={item.label}/>
                        </button>
                    ))}
                </div>
                {tab === 'home' && <Home setTab={setTab} setIdElement={setIdElement} nbCvs={notifications['cvs'].red} contracts={contracts} nbSubmittedOffers={notifications['stages'].red} setOfferFilter={setOfferFilter} />}
                {tab === 'stages' && <JobOffers offers={offers} updateJobOfferList={updateJobOfferList}
                                                updateJobOfferListAfterApprovalOrRefusal={updateJobOfferListAfterApprovalOrRefusal}
                                                selectedState={offerFilter} />}
                {tab === 'cvs' && <Cvs cvs={cvs} updateCvList={updateCvList} getAllCvs={getAllCvs} selectedById={idElement} />}
                {tab === 'contracts' && <ContractList contracts={contracts} user={user} reloadContracts={getAllContracts}/>}
                {tab === 'students' && <StudentList students={students}/>}
            </div>
        </div>
    );
}

export default ManagerPage
