const advtString = localStorage.getItem('currentAdvt');
const userString = localStorage.getItem('userAdvt');
const advt = JSON.parse(advtString);
const user = JSON.parse(userString);

localStorage.removeItem('currentAdvt');

const container = document.getElementById("advt");
const pageTitle = document.getElementById("idTitlePage");
pageTitle.textContent = "Объявление: " + advt.title;

const h2Title = document.createElement("h2");
h2Title.textContent = advt.title;

let mimeType = getImageMimeType(advt.photoBase64);
let src = `data:${mimeType};base64,${advt.photoBase64}`;

const containerImgAndUser = document.createElement("div");
containerImgAndUser.style = "display: flex;";

const divContact = document.createElement("div");
divContact.style = "margin-left: 20px"

const pUsername = document.createElement("p");
pUsername.textContent = "Продавец: " + user.username;

const pEmail = document.createElement("p");
pEmail.textContent = "Почта: " + user.email;

const pPhone = document.createElement("p");
pPhone.textContent = "Телефон: " + user.phone;

const divImg = document.createElement('div');
divImg.classList.add('img_advt');

divContact.appendChild(pUsername);
divContact.appendChild(pEmail);
divContact.appendChild(pPhone)

const img = document.createElement('img');
img.src = src;
img.classList.add('img_advt_img');

divImg.appendChild(img);

containerImgAndUser.appendChild(divImg)
containerImgAndUser.appendChild(divContact)

const pInfoAdvt = document.createElement("p");
pInfoAdvt.textContent = advt.info;

const pCost = document.createElement("p");
pCost.textContent = advt.cost;

const pCategory = document.createElement("p");
pCategory.textContent = "Категория товара: " + advt.category;

container.appendChild(h2Title);
container.appendChild(containerImgAndUser);
container.appendChild(pCategory);
container.appendChild(pCost);
container.appendChild(pInfoAdvt);

function getImageMimeType(base64String) {
    if (base64String.startsWith("/9j/")) return "image/jpeg";
    if (base64String.startsWith("iVBORw0KGgo")) return "image/png";
    if (base64String.startsWith("R0lGOD")) return "image/gif";
    return "image/png"; // По умолчанию
}
