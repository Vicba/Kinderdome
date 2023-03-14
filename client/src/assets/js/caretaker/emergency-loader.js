'use strict';

import {initNotifications} from "../notifications.js";

loadConfig().then(() => {
    initEmergencyCaretakerLoad();
    initNotifications(socket);
});

function initEmergencyCaretakerLoad() {
    const childcareCenterId = loadFromStorage("childcareCenterId");
    get(`childcareCenter/${childcareCenterId}/emergencies`, (response) => {
        saveToStorage("allEmergencies", response);
        renderEmergenciesPage(response);
    });

}
