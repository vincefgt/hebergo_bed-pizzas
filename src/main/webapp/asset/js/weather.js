
const API_KEY = "ba61b7dcff77e6e1f82fc8d1d1787c0e";

function searchCity() {
    let city = $("#destinationInput").val().trim();
    if (city !== "") {
        getWeather(city);
    }
}

$("#searchField").on("click", searchCity);

/*

$("#destinationInput").on("keypress", function (e) {
    if (e.which === 13) {
        searchCity();
    }
});


*/


function getWeather(city) {
    let url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&lang=fr&appid=${API_KEY}`;

    $.get(url)
        .done(function (data) {
            let temp = Math.round(data.main.temp);
            let desc = data.weather[0].description;
            let icon = data.weather[0].icon;
            let iconUrl = `https://openweathermap.org/img/wn/${icon}@2x.png`;

            $("#meteo-ville").text(data.name);
            $("#meteo-desc").text(desc);
            $("#meteo-temp").text(temp + "°C");
            $("#meteo-icon").attr("src", iconUrl);

            // Carte
            let lat = data.coord.lat;
            let lon = data.coord.lon;

            map.setView([lat, lon], 10);

            if (marker) map.removeLayer(marker);

            marker = L.marker([lat, lon]).addTo(map)
                .bindPopup(`${data.name}<br>${temp}°C<br>${desc}`)
                .openPopup();
        })
        .fail(function () {
            alert("Ville non trouvée");
        });
}

// Charger Paris par défaut au chargement de la page
$(document).ready(function() {
    getWeather("Paris");
});