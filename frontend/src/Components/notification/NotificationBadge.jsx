import React from 'react';
import './NotificationBadge.css';

const NotificationBadge = ({ count, color, index }) => {
    if (count > 0) {
        const badgeStyle = {
            right: `${index * 20}px`,
            zIndex: 2 + index
        };

        const badgeClass = `notification-badge notification-badge-${color}`;
        return (
            <span className={badgeClass} style={badgeStyle}>
        {count}
      </span>
        );
    }

    return null;
};

export default NotificationBadge;
