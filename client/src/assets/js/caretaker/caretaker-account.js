'use strict';
import {initNotifications} from "../notifications.js";

loadConfig().then(() => {
    initCaretakerAccount();
    initNotifications(socket);
});

function initCaretakerAccount() {
    const childcareCenterId = loadFromStorage("childcareCenterId");
    const caretakerId = loadFromStorage("caretakerId");
    get(`childcareCenter/${childcareCenterId}/caretaker/${caretakerId}`, renderAccountPage);
}

function renderAccountPage(caretaker) {
    document.querySelector("#caretakerName").innerText = caretaker.name;
    document.querySelector("#extra-info").innerHTML = `
    <ul>
        <li>Employee ID: ${caretaker.caretakerID}</li>
        <li>Employed at: ${caretaker.childcareCenter.childcareCenterName}</li>
        <li>Current salary: \$${caretaker.salary}</li>
    </ul>
    `;
}
