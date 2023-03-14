const CHNL_TO_SERVER = "messages.to.server";
const CHNL_FROM_PARENT = "messages.from.parent.";
const CHNL_FROM_CARETAKER = "messages.from.caretakers";

function openSocket(clientId) {
    const eb = new EventBus(socket);
    function sendMessage(message) {
        message.clientId = clientId;
        eb.publish(CHNL_TO_SERVER, message);
    }

    eb.onopen = function() {
        eb.registerHandler(CHNL_FROM_PARENT, (error, message) => {
            (!error) ? onMessage(message, "parent") : console.error(error);
        });
        eb.registerHandler(CHNL_FROM_CARETAKER, (error, message) => {
            (!error) ? onMessage(message, "caretaker") : console.error(error);
        });
    };
    return sendMessage;
}
