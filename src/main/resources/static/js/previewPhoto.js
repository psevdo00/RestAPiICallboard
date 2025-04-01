const canvas = document.getElementById("previewPhoto");
const ctx = canvas.getContext("2d");

ctx.fillStyle = "#999999";
ctx.font = "20px Arial";
ctx.textAlign = 'center';
ctx.textBaseline = 'middle';

ctx.fillText("Загрузите фото для предварительного просмотра", canvas.width/2, canvas.height/2);

document.getElementById("photoInput").addEventListener("change", function(event){

    const filePhoto = event.target.files[0];

    if (!filePhoto.type.startsWith("image/")){

        console.log("Выбранный файл не изображение!");
        return;

    }

    const reader = new FileReader();

    reader.onload = function(e) {

        const URLPhoto = e.target.result;

        const img = new Image();
        img.onload = function(){

            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

        }
        img.src = URLPhoto;

    }

    reader.readAsDataURL(filePhoto);

})