async function reg(){

    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;
    const password = document.getElementById("password").value;
    const repeatPassword = document.getElementById("repeatPassword").value;

    const response = await fetch("api/users/regUser", {

        method: 'POST',
        headers: {

            "Content-Type": "application/json"

        },
        body: JSON.stringify({username, email, phone, password, repeatPassword})

    });

    const result = await response.json(); // или response.json(), если ответ в формате JSON
    console.log(result); // Вывод ответа в консоль

    switch (response.status){

        case 200: {

            alert(result.message);

            window.location.href = result.newURL;

        }
        break;
        case 400: {

            const usernameError = document.getElementById("username_error");
            const emailError = document.getElementById("email_error");
            const phoneError = document.getElementById("phone_error");
            const passwordError = document.getElementById("password_error");
            const repeatPasswordError = document.getElementById("repeatPassword_error");

            usernameError.textContent = result.username;
            emailError.textContent = result.email;
            phoneError.textContent = result.phone;
            passwordError.textContent = result.password;
            repeatPasswordError.textContent = result.passwordMatching;

            const pError = document.getElementById("errors");
            pError.textContent = result.message;

        }
        break;
        default: alert("Ошибка с запросом на сервер!");

    }
}

async function auth(){

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch("api/users/authUser", {

        method: 'POST',
        headers: {

            "Content-Type": "application/json"

        },
        body: JSON.stringify({username, password})

    });

    const result = await response.json();
    console.log(result); // Вывод ответа в консоль

    switch (response.status){

        case 200: {

            alert(result.message);

            window.location.href = result.newURL;

        }
            break;
        case 400: {

            const pError = document.getElementById("errors");
            pError.textContent = result.message;

        }
            break;
        default: alert("Ошибка с запросом на сервер!");

    }

}

function goReg(){

    window.location.href = "regUser.html";

}

function goAuth(){

    window.location.href = "authUser.html";

}