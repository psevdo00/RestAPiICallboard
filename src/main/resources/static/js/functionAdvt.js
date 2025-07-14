document.addEventListener('DOMContentLoaded', function() {

    const container = document.getElementById("container");
    container.addEventListener('click', function (event) {

        const advtCard = event.target.closest(".advt_card, .advt_card_not_visible");

        const idElem = advtCard.querySelector('#id_p');
        const id = idElem.textContent;

        if (event.target.id === "close_button") {

            console.log("Удаляемое объявление id: ", id);

            deleteAdvt(id);

        } else if (event.target.id === "advt_card" || event.target.id === "title" || event.target.id === "cost" || event.target.id === "divImg" || event.target.id === "img"){

            openFullAdvt(id)

        } else if (event.target.id === "visible_button"){

            hideOrShowAdvt(id);

        } else if (event.target.id === "edit_button"){

            editAdvt(id);

        }

    });

});

async function editAdvt(id) {

    const response = await fetch(`api/advt/search/${id}`, {

        method: "GET"

    });

    const result = await response.json();

    switch (response.status) {

        case 200: {

            console.log(result.message);

            const advt = result.entity;

            localStorage.setItem('currentAdvt', JSON.stringify(advt));

            window.location.href = "editAdvt.html";

        }
            break;
        case 400: {

            alert(result.message);

        }
            break;
        default:
            alert("Ошибка с запросом на сервер!");

    }
}

async function hideOrShowAdvt(id) {

    const response = await fetch(`/api/advt/updateStatusAdvt/${id}`, {

        method: "PUT"

    });

    location.reload();

}

async function deleteAdvt(id){

    const response = await fetch(`api/advt/deleteAdvt/${id}`, {

        method: "DELETE"

    });

    const result = await response.json();

    switch (response.status){

        case 200: {

            alert(result.message);
            location.reload();

        }
        break;
        case 400: {

            alert(result.message);

        }
        break;
        default: alert("Ошибка с запросом на сервер!");

    }

}

async function openFullAdvt(id){

    const response = await fetch(`api/advt/search/${id}`, {

        method: "GET"

    });

    const result = await response.json();

    switch (response.status){

        case 200: {

            console.log(result.message);

            const advt = result.entity;
            const user = result.user;

            localStorage.setItem('currentAdvt', JSON.stringify(advt));
            localStorage.setItem('userAdvt', JSON.stringify(user));

            window.location.href = "advtPage.html";

        }
        break;
        case 400: {

            alert(result.message);

        }
        break;
        default: alert("Ошибка с запросом на сервер!");

    }

}

function goToCreateAdvt(){

    window.location.href = "create.html";

}
