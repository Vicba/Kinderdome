'use strict';


import {initNotifications} from "../notifications.js";

loadConfig().then(() => {
    initEmergencyParentLoad();
    initNotifications(socket);
});

function initEmergencyParentLoad() {
    const parentId = loadFromStorage("parentId");
    get(`parent/${parentId}/emergencies`, (response) => {
        saveToStorage("allEmergencies", response);
        renderEmergenciesPage(response);
    });

}
