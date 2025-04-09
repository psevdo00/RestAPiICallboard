async function checkSession(){

    const response = await fetch("api/getSession");

    const result = await response.text()

    alert(result);

}

async function getCurrRoleUser() {

    const response = await fetch("api/getCurrRoleUser");

    return await response.json();

}

async function logOut(){

    const response = await fetch("api/deleteSession", {

        method: "DELETE"

    })

    const result = await response.json();

    switch (response.status){

        case 200: {

            alert(result.message);

            window.location.href = result.newURL;

        }
            break;
        case 400: {

            console.log(result.message);

        }
            break;
        default: alert("Ошибка с запросом на сервер!");

    }

}

