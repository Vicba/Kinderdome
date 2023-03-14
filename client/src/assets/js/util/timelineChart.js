'use strict';

let _globalEventList;

function createTimelineData(eventList) {
    return {
        labels: createLabels(eventList),
        datasets: createTimelineDataset(eventList),
    };
}

function createTimelineDataset(eventList) {
    const standardValue = 0;
    return [{
        data: eventList.map(event => standardValue),
        borderColor: 'rgb(75, 192, 192)'
    }];
}

function createTimelineScales() {
    return {
        x: {
            display: false,
        },
        y: {
            display: true,
            ticks : {
                color: 'rgb(0,0,0)'
            }
        }
    };
}

function createTimeLinePlugin() {
    return {
        legend: {
            display: false,
        },
        tooltip: {
            backgroundColor: 'rgb(128,128,128)',
            titleFont: {
                size: 24,
                weight: 'bold'
            },
            callbacks: {
                title: function (tooltipItem) {
                    return _globalEventList[tooltipItem[0].dataIndex].eventType;
                },
                beforeBody: function (tooltipItem) {
                    return _globalEventList[tooltipItem[0].dataIndex].description;
                }
            }
        }
    };
}

function createTimelineOptions(chartSize, onClickHandler) {
    return {
        interaction: {
            mode: 'point'
        },
        clip: false,
        aspectRatio: 5/chartSize,
        indexAxis: 'y',
        scales: createTimelineScales(),
        elements: {
            point: {
                pointRadius: 7,
                pointHoverRadius: 20,
                pointBackgroundColor: 'rgb(75, 192, 192)',
            }
        },
        onClick: onClickHandler,
        plugins: createTimeLinePlugin()
    };
}

function createTimelineConfig(eventList, onClickHandler) {
    _globalEventList = eventList;
    return {
        type: 'line',
        data: createTimelineData(eventList),
        options: createTimelineOptions(eventList.length, onClickHandler),
    };
}

function getEventFromClick(clickEvent) {
    const chart = clickEvent.chart;
    const points = chart.getElementsAtEventForMode(clickEvent, 'point', {intersect: true}, true);
    if (points.length) {
        const index = points[0].index;
        return _globalEventList[index];
    }
    return null;
}


