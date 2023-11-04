import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const CircleNotification = ({ tab, user }) => {
    const [notifications, setNotifications] = useState([
        { type: 'success', count: 3 },
        { type: 'warning', count: 2 },
        { type: 'danger', count: 1 },
    ]);

    const handleNotificationClick = (type) => {
        // Find the first notification of the specified type
        const firstNotification = notifications.find((notif) => notif.type === type);

        if (firstNotification) {
            // Redirect to the first notification (you need to adjust this part based on your routing setup)
            window.location.href = `/${user.role.split('_')[1].toLowerCase()}/${tab}`;

            // Update the notification count
            setNotifications((prevNotifications) => {
                const updatedNotifications = prevNotifications.map((notif) => {
                    if (notif.type === type) {
                        return { ...notif, count: 0 };
                    }
                    return notif;
                });
                return updatedNotifications;
            });
        }
    };

    return (
        <>
            {notifications.map((notif) => (
                notif.count !== 0 ? (
                    <button
                        key={notif.type}
                        className={`btn btn-${notif.type} p-0 px-1 ms-1`}
                        onClick={() => handleNotificationClick(notif.type)}
                    >
                        {notif.count}
                    </button>
                ) : null
            ))}
        </>
    );
};

export default CircleNotification;
