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
        p_not_result_in_search.style.display = "inline-block";
        return;
    }

    for (let i = 0; i < advtArray.length; i++) {
        // Пропускаем скрытые completed объявления для неадминов
        if (advtArray[i].completed === true && (!currUser || currUser.role !== "ADMIN")) {
            continue;
        }

        let mimeType = getImageMimeType(advtArray[i].photoBase64);
        let src = `data:${mimeType};base64,${advtArray[i].photoBase64}`;

        // Логика видимости кнопок
        let showButtons = false;
        if (currUser) {
            if (currUser.role === "ADMIN") {
                showButtons = true;
            } else if (currUser.id === advtArray[i].user_id) {
                showButtons = true;
            }
        }

        // Классы для объявления (видимые / не видимые)
        let cardClass = advtArray[i].completed === false ? 'advt_card' : 'advt_card_not_visible';
        let imgContainerClass = advtArray[i].completed === false ? 'container_img' : 'container_img_not_visible';
        let imgClass = advtArray[i].completed === false ? 'photo_img' : 'photo_img_not_visible';
        let titleClass = advtArray[i].completed === false ? 'photo_title' : 'photo_title_not_visible';
        let costClass = advtArray[i].completed === false ? 'photo_title' : 'photo_title_not_visible';

        container.innerHTML += `
            <div id="advt_card" class="${cardClass}">
                <div class="advt-controls">
                    <button id="visible_button" class="visible_button" style="visibility: ${showButtons ? 'visible' : 'hidden'};">👁️</button>
                    <button id="edit_button" class="edit_button" style="visibility: ${showButtons ? 'visible' : 'hidden'};">✎</button>
                    <button id="close_button" class="close_button" style="visibility: ${showButtons ? 'visible' : 'hidden'};">×</button>
                </div>
                <div class="${imgContainerClass}">
                    <img src="${src}" class="${imgClass}">
                </div>
                <p style="margin-left: 5px;">Категория товара: ${advtArray[i].category}</p>
                <p id="title" class="${titleClass}">${advtArray[i].title}</p>
                <p id="cost" class="${costClass}">${advtArray[i].cost}</p>
                <p id="id_p" style="visibility: hidden; margin: 0;">${advtArray[i].id}</p>
            </div>
        `;
    }
}

function getImageMimeType(base64String) {
    if (base64String.startsWith("/9j/")) return "image/jpeg";
    if (base64String.startsWith("iVBORw0KGgo")) return "image/png";
    if (base64String.startsWith("R0lGOD")) return "image/gif";
    return "image/png"; // По умолчанию
}

