let sendToServer;

loadConfig().then(() => {
    initParentComm();
});

function initParentComm() {
    sendToServer = openSocket(loadFromStorage("parentId"));
    initCalling();
    fillSelect();
    document.querySelector("#send").addEventListener('click', sendMessage);
    document.querySelector("#select").addEventListener("change", changeSelected);
}

function changeSelected() {
    const $gif = document.querySelector("#video-player-other img");
    const $selector = document.querySelector("#select").value;
    const $chat = document.querySelector("#chat");
    const $input = document.querySelector("input");
    document.querySelector("#messages").innerHTML = "";
    $input.value = "";
    if($selector === "-1"){
        $gif.src = "../assets/images/nurse.gif";
        chat.classList.remove("unavailable");
        $input.placeholder = "Send a message to the caretaker";
        $input.disabled = false;
    }
    else {
        $gif.src = "../assets/images/baby-jump.gif";
        $chat.classList.add("unavailable");
        $input.placeholder = "Select a caretaker to send a message!";
        $input.disabled = true;
    }
}

function fillSelect() {
    const $select = document.querySelector("#select");
    if (localStorage.getItem('allChildren') !== null) {
        const allChildren = loadFromStorage("allChildren");
        allChildren.push({id: -1, name: "Caretaker"});
        fillSelectOptions(allChildren, $select);
        const childId = loadFromStorage("childId");
        if (childId) {
            $select.value = childId;
        }
    } else {
        fillStorageWithChildren();
    }
    changeSelected();
}

function sendMessage() {
    const $input = document.querySelector('#chat-field');
    const message = $input.value;
    $input.value = "";
    if (!$input.disabled && message !== "") {
        const data = {type: "from_parent", message: message};
        sendToServer(data);
    }
}

function onMessage(message, sender) {
    const $messages = document.querySelector("#messages");
    const cleanedMessage = cleanMessages(message.body);
    if (sender === "parent"){
        $messages.insertAdjacentHTML("afterbegin", `<p class="message right">${cleanedMessage}</p>`);
    } else {
        $messages.insertAdjacentHTML("afterbegin", `<p class="message left">${cleanedMessage}</p>`);
    }
}

function cleanMessages(string) {
    return string.replace(/</g, "&lt;").replace(/>/g, "&gt;");
}
