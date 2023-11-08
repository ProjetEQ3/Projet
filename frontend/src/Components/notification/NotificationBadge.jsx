import React from 'react';
import './NotificationBadge.css';
import {useTranslation} from "react-i18next";

const NotificationBadge = ({ notifications, tab, setTab, titleInfos }) => {
    const {t} = useTranslation();
    if (titleInfos === undefined) titleInfos = '';
    const badgeTypes = ['red', 'green', 'gray'];
    let visibleBadges = badgeTypes.filter(type => notifications[type] > 0 || notifications[type] === -1);
    const handleNotificationClick = () => {
        setTab(tab.id);
    };

    return (
        <>
            {visibleBadges.map((type, index) => (
                <button
                    key={type}
                    className={`notification-badge notification-badge-${type}`}
                    title={t(titleInfos)}
                    style={{ right: `${index * 22}px` }}
                    onClick={handleNotificationClick}
                >
                    {notifications[type] === -1 ? ' ' : notifications[type]}
                </button>
            ))}
        </>
    );
};

export default NotificationBadge;
