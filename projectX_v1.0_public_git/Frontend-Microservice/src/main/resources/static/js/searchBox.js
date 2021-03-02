var artists = []
var artistsNameCollection = document.getElementsByClassName('artists_name');
var artistsUsernameCollection = document.getElementsByClassName('artists_username');

for(var i = 0; i < artistsNameCollection.length; i++) {
    var artist = {};
    artist.first_name = artistsNameCollection[i].innerText;
    artist.username = artistsUsernameCollection[i].innerText;
    artist.link = "http://localhost:8080/artist/"+artist.username;
    artists.push(artist);
}

console.log(artists)


const searchInput = document.querySelector('.search-input');
const suggestionsPanel = document.querySelector('.suggestions');

searchInput.addEventListener('keyup', function() {
    const input = searchInput.value;
    suggestionsPanel.innerHTML = '';
    const suggestions = artists.filter(function(artist) {
    console.log(artist)
        return artist.first_name.toLowerCase().includes(input.toLowerCase());
    });
    suggestions.forEach(function(suggested) {
        const div = document.createElement('div');
        div.innerHTML = suggested.first_name;
        div.onclick = function () {
                    window.location = suggested.link;
                }
        suggestionsPanel.appendChild(div);
    });
    if (input == '') { //da se lista svih artista ne prikazuje kad izbrisemo slova
            suggestionsPanel.innerHTML = '';
        }
});


/*
    icon.onclick=()=> {
            weblink="https:www.google.com/search?q"+userdata; */
