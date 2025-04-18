const advtString = localStorage.getItem('currentAdvt');
const advt = JSON.parse(advtString);

localStorage.removeItem('currentAdvt');

const container = document.getElementById("advt");
const pageTitle = document.getElementById("idTitlePage");
pageTitle.textContent = "Объявление: " + advt.title;

const h2Title = document.createElement("h2");
h2Title.textContent = advt.title;

let mimeType = getImageMimeType(advt.photoBase64);
let src = `data:${mimeType};base64,${advt.photoBase64}`;

const divImg = document.createElement('div');
//divImg.classList.add('container_img');

const img = document.createElement('img');
img.src = src;
//img.classList.add('photo_img');

divImg.appendChild(img);

const pInfoAdvt = document.createElement("p");
pInfoAdvt.textContent = advt.info;

const pCost = document.createElement("p");
pCost.textContent = advt.cost;

const pCategory = document.createElement("p");
pCategory.textContent = advt.category;

container.appendChild(h2Title);
container.appendChild(divImg);
container.appendChild(pCategory);
container.appendChild(pCost);
container.appendChild(pInfoAdvt);

function getImageMimeType(base64String) {
    if (base64String.startsWith("/9j/")) return "image/jpeg";
    if (base64String.startsWith("iVBORw0KGgo")) return "image/png";
    if (base64String.startsWith("R0lGOD")) return "image/gif";
    return "image/png"; // По умолчанию
}
