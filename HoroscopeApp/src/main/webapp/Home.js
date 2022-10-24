let currentUser = localStorage.getItem("currentUser");
currentUser = JSON.parse(currentUser);
console.log(currentUser);
console.log(localStorage);

let welcomeLabel = document.getElementById("welcome-label");
welcomeLabel.innerHTML = `Welcome ${currentUser.firstname}! You're zodiac sign is ${currentUser.zodiac}.`

async function getHoroscope() {
    
    let horoscopeLabel = document.getElementById("horoscope-label");

    try {
        let raw_response = await fetch(`http://sandipbgt.com/theastrologer/api/horoscope/${currentUser.zodiac}/today`);

        if (!raw_response.ok) {
            alert(`Error Status: ${raw_response.status}`)
        }

        const json_data = await raw_response.json();

        horoscopeLabel.innerHTML = json_data.horoscope;
        console.log(json_data);

    } catch (error) {
        console.log(error);
    }
}
  
getHoroscope();

let moodLabel = document.getElementById("mood-label");
let requestButton = document.getElementById("request-button");

requestButton.addEventListener('click', async(event) => {
    event.preventDefault();

    try {
        let raw_response = await fetch(`http://sandipbgt.com/theastrologer/api/horoscope/${currentUser.zodiac}/today`);

        if (!raw_response.ok) {
            alert(`Error Status: ${raw_response.status}`)
        }

        const json_data = await raw_response.json();

        moodLabel.innerHTML = json_data.meta.mood;

        let userObject = {
            id:currentUser.id,
            mood:json_data.meta.mood
        }

        let json = JSON.stringify(userObject);

        let response = await fetch(`http://localhost:8080/user`, {
            method:"PUT",
            body:json
        })

        if (!response.ok) {
            throw new Error(raw_response.status)
        }

    } catch (error) {
        console.log(error);
    }
})