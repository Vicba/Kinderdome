'use strict';
import {initNotifications} from "../notifications.js";
import {searchByName, renderPage} from "../children-overview.js";

loadConfig().then(() => {
    initAddChild();
    initNotifications(socket);
});

function initAddChild() {
    document.querySelector("#search-child").addEventListener("input", searchByName);
    const parentId = loadFromStorage("parentId");
    const noCenterAssigned = -1;
    get(`parent/${parentId}/childList`, (response) => {
        renderPage(response.filter(child => child.childcareCenterID === noCenterAssigned));
        addEventListeners();
    });
}

function addEventListeners() {
    document.querySelectorAll(".child-card").forEach(element => {
        element.addEventListener("click", confirmationPopup);
    });
}

function confirmationPopup(e) {
    const childId = e.currentTarget.id;
    const childName = e.currentTarget.innerText;
    clearPopup();
    addToPopup(`<p>Are you sure you want to enroll ${childName}?</p>`);
    addToPopup(`<div><span class="material-symbols-outlined dialog-option" id="cancel">close</span>
                <span class="material-symbols-outlined dialog-option" id="confirm">done</span>
            </div>`);
    document.querySelector("#cancel").addEventListener("click", hidePopup);
    document.querySelector("#confirm").addEventListener("click", () => {
        hidePopup();
        confirmEnrollment(childId);
    });
    showPopup();
}

function confirmEnrollment(childId) {
    const childcareCenterId = loadFromStorage("childcareCenterId");
    put(`childcareCenter/${childcareCenterId}/child/${childId}/enroll`, {"": ""}, initAddChild);
}

function hidePopup() {
    const $popup = document.querySelector("#dialog-container");
    $popup.classList.remove("show-popup");
    $popup.classList.add("hide");
}

function showPopup() {
    const $popup = document.querySelector("#dialog-container");
    $popup.classList.remove("hide");
    $popup.classList.add("show-popup");
}

function clearPopup() {
    const $popup = document.querySelector("#popup");
    $popup.innerHTML = "";
}

function addToPopup(content) {
    const $popup = document.querySelector("#popup");
    $popup.insertAdjacentHTML("beforeend", content);
}

