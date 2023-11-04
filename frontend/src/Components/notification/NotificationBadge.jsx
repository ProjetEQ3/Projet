import React from 'react';
import './NotificationBadge.css';

const NotificationBadge = ({ count, color, index, tab, setTab, user }) => {

    const handleNotificationClick = () => {
        setTab(tab.id);
    };

    if (count > 0) {
        const badgeStyle = {
            right: `${index * 20}px`,
            zIndex: 2 + index
        };

        const badgeClass = `notification-badge notification-badge-${color}`;
        return (
            <button className={badgeClass} style={badgeStyle} onClick={handleNotificationClick}>
        {count}
      </button>
        );
    }

    return null;
};

export default NotificationBadge;
