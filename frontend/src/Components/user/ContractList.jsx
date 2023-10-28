import React, {useEffect} from 'react';
import ShortContract from "./ShortContract";
import { axiosInstance } from '../../App';

const ContractList = ({ contracts }) => {
    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    {contracts.map((contract, index) => (
                        <div key={index}>
                            <ShortContract contractId={contracts[index]}/>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default ContractList;
