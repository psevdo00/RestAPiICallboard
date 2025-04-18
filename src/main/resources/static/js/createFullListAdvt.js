async function fetchAdvtList() {
    const response = await fetch("api/advt/getAllAdvt", {

        method: "GET",
        headers: {

            "Content-Type": "application/json"

        }

    });

    const result = await response.json();
    const listAdvt = result.list; // список всех объявлений

    const currUser = await getCurrRoleUser(); // получение роли и id текущего пользователя

    const container = document.getElementById('container');

    for(let i = 0; i < listAdvt.length; i++){

        const newDiv = document.createElement('div');
        newDiv.classList.add('advt_card');
        newDiv.id = "advt_card";

        let mimeType = getImageMimeType(listAdvt[i].photoBase64);
        let src = `data:${mimeType};base64,${listAdvt[i].photoBase64}`;

        const divImg = document.createElement('div');
        divImg.classList.add('container_img');
        divImg.id = "divImg";

        const img = document.createElement('img');
        img.src = src;
        img.classList.add('photo_img');
        img.id = "img";

        const title = document.createElement("p");
        title.textContent = listAdvt[i].title;
        title.classList.add('photo_title');
        title.id = "title";

        const cost = document.createElement("p");
        cost.textContent = listAdvt[i].cost;
        cost.classList.add('photo_title');
        cost.id = "cost";

        const id_p = document.createElement("p");
        id_p.textContent = listAdvt[i].id;
        id_p.id = "id_p";
        id_p.style = "visibility: hidden; margin: 0;";

        const buttonDiv = document.createElement("div");
        buttonDiv.classList.add("div_button");

        const closeButton = document.createElement("button");
        closeButton.textContent = "×";
        closeButton.id = "close_button";
        closeButton.classList.add("close_button");

        const editButton = document.createElement("button");
        editButton.textContent = "✎";
        editButton.id = "edit_button";
        editButton.classList.add("edit_button");

        if (currUser.role != "ADMIN"){

            if (currUser.id_user != listAdvt[i].user_id){

                closeButton.style = "visibility: hidden;";
                editButton.style = "visibility: hidden;"

            }

        }

        buttonDiv.appendChild(editButton);
        buttonDiv.appendChild(closeButton);
        newDiv.appendChild(buttonDiv);
        divImg.appendChild(img);
        newDiv.appendChild(divImg);
        newDiv.appendChild(title);
        newDiv.appendChild(cost);
        newDiv.appendChild(id_p);

        container.appendChild(newDiv);

    }
}

fetchAdvtList();

function getImageMimeType(base64String) {
    if (base64String.startsWith("/9j/")) return "image/jpeg";
    if (base64String.startsWith("iVBORw0KGgo")) return "image/png";
    if (base64String.startsWith("R0lGOD")) return "image/gif";
    return "image/png"; // По умолчанию
}
