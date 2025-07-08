const p_not_result_in_search = document.getElementById("p_not_result_in_search");
p_not_result_in_search.style = "display: none";

document.addEventListener('DOMContentLoaded', function() {

    getAllAdvertisement();

});

document.getElementById('searchButton').addEventListener('click', function() {

    searchAllAdvtByTitle();

});

document.getElementById("list_category").addEventListener("change", function (){

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

async function getAllAdvertisement(){

    p_not_result_in_search.style = "display: none";

    const response = await fetch("api/advt", {

        method: "GET",

    });

    const result = await response.json();

    fetchAdvtList(result);

}

async function searchAllAdvtByCategory(id){

    p_not_result_in_search.style.display = "none";

    const response = await fetch(`/api/advt?category=${id}`, {

        method: "GET"

    });

    const result = await response.json();

    if (!result || result.length === 0){

        p_not_result_in_search.style = "display: inline-block;";

    }

    fetchAdvtList(result);

}

async function fetchAdvtList(advtArray) {
    const currUser = JSON.parse(sessionStorage.getItem("user"));
    const container = document.getElementById('container');
    container.innerHTML = '';

    const pNotResult = document.getElementById('p_not_result_in_search');
    if (!advtArray || advtArray.length === 0) {
        pNotResult.style.display = "inline-block";
        return;
    } else {
        pNotResult.style.display = "none";
    }

    advtArray.forEach(advt => {
        // Пропускаем скрытые completed объявления для неадминов
        if (advt.completed && (!currUser || currUser.role !== "ADMIN")) return;

        const mimeType = getImageMimeType(advt.photoBase64);
        const src = `data:${mimeType};base64,${advt.photoBase64}`;

        // Логика видимости кнопок
        const showButtons = currUser && (currUser.role === "ADMIN" || currUser.id === advt.user_id);

        // Классы для объявления
        const isVisible = !advt.completed;
        const cardClass = isVisible ? 'advt_card' : 'advt_card_not_visible';
        const imgContainerClass = isVisible ? 'container_img' : 'container_img_not_visible';
        const imgClass = isVisible ? 'photo_img' : 'photo_img_not_visible';
        const titleClass = isVisible ? 'photo_title' : 'photo_title_not_visible';
        const costClass = isVisible ? 'photo_cost' : 'photo_title_not_visible';

        // Создаем карточку через DOM, а не innerHTML +=
        const card = document.createElement('div');
        card.className = cardClass;

        // Заголовок
        const title = document.createElement('h2');
        title.className = titleClass;
        title.textContent = advt.title;
        card.appendChild(title);

        // Меню с кнопками (если нужно)
        if (showButtons) {
            const menuContainer = document.createElement('div');
            menuContainer.className = 'menu-container';

            const menuTrigger = document.createElement('div');
            menuTrigger.className = 'menu-trigger';
            menuTrigger.textContent = '⋮';
            menuContainer.appendChild(menuTrigger);

            const menuDropdown = document.createElement('div');
            menuDropdown.className = 'menu-dropdown';

            const btnView = document.createElement('button');
            btnView.id = 'visible_button';
            btnView.textContent = '👁️ Просмотр';
            menuDropdown.appendChild(btnView);

            const btnEdit = document.createElement('button');
            btnEdit.id = 'edit_button';
            btnEdit.textContent = '✎ Редактировать';
            menuDropdown.appendChild(btnEdit);

            const btnDelete = document.createElement('button');
            btnDelete.id = 'close_button';
            btnDelete.textContent = '× Удалить';
            menuDropdown.appendChild(btnDelete);

            menuContainer.appendChild(menuDropdown);
            card.appendChild(menuContainer);
        }

        // Контейнер с изображением
        const imgContainer = document.createElement('div');
        imgContainer.className = imgContainerClass;

        const img = document.createElement('img');
        img.src = src;
        img.className = imgClass;
        img.alt = advt.title;

        imgContainer.appendChild(img);
        card.appendChild(imgContainer);

        // Категория
        const categoryP = document.createElement('p');
        categoryP.style.marginLeft = '5px';
        categoryP.textContent = `Категория товара: ${advt.category}`;
        card.appendChild(categoryP);

        // Стоимость
        const costP = document.createElement('p');
        costP.className = costClass;
        costP.textContent = advt.cost + " ₽";
        card.appendChild(costP);

        // Скрытый ID для возможного использования
        const idP = document.createElement('p');
        idP.style.visibility = 'hidden';
        idP.style.margin = '0';
        idP.textContent = advt.id;
        card.appendChild(idP);

        container.appendChild(card);
    });
}

function getImageMimeType(base64String) {
    if (base64String.startsWith("/9j/")) return "image/jpeg";
    if (base64String.startsWith("iVBORw0KGgo")) return "image/png";
    if (base64String.startsWith("R0lGOD")) return "image/gif";
    return "image/png"; // По умолчанию
}

