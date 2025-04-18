document.addEventListener('DOMContentLoaded', function() {

    const container = document.getElementById("container");
    container.addEventListener('click', function (event) {

        if (event.target.id === "close_button") {

            const advtCard = event.target.closest(".advt_card");

            const idElem = advtCard.querySelector('#id_p');
            const id = idElem.textContent;

            console.log("Удаляемое объявление id: ", id);

            deleteAdvt(id);

        } else if (event.target.id === "advt_card" || event.target.id === "title" || event.target.id === "cost" || event.target.id === "divImg" || event.target.id === "img"){

            const advtCard = event.target.closest(".advt_card");

            const idElem = advtCard.querySelector('#id_p');
            const id = idElem.textContent;

            openFullAdvt(id)

        }

    });

});

async function deleteAdvt(id){

    const response = await fetch(`api/advt/deleteAdvt/${id}`, {

        method: "DELETE"

    });

    if (response.ok){

        alert("Объявление успешно удалено!");
        location.reload();

    } else {

        alert("Ошибка удаления объявления!");

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

            localStorage.setItem('currentAdvt', JSON.stringify(advt));

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
