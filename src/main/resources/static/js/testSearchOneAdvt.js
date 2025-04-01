async function searchAdvt(){

    const id = document.getElementById("id").value;

    const response = await fetch(`api/advt/search?id=${id}`);

    if (!response.ok) {
        throw new Error("Объявление не найдено!");
    }

    const data = await response.json(); // Парсим JSON
    const entity = data.entity; // Извлекаем объект объявления

    let mimeType = getImageMimeType(entity.photoBase64);
    let src = `data:${mimeType};base64,${entity.photoBase64}`;

    document.getElementById("result").innerHTML = `
            <h2>${entity.title}</h2>
            <p>${entity.info}</p>
            <p>Цена: ${entity.cost}</p>
            <p>Категория: ${entity.category}</p>
            <img src="${src}" 
                alt="Фото объявления" 
                style="max-width:400px;">
        `;

}

function getImageMimeType(base64String) {
    if (base64String.startsWith("/9j/")) return "image/jpeg";
    if (base64String.startsWith("iVBORw0KGgo")) return "image/png";
    if (base64String.startsWith("R0lGOD")) return "image/gif";
    return "image/png"; // По умолчанию
}