import React, {useEffect} from 'react';
import ShortContract from "./ShortContract";
import { axiosInstance } from '../../App';

const ContractList = ({ contracts }) => {
    contracts = [1];
    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {contracts.map((contract, index) => (
                        <div key={index}>
                            <ShortContract />
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default ContractList;
