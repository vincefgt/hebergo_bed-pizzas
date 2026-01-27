var map = L.map('map').setView([48.85, 2.35], 13);

var Stadia_OSMBright = L.tileLayer('https://tiles.stadiamaps.com/tiles/osm_bright/{z}/{x}/{y}{r}.{ext}', {
    minZoom: 0,
    maxZoom: 20,
    attribution: '&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; ' +
                        '<a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; ' +
                        '<a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    ext: 'png'
});

Stadia_OSMBright.addTo(map);