const p_not_result_in_search = document.getElementById("p_not_result_in_search");
p_not_result_in_search.style = "display: none";

document.addEventListener('DOMContentLoaded', function() {

    getAllAdvt();

});

document.getElementById('searchButton').addEventListener('click', function() {

    searchAllAdvtByTitle();

});

document.getElementById("list_category").addEventListener("change", function (){

    p_not_result_in_search.style = "display: none";

    const selectedValue = this.value;

    if (selectedValue === ""){

        getAllAdvt();
        return;

    }

    searchAllAdvtByCategory(selectedValue);

})

async function searchAllAdvtByTitle(){

    p_not_result_in_search.style = "display: none";

    const searchTitle = document.getElementById("searchInput").value.trim();

    if (searchTitle === ""){

        await getAllAdvt();
        return;

    }

    const response = await fetch(`api/advt/searchAllAdvtByTitle/${searchTitle}`, {

        method: "GET"

    });

    const result = await response.json();

    if (!result.list || result.list.length === 0){

        p_not_result_in_search.style = "display: inline-block;";

    }

    fetchAdvtList(result);

}

async function getAllAdvt(){

    p_not_result_in_search.style = "display: none";

    const response = await fetch("api/advt", {

        method: "GET",
        headers: {

            "Content-Type": "application/json"

        }

    });

    const result = await response.json();

    fetchAdvtList(result);

}

async function searchAllAdvtByCategory(id){

    const response = await fetch(`/api/advt/searchAllAdvtByCategory/${id}`, {

        method: "GET"

    });

    const result = await response.json();

    if (!result.list || result.list.length === 0){

        p_not_result_in_search.style = "display: inline-block;";

    }

    fetchAdvtList(result);

}

async function fetchAdvtList(advtArray) {

    const currUser = JSON.parse(sessionStorage.getItem("user"));
    const container = document.getElementById('container');
    container.innerHTML = '';

    if (!advtArray || advtArray.length === 0) {
        p_not_result_in_search.style = "display: inline-block";
        return;
    }

    for(let i = 0; i < advtArray.length; i++){

        const newDiv = document.createElement('div');
        newDiv.id = "advt_card";

        let mimeType = getImageMimeType(advtArray[i].photoBase64);
        let src = `data:${mimeType};base64,${advtArray[i].photoBase64}`;

        const pCategory = document.createElement("p");
        pCategory.textContent = "Категория товара: " + advtArray[i].category;
        pCategory.style = "margin-left: 5px"

        const divImg = document.createElement('div');
        divImg.classList.add('container_img');

        const img = document.createElement('img');
        img.src = src;
        img.classList.add('photo_img');

        const title = document.createElement("p");
        title.textContent = advtArray[i].title;
        title.id = "title";

        const cost = document.createElement("p");
        cost.textContent = advtArray[i].cost;
        cost.id = "cost";

        const id_p = document.createElement("p");
        id_p.textContent = advtArray[i].id;
        id_p.id = "id_p";
        id_p.style = "visibility: hidden; margin: 0;";

        const buttonDiv = document.createElement("div");

        const closeButton = document.createElement("button");
        closeButton.textContent = "×";
        closeButton.id = "close_button";

        const editButton = document.createElement("button");
        editButton.textContent = "✎";
        editButton.id = "edit_button";

        const visibleButton = document.createElement("button");
        visibleButton.textContent = "👁️";
        visibleButton.id = "visible_button";

        buttonDiv.classList.add("advt-controls");
        closeButton.classList.add("close_button");
        editButton.classList.add("edit_button");
        visibleButton.classList.add("visible_button");

        if (advtArray[i].completed === false){

            newDiv.classList.add('advt_card');
            divImg.classList.add('container_img');
            img.classList.add('photo_img');
            title.classList.add('photo_title');
            cost.classList.add('photo_title');

        } else {

            newDiv.classList.add('advt_card_not_visible');
            divImg.classList.add('container_img_not_visible');
            img.classList.add('photo_img_not_visible');
            title.classList.add('photo_title_not_visible');
            cost.classList.add('photo_title_not_visible');

        }

        buttonDiv.appendChild(visibleButton);
        buttonDiv.appendChild(editButton);
        buttonDiv.appendChild(closeButton);
        newDiv.appendChild(buttonDiv);
        divImg.appendChild(img);
        newDiv.appendChild(divImg);
        newDiv.appendChild(pCategory);
        newDiv.appendChild(title);
        newDiv.appendChild(cost);
        newDiv.appendChild(id_p);

        if (currUser.role !== "ADMIN"){

            if (currUser.id !== advtArray[i].user_id){

                closeButton.style = "visibility: hidden;";
                editButton.style = "visibility: hidden;";
                visibleButton.style = "visibility: hidden";

                if (advtArray[i].completed === true) {

                    continue;

                }

            }

        }

        container.appendChild(newDiv);

    }
}

function getImageMimeType(base64String) {
    if (base64String.startsWith("/9j/")) return "image/jpeg";
    if (base64String.startsWith("iVBORw0KGgo")) return "image/png";
    if (base64String.startsWith("R0lGOD")) return "image/gif";
    return "image/png"; // По умолчанию
}

