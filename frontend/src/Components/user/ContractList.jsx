import React, {useEffect} from 'react';
import ShortContract from "./ShortContract";
import { axiosInstance } from '../../App';
import {useTranslation} from "react-i18next";

const ContractList = ({ contracts, user, reloadContracts }) => {
    const {t} = useTranslation();

    return (
        <div className="container" data-testid="contract-list">
            <div className="row">
                <div className="col-12">
                    {contracts.length > 0 ? contracts.map((contract, index) => (
                        <div key={index}>
                            <ShortContract contract={contracts[index]} user={user} reloadContracts={reloadContracts}/>
                        </div>
                    )) : (
                        <div className="text-center">
                            <h4>{t('noContracts')}</h4>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default ContractList;
