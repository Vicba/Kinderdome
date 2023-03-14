'use strict';
import {initNotifications} from "./notifications.js";

let _timelineChart;
let _map;
let _globalEventList;

loadConfig().then(() => {
    initMapPage();
    initNotifications(socket);
});


function initMapPage() {
    get("eventTypes", (response)  => {
        saveToStorage("eventTypes", response);
    });
    _map = createMap();
    fillSelect();
    document.querySelector("#select").addEventListener("change", changeSelected);
}

function fillSelect() {
    const $select = document.querySelector("#select");
    if (localStorage.getItem('allChildren') !== null) {
        const allChildren = loadFromStorage("allChildren");
        fillSelectOptions(allChildren, $select);
        changeSelected();
    } else {
        fillStorageWithChildren();
    }
}

function changeSelected() {
    const childID = document.querySelector("#select").value;
    saveToStorage("childID", childID);
    get(`child/${childID}/history`, renderMapPage);
}

function renderMapPage(eventList) {
    const timeline = document.querySelector('.timeline-chart').getContext('2d');
    if (_timelineChart) {
        _timelineChart.destroy();
    }
    _globalEventList = eventList;
    _timelineChart = new Chart(timeline, createTimelineConfig(_globalEventList, onClick));
    createMarkerLayers(eventList);
}

function createMarkerLayers(eventList) {
    const eventTypes = loadFromStorage("eventTypes");

    const layers = _map.getLayers().getArray().slice();
    layers.forEach(layer => {
        if (layer.className_ !== "ol-layer") {
            _map.removeLayer(layer);
        }
    });

    eventTypes.forEach(type => {
        const filteredList = filterListByType(eventList, type);
        if (filteredList.length > 0) {
            createMarkers(filteredList, type, _map);
        }
    });
}

function filterListByType(list, type) {
    return list.filter(event => event.eventType === type);
}

function onClick(clickEvent) {
    const selectedEvent = getEventFromClick(clickEvent);
    if (selectedEvent !== null) {
        const listWithoutEvent = _globalEventList.filter(event => event !== selectedEvent);
        createMarkerLayers(listWithoutEvent);
        createMarkers([selectedEvent], selectedEvent.eventType + "_red", _map);
    }
}
