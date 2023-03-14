'use strict';
import {renderPage, searchByName} from "../children-overview.js";
import {initNotifications} from "../notifications.js";

loadConfig().then(() => {
    initNotifications(socket);
    initCaretakerOverview();
});

function initCaretakerOverview() {
    document.querySelector("#search-child").addEventListener("input", searchByName);
    const childcareCenterId = loadFromStorage("childcareCenterId");
    get(`childcareCenter/${childcareCenterId}/childList`, (response) => {
        saveToStorage("allChildren", response);
        renderPage(response);
    });
}
