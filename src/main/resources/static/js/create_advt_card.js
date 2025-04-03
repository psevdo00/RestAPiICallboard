async function fetchAdvtList() {
    const response = await fetch("api/advt/getAllAdvt", {

        method: "GET",
        headers: {

            "Content-Type": "application/json"

        }

    });

    const result = await response.json();
    const listAdvt = result.list;

    console.log(listAdvt);

    const container = document.getElementById('container');

    for(let i = 0; i < listAdvt.length; i++){

        const newDiv = document.createElement('div');
        newDiv.classList.add('advt_card');

        let mimeType = getImageMimeType(listAdvt[i].photoBase64);
        let src = `data:${mimeType};base64,${listAdvt[i].photoBase64}`;

        const divImg = document.createElement('div');
        divImg.classList.add('container_img');

        const img = document.createElement('img');
        img.src = src;
        img.classList.add('photo_img');

        const title = document.createElement("p");
        title.textContent = listAdvt[i].title;
        title.classList.add('photo_title');

        const cost = document.createElement("p");
        cost.textContent = listAdvt[i].cost;
        cost.classList.add('photo_title');

        divImg.appendChild(img);
        newDiv.appendChild(divImg);
        newDiv.appendChild(title);
        newDiv.appendChild(cost);

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
