'use strict';

function renderPage(data) {
    document.querySelector("#children").innerHTML = "";
    if (data.length === 0) {
        document.querySelector(".error-message").classList.toggle("hidden");
        return;
    }
    const $childTemplate = document.querySelector('#child-template').content.firstElementChild.cloneNode(true);

    data.forEach(child => {
        $childTemplate.querySelector("h2").innerText = child.name;
        $childTemplate.id = child.id;
        $childTemplate.querySelector("img").src = `../assets/images/children/${child.id}.jpg`;
        document.querySelector('#children').insertAdjacentHTML("beforeend", $childTemplate.outerHTML);
    });

    document.querySelectorAll("#children div").forEach(div => {
        div.addEventListener("click", function (e) {
            saveToStorage("childId", e.currentTarget.id);
        });
    });
}

function clearChildren() {
    document.querySelector("#children").innerHTML = "";
}

function searchByName(e) {
    const searchTerm = e.target.value.toLowerCase();
    const allChildren = loadFromStorage("allChildren");
    const selection = [];
    allChildren.forEach(child => {
        if (child.name.toLowerCase().includes(searchTerm)) {
            selection.push(child);
        }
    });
    clearChildren();
    renderPage(selection);
}

export {renderPage, searchByName};
