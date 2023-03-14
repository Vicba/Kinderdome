'use strict';

let _markers;

function createMap(){
    return new ol.Map({
        controls: [],
        interactions: [],
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            }),
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([3.214380,51.191988]),
            zoom: 19
        })
    });
}

function createMarkers(eventList, type, map) {
    const longLatData = eventList.map(event => [event.location.latitude, event.location.longitude]);

    const markers = longLatData.map(coordinate => new ol.Feature({
        type: 'marker',
        geometry: new ol.geom.Point(ol.proj.fromLonLat(coordinate))
    }));

    const markerVectors = new ol.source.Vector({
        features: markers
    });

    _markers = new ol.layer.Vector({
        source: markerVectors,
        className: type,
        style: new ol.style.Style({
            image: new ol.style.Icon({
                src: `../assets/images/markers/${type}.png`,
                anchor: [0.5, 1]
            })
        })
    });
    map.addLayer(_markers);
}
