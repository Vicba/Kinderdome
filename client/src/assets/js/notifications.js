'use strict';

const CHNL_FROM_PARENT = "messages.from.parent.";
const CHNL_FROM_CARETAKER = "messages.from.caretakers";


function initNotifications(socket) {
    const ebNotifications = new EventBus(socket);
    ebNotifications.onopen = function() {
        ebNotifications.registerHandler(CHNL_FROM_PARENT, (error, message) => {
            (!error) ? createNotification("parent",message) : console.error(error);
        });
        ebNotifications.registerHandler(CHNL_FROM_CARETAKER, (error, message) => {
            (!error) ? createNotification("caretaker", message) : console.error(error);
        });
    };

    Notification.requestPermission();
}

function createNotification(title, message) {
    new Notification(`New message from a ${title}!`, {
        body: message.body,
        data: message.body
    });
}

export {initNotifications};
