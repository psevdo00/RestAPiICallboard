async function createAdvt(){

    const title = document.getElementById("title").value;
    const info = document.getElementById("info").value;
    const cost = document.getElementById("cost").value;
    const category = document.getElementById("category").value;
    const photoInput = document.getElementById("photoInput");
    const filePhoto = photoInput.files[0];

    let photoBase64 = null;

    if (filePhoto) {

        photoBase64 = await convertFileToBase64(filePhoto);

    }

    const response = await fetch("api/advt/createAdvt", {

        method: 'POST',
        headers: {

            "Content-Type": "application/json"

        },
        body: JSON.stringify({title, info, photoBase64, cost, category})

    });

    const result = await response.json(); // или response.json(), если ответ в формате JSON
    console.log(result); // Вывод ответа в консоль

    switch (response.status){

        case 200: {

            alert(result.message);

            window.location.href = result.newURL;

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

function convertFileToBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result.split(',')[1]); // Удаляем префикс data:image/...
        reader.onerror = error => reject(error);
    });
}

