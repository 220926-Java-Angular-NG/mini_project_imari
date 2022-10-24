let signUpButton = document.getElementById("sign-up");

signUpButton.addEventListener('click', (event) => {
    event.preventDefault();
    window.location.replace("register.html")
});

let loginButton = document.getElementById("login");

loginButton.addEventListener('click', async(event) => {
    event.preventDefault();

    let email1 = document.getElementById("email-sign-in").value;
    let password1 = document.getElementById("password-sign-in").value;

    let loginInfo = {
        email: email1,
        password:password1
    }

    let json = JSON.stringify(loginInfo);

    try {
        const raw_response = await fetch(`http://localhost:8080/login`, {
            method:"POST",
            body:json
        });

        if (!raw_response.ok) {
            throw new Error(raw_response.status)
        }
        
        raw_response.json().then( (data) => {
            console.log(data);
            let loggedInUser = JSON.stringify(data)

            localStorage.setItem("currentUser", loggedInUser)
            console.log(loggedInUser);
        })

        setTimeout( () => {
            window.location.replace("home.html")
        }, 3000 )


    } catch(error) {
        console.log(error)
    }
})