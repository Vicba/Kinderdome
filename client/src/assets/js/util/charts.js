'use strict';

function createConfig(eventList, dataType) {
    return {
        type: 'line',
        data: createData(eventList, dataType),
        options: createBodyTempOptions(),
    };
}

function createData(eventList, dataType) {
    return {
        labels: createLabels(eventList),
        datasets: createDataset(eventList, dataType),
    };
}

function createLabels(eventList) {
    return eventList.map(event => event.dateTime.split(" ")[1]);
}

function createDataset(eventList, dataType) {
    return [{
        data: eventList.map(event => event[dataType]),
        borderColor: 'rgb(234, 75, 99)'
    }];
}

function createBodyTempOptions() {
    return {
        maintainAspectRatio: false,
        clip: false,
        scales: {
            x: scale,
            y: scale,
        },
        elements: {
            point: {
                pointRadius: 7,
                pointHoverRadius: 15,
                pointBackgroundColor: 'rgb(234, 75, 99)',
            },
        },
        onClick: getEventFromClick,
        plugins: {
            legend: {
                display: false,
            },
        }
    };
}

const scale = {
    ticks : {
        color: 'rgb(0,0,0)'
    },
    grid: {
        display: false
    }
};
