async function createAdvt(event){

    event.preventDefault();

    const title = document.getElementById("title").value;
    const info = document.getElementById("info").value;
    const cost = document.getElementById("cost").value;
    const categoryId = document.getElementById("list_category").value;
    const photoInput = document.getElementById("photoInput");

    const filePhotoValue = photoInput.files[0];

    let photoBase64 = null;

    if (filePhotoValue) {

        photoBase64 = await convertFileToBase64(filePhotoValue);

    }

    const response = await fetch("api/advt/createAdvt", {

        method: 'POST',
        headers: {

            "Content-Type": "application/json"

        },
        credentials: 'include',
        body: JSON.stringify({title, info, photoBase64, cost, categoryId})

    });

    const result = await response.json();

    switch (response.status){

        case 200: {

            alert(result.message);

            window.location.href = result.newURL; // переход на главную страницу

        }
        break;
        case 400: {

            const pError = document.getElementById("errors");
            pError.textContent = result.message;

        }
        break;
        default: alert("Ошибка с запросом на сервер!");

    }

}

function convertFileToBase64(file) { // преобразование в строку Base64 для сохранения на сервере
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result.split(',')[1]); // Удаляем префикс data:image/...
        reader.onerror = error => reject(error);
    });
}

