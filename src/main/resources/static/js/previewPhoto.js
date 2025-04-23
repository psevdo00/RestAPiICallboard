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

            // Рассчитываем новые размеры с сохранением пропорций
            const canvasRatio = canvas.width / canvas.height;
            const imgRatio = img.width / img.height;

            let drawWidth, drawHeight, offsetX = 0, offsetY = 0;

            if (imgRatio > canvasRatio) {
                // Ширина изображения ограничивает
                drawWidth = canvas.width;
                drawHeight = canvas.width / imgRatio;
                offsetY = (canvas.height - drawHeight) / 2;
            } else {
                // Высота изображения ограничивает
                drawHeight = canvas.height;
                drawWidth = canvas.height * imgRatio;
                offsetX = (canvas.width - drawWidth) / 2;
            }

            ctx.drawImage(img, offsetX, offsetY, drawWidth, drawHeight);
        }
        img.src = URLPhoto;

    }

    reader.readAsDataURL(filePhoto);

})

