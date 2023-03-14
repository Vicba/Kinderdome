"use strict";

let _week = 0;

document.addEventListener("DOMContentLoaded", initMenu);

function initMenu() {
    getLocal("menus.json", data => {
        renderPage(data);
    });
}

function renderPage(data) {
    if (data[_week].description) {
        document.querySelector("#days").innerHTML = `<h2 class="last-week">${data[_week].description}</h2>`;
    } else {
        renderMenu(data[_week]);
    }
    const weeksCount = data.length;
    renderChevrons();
    const $previousWeek = document.querySelector("#previous-week");
    const $nextWeek = document.querySelector("#next-week");
    if (_week === 0) {
        $previousWeek.classList.add("unavailable");
    } else if (_week === weeksCount - 1) {
        $nextWeek.classList.add("unavailable");
    }
    $nextWeek.addEventListener("click", (e) => {
        showNewWeek(e, data);
    });
    $previousWeek.addEventListener("click", (e) => {
        showNewWeek(e, data);
    });
}

function renderChevrons() {
    const $menuHeader = document.querySelector("#menu-header");
    $menuHeader.querySelectorAll(".toggle-view").forEach(span => {
        span.remove();
    });
    $menuHeader.insertAdjacentHTML("afterbegin", `<span id="previous-week" class="material-symbols-outlined toggle-view">chevron_left</span>`);
    $menuHeader.insertAdjacentHTML("beforeend", `<span id="next-week" class="material-symbols-outlined toggle-view">chevron_right</span>`);
}

function renderMenu(weekMenu) {
    document.querySelector("#menu-dates").innerText = `${weekMenu.dateStartWeek} - ${weekMenu.dateEndWeek}`;
    document.querySelector("#days").innerHTML = "";
    const $menusTemplate = document.querySelector('#menu-template').content.firstElementChild.cloneNode(true);
    weekMenu.days.forEach(day => {
        $menusTemplate.querySelector("h2").innerText = day.day;
        $menusTemplate.querySelector("#breakfast .meal").innerText = day.breakfast.meal;
        $menusTemplate.querySelector("#breakfast .drinks").innerText = day.breakfast.drinks;

        $menusTemplate.querySelector("#lunch .meal").innerText = day.lunch.meal;
        $menusTemplate.querySelector("#lunch .drinks").innerText = day.lunch.drinks;

        $menusTemplate.querySelector("#dinner .meal").innerText = day.dinner.meal;
        $menusTemplate.querySelector("#dinner .drinks").innerText = day.dinner.drinks;

        document.querySelector('#days').insertAdjacentHTML("beforeend", $menusTemplate.outerHTML);
    });
}

function showNewWeek(e, data) {
    if (e.target.classList.contains("unavailable")) {
        return;
    }
    if (e.target.id === "next-week") {
        _week++;
        renderPage(data);
    } else {
        _week--;
        renderPage(data);
    }
}

function getLocal(uri, successHandler = logJson, failureHandler = logError) {
    const request = "menus.json";
    call(request, successHandler, failureHandler);
}
