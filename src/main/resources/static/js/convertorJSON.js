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

    const result = await response.json(); // или response.json(), если ответ в формате JSON
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