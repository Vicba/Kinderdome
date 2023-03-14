'use strict';


function renderEmergenciesPage(data) {
    document.querySelector("#emergencies").innerHTML = "";
    const $emergencyTemplate = document.querySelector('#emergency-template').content.firstElementChild.cloneNode(true);

    data.forEach(emergency => {
        $emergencyTemplate.querySelector("h2").innerText = emergency.name;
        $emergencyTemplate.id = emergency.id;
        $emergencyTemplate.querySelector(".emergency-description").innerText = emergency.emergency;
        $emergencyTemplate.querySelector(".time").innerText = emergency.date;
        if (emergency.childId !== undefined){
            $emergencyTemplate.querySelector("img").src = `../assets/images/children/${emergency.childId}.jpg`;
        }
        $emergencyTemplate.querySelector(".status").innerText = emergency.status;
        document.querySelector('#emergencies').insertAdjacentHTML("beforeend", $emergencyTemplate.outerHTML);
    });

    document.querySelectorAll(".contact").forEach(div => {
        div.addEventListener("click", contact);});
}

function contact(){
    const currentPage = document.querySelector("body").id;
    if(currentPage === "parent-emergencies") {
        location.href = "../parent/call.html";
    }else {
        location.href = "../caretaker/call.html";
    }
}
