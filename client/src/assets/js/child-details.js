import {initNotifications} from "./notifications.js";

let _timelineChart;
let _globalEventList;
let _bodyTempChart;
let _heartRateChart;

loadConfig().then(() => {
    initNotifications(socket);
    initChildDetails();
});


function initChildDetails() {
    initNotifications(socket);
    const childId = loadFromStorage("childId");

    get(`child/${childId}/history`, renderHistory);

    document.querySelectorAll(".toggle-view").forEach(item => {
        item.addEventListener("click", () => {
            toggleInfo("toggle-panels");
        });
    });

    document.querySelectorAll(".toggle_remove").forEach(item => {
        item.addEventListener("click", () => {
            toggleInfo("basic-info");
        });
    });

    if (document.querySelector("body").id === "child-details-parent") {
        document.querySelector("#remove-child").addEventListener("click", disenrollChild);
    }

    get(`child/${childId}`, renderOverviewPage);
}

function disenrollChild() {
    const childId = loadFromStorage("childId");
    const childcareCenterId = loadFromStorage("childcareCenterId");
    put(`childcareCenter/${childcareCenterId}/child/${childId}/disenroll`, {"": ""}, successfullyDisenrolled);
}

function successfullyDisenrolled() {
    location.href = "../parent/children-overview.html";
}

function toggleInfo(classname) {
    document.querySelectorAll(`.${classname}`).forEach(element => {
        element.classList.toggle("hidden");
    });
}

function renderOverviewPage(child) {
    document.querySelector("#childName").innerText = child.name;
    document.querySelector("#profile-picture").src = `../assets/images/children/${child.id}.jpg`;
}

function renderHistory(eventList) {
    const $status = document.querySelector("#status");
    const lastElement = eventList.at(-1);
    if (lastElement.eventType === "Emergency") {
        $status.innerText = `Status: ${lastElement.eventType} (${lastElement.EmergencyStatus}).`;
    } else if (lastElement.eventType === "Generic") {
        $status.innerText = `Status: ${lastElement.description}.`;
    } else {
        $status.innerText = `Status: ${lastElement.eventType}.`;
    }
    createCharts(eventList);
    renderSmallInfo(eventList);
}

function createCharts(eventList) {
    const timeline = document.querySelector('.timeline-chart').getContext('2d');
    const bodyTemp = document.querySelector('#body-temp-chart').getContext('2d');
    const heartRate = document.querySelector('#heart-rate-chart').getContext('2d');
    _globalEventList = eventList;
    _timelineChart = new Chart(timeline, createTimelineConfig(eventList, renderEventInfo));
    _bodyTempChart = new Chart(bodyTemp, createConfig(eventList, "bodyTemp"));
    _heartRateChart = new Chart(heartRate, createConfig(eventList, "heartRate"));
}

function renderSmallInfo(eventList) {
    const $nutrition = document.querySelector("#nutrition span");
    const $words = document.querySelector("#words span");
    const $tooltip = document.querySelector("#tooltiptext");
    let nutrition = 0;
    let words = 0;
    const wordsList = [];
    eventList.forEach(event => {
        if (event.eventType === "Eating") {
            nutrition += event.nutritionalValue;
        } else if (event.eventType === "SpokenWord") {
            words++;
            wordsList.push(event.word.word);
        }
    });
    $nutrition.innerText = nutrition;
    $words.innerText = words;
    const top5Words = getTop5Words(wordsList);
    $tooltip.innerHTML = "<h3>5 Most used words:</h3>" + top5Words.join("<br>");
}
function getTop5Words(list) {
    const frequencyMap = {};
    for (const str of list) {
        if (frequencyMap[str]) {
            frequencyMap[str]++;
        } else {
            frequencyMap[str] = 1;
        }
    }
    list.sort((a, b) => frequencyMap[b] - frequencyMap[a]);
    return removeRepeatingStrings(list).slice(0, 5);
}
function removeRepeatingStrings(strings) {
    const uniqueStrings = new Set();
    for (const string of strings) {
        if (!uniqueStrings.has(string)) {
            uniqueStrings.add(string);
        }
    }
    return Array.from(uniqueStrings);
}

function renderEventInfo(clickEvent) {
    const event = getEventFromClick(clickEvent);
    document.querySelector("#event-info h3").innerHTML = event.eventType;
    document.querySelector("#description").innerHTML = event.description;
}
