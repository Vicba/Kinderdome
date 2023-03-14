'use strict';
import {initNotifications} from "../notifications.js";
import {searchByName, renderPage} from "../children-overview.js";

loadConfig().then(() => {
    initNotifications(socket);
    initChildrenOverview();
});

function initChildrenOverview() {
    document.querySelector("#search-child").addEventListener("input", searchByName);
    const parentId = loadFromStorage("parentId");
    const childcareCenterId = loadFromStorage("childcareCenterId");
    get(`parent/${parentId}/childList`, (response) => {
        const filteredList = response.filter(child => child.childcareCenterID === childcareCenterId);
        saveToStorage("allChildren", filteredList);
        renderPage(filteredList);
    });
}
