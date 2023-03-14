async function initCalling() {
    camToggle();
    micToggle();
    callToggle();
}

function callToggle() {
    document.querySelectorAll(".call-button").forEach(button => {
        button.addEventListener("click", function () {
            toggleHidden("call-button");
            toggleHidden("gif");
        });
    });
}

function micToggle() {
    document.querySelectorAll(".mic-button").forEach(button => {
        button.addEventListener("click", function () {
            toggleHidden("mic-button");
        });
    });
}

function camToggle() {
    const enableCam = document.querySelector('#enable-cam');
    const disableCam = document.querySelector('#disable-cam');

    let stream;

    enableCam.addEventListener('click', () => {
        navigator.mediaDevices
            .getUserMedia({video: true})
            .then(mediaStream => {
                player.srcObject = mediaStream;
                stream = mediaStream;
            });
        toggleHidden("cam-button");
    });

    disableCam.addEventListener('click', () => {
        stream.getTracks().forEach(track => track.stop());
        toggleHidden("cam-button");
        player.src = "";
    });
}

function toggleHidden(type) {
    document.querySelectorAll(`.${type}`).forEach(button => {
        button.classList.toggle("hidden");
    });
}

