let backButton = document.getElementById("back-button")

backButton.addEventListener('click', (event) => {
    event.preventDefault();
    window.location.replace("index.html")
})

let registerButton = document.getElementById("register-button");

registerButton.addEventListener('click', async(event) => {
    event.preventDefault();

    let firstnameValue = document.getElementById("register-firstname").value;
    let lastnameValue = document.getElementById("register-lastname").value;
    let emailValue = document.getElementById("register-email").value;
    let passwordValue = document.getElementById("register-password").value;
    let birthdateValue = document.getElementById("register-birthdate").value;

    // console.log(`Birthdate: ${birthdateValue}`)

    var bdate = new Date(birthdateValue);
    var bmonth = bdate.getUTCMonth();
    var bday = bdate.getUTCDate();

    var zodiacValue;

    if ((bmonth == 0 && bday >= 20) || (bmonth == 1 && bday <= 18)) {
        zodiacValue = "aquarius";
    } else if ((bmonth == 1 && bday >= 19) || (bmonth == 2 && bday <= 20)) {
        zodiacValue = "pisces";
    } else if ((bmonth == 2 && bday >= 21) || (bmonth == 3 && bday <= 19)) {
        zodiacValue = "aries";
    } else if ((bmonth == 3 && bday >= 20) || (bmonth == 4 && bday <= 20)) {
        zodiacValue = "taurus";
    } else if ((bmonth == 4 && bday >= 21) || (bmonth == 5 && bday <= 20)) {
        zodiacValue = "gemini";
    } else if ((bmonth == 5 && bday >= 21) || (bmonth == 6 && bday <= 22)) {
        zodiacValue = "cancer";
    } else if ((bmonth == 6 && bday >= 23) || (bmonth == 7 && bday <= 22)) {
        zodiacValue = "leo";
    } else if ((bmonth == 7 && bday >= 23) || (bmonth == 8 && bday <= 22)) {
        zodiacValue = "virgo";
    } else if ((bmonth == 8 && bday >= 23) || (bmonth == 9 && bday <= 22)) {
        zodiacValue = "libra";
    } else if ((bmonth == 9 && bday >= 23) || (bmonth == 10 && bday <= 21)) {
        zodiacValue = "scorpio";
    } else if ((bmonth == 10 && bday >= 22) || (bmonth == 11 && bday <= 21)) {
        zodiacValue = "sagittarius";
    } else if ((bmonth == 11 && bday >= 22) || (bmonth == 0 && bday <= 19)) {
        zodiacValue = "capricorn";
    }

    let userObject = {
        firstname:firstnameValue,
        lastname:lastnameValue,
        email:emailValue,
        password:passwordValue,
        birthdate:birthdateValue,
        zodiac:zodiacValue
    }

    let json = JSON.stringify(userObject);

    console.log(json);

    try {

        let raw_response = await fetch(`http://localhost:8080/user`, {
            method:"POST",
            body:json
        })

        if (!raw_response.ok) {
            throw new Error(raw_response.status)
        }

        alert("Account successfully created!")
        window.location.replace("index.html")
        
    } catch(error) {
       console.log(error)
    }
})