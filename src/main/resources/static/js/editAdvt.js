const advtStringEntity = localStorage.getItem('currentAdvt');

async function updateAdvt(){

    const advt = JSON.parse(advtStringEntity);

    localStorage.removeItem('currentAdvt');

    const title = document.getElementById("title").value;
    const info = document.getElementById("info").value;
    const cost = document.getElementById("cost").value;
    const categoryId = document.getElementById("list_category").value;
    const photoInput = document.getElementById("photoInput");

    let photoBase64 = advt.photoBase64; // По умолчанию используем старое фото

    // Если выбран новый файл, конвертируем его
    if (photoInput.files[0]) {

        photoBase64 = await convertFileToBase64(photoInput.files[0]);

    }

    const id = advt.id;

    const response = await fetch(`/api/advt/editAdvt/${id}`, {

        method: 'PUT',
        headers: {

            "Content-Type": "application/json"

        },
        body: JSON.stringify({title, info, photoBase64, cost, categoryId})

    });

    const result = await response.json();

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
        reader.onload = () => resolve(reader.result.split(',')[1]);
        reader.onerror = error => reject(error);
    });
}

