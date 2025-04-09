document.addEventListener('DOMContentLoaded', function() {

    const container = document.getElementById("container");
    container.addEventListener('click', function (event) {

        if (event.target.id === "close_button") {

            const advtCard = event.target.closest(".advt_card");

            const idElem = advtCard.querySelector('#id_p');
            const id = idElem.textContent;

            console.log("Удаляемое объявление id: ", id);

            deleteAdvt(id);

        }

    });

});

async function deleteAdvt(id){

    const respone = await fetch(`api/advt/deleteAdvt/${id}`, {

        method: "DELETE"

    });

    if (respone.ok){

        alert("Объявление успешно удалено!");
        location.reload();

    } else {

        alert("Ошибка удаления объявления!");

    }

}