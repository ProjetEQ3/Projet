import React from 'react';
import './NotificationBadge.css';

const NotificationBadge = ({ notifications, tab, setTab }) => {
    const badgeTypes = ['red', 'yellow', 'green'];
    let visibleBadges = badgeTypes.filter(type => notifications[type] > 0);
    const handleNotificationClick = () => {
        setTab(tab.id);
    };

    return (
        <>
            {visibleBadges.map((type, index) => (
                <button
                    key={type}
                    className={`notification-badge notification-badge-${type}`}
                    style={{ right: `${index * 20}px` }}
                    onClick={handleNotificationClick}
                >
          {notifications[type]}
        </button>
            ))}
        </>
    );
};

export default NotificationBadge;
