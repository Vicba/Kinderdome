"use strict";

let api;
let socket;

function saveToStorage(key, value) {
    if (localStorage) {
        return localStorage.setItem(key,JSON.stringify(value));
    }else {
        return null;
    }
}

function loadFromStorage(key) {
    if (localStorage) {
        return JSON.parse(localStorage.getItem(key));
    }else {
        return null;
    }
}

function fillSelectOptions(data, selectElement) {
    data.forEach(e => {
        const option = document.createElement("option");
        option.text = e.name;
        option.value = e.id;
        option.id = e.id;
        selectElement.add(option);
    });
}

function fillStorageWithChildren() {
    const currentPage = document.querySelector("body").id;
    if(currentPage.includes("parent")) {
        const defaultParentId = loadFromStorage("parentId");
        get(`parent/${defaultParentId}/childList`, (data) => {
            saveToStorage("allChildren", data);
            fillSelect();
        });
    } else if (currentPage.includes("caretaker")) {
        const defaultChildcareCenterID = 1;
        get(`caretaker/${defaultChildcareCenterID}/childList`, (data) => {
            saveToStorage("allChildren", data);
            fillSelect();
        });
    }
}

async function loadConfig() {
    return fetch("../config.json")
        .then(resp => resp.json())
        .then(config => {
            api = `${config.host ? config.host + '/' : ''}${config.group ? config.group + '/' : ''}api/`;
            socket = `${config.host}/${config.group}/events/`;
        });
}
