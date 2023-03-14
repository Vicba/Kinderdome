let sendToServer;

loadConfig().then(() => {
    initCaretakerComm();
});

function initCaretakerComm() {
    sendToServer = openSocket(loadFromStorage("caretakerId"));
    initCalling();
    fillSelect();
    document.querySelector("#send").addEventListener('click', sendMessage);
    document.querySelector("#select").addEventListener("change", changeSelected);
}

function fillSelect() {
    const $select = document.querySelector("#select");
    const centerId = loadFromStorage("childcareCenterId");
    get(`childcareCenter/${centerId}/parentList`, (response) => {
        fillSelectOptions(response, $select);
    });
    changeSelected();
}

function sendMessage() {
    const $input = document.querySelector("#chat-field");
    const message = $input.value;
    $input.value = "";
    if (!$input.disabled && message !== "") {
        const data = {type: "from_caretaker", message: message};
        sendToServer(data);
    }
}

function changeSelected() {
    document.querySelector("#messages").innerHTML = "";
}

function onMessage(message, sender) {
    const $messages = document.querySelector("#messages");
    const cleanedMessage = cleanMessages(message.body);
    if (sender === "caretaker"){
        $messages.insertAdjacentHTML("afterbegin", `<p class="message right">${cleanedMessage}</p>`);
    } else {
        $messages.insertAdjacentHTML("afterbegin", `<p class="message left">${cleanedMessage}</p>`);
    }
}
function cleanMessages(string) {
    return string.replace(/</g, "&lt;").replace(/>/g, "&gt;");
}

