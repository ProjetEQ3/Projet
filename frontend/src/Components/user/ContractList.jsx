import React, {useEffect} from 'react';
import ShortContract from "./ShortContract";
import { axiosInstance } from '../../App';

const ContractList = ({ contracts, user }) => {
    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {contracts.map((contract, index) => (
                        <div key={index}>
                            <ShortContract contract={contracts[index]} user={user} />
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default ContractList;
