// Получаем canvas и его контекст один раз при загрузке
const canvas = document.getElementById('previewPhoto');
const ctx = canvas?.getContext('2d');

// Основная функция загрузки данных объявления
function loadAdvertisementData() {
    const advtString = localStorage.getItem('currentAdvt');

    if (!advtString) return;

    try {
        const advt = JSON.parse(advtString);

        // Заполняем текстовые поля
        document.getElementById("title").value = advt.title || '';
        document.getElementById("info").value = advt.info || '';
        document.getElementById("cost").value = advt.cost || '';

        // Загружаем изображение, если оно есть
        if (advt.photoBase64 && canvas) {
            loadImageToCanvas(advt.photoBase64);
        }
    } catch (error) {
        console.error('Ошибка при загрузке данных объявления:', error);
    }
}

// Функция загрузки изображения в canvas
function loadImageToCanvas(base64String) {
    if (!canvas || !ctx) return;

    const img = new Image();
    img.onload = function() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Рассчитываем размеры с сохранением пропорций
        const ratio = Math.min(
            canvas.width / img.width,
            canvas.height / img.height
        );
        const newWidth = img.width * ratio;
        const newHeight = img.height * ratio;
        const x = (canvas.width - newWidth) / 2;
        const y = (canvas.height - newHeight) / 2;

        ctx.drawImage(img, x, y, newWidth, newHeight);
    };

    img.onerror = function() {
        console.error('Не удалось загрузить изображение');
    };

    // Добавляем префикс data: если его нет
    const src = base64String.startsWith('data:') ? base64String
        : `data:${getImageMimeType(base64String)};base64,${base64String}`;
    img.src = src;
}

// Обработчик изменения файла
document.getElementById("photoInput")?.addEventListener("change", function(event) {
    const file = event.target.files[0];
    if (!file) return;

    if (!file.type.startsWith("image/")) {
        alert("Пожалуйста, выберите файл изображения");
        return;
    }

    const reader = new FileReader();
    reader.onload = function(e) {
        if (canvas && ctx) {
            loadImageToCanvas(e.target.result.split(',')[1]); // Берем только base64 часть
        }
    };
    reader.onerror = function() {
        alert("Ошибка при чтении файла");
    };
    reader.readAsDataURL(file);
});

// Вспомогательная функция для определения типа изображения
function getImageMimeType(base64String) {
    const signature = base64String.substring(0, 20);
    if (signature.includes('/9j') || signature.includes('FFD8')) return "image/jpeg";
    if (signature.includes('iVBORw') || signature.includes('PNG')) return "image/png";
    if (signature.includes('R0lGOD')) return "image/gif";
    return "image/jpeg"; // По умолчанию
}

// Загружаем данные при старте
document.addEventListener('DOMContentLoaded', loadAdvertisementData);