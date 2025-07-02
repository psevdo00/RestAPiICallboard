function openEditProfile() {
    document.getElementById('editProfileModal').style.display = 'block';
}

function closeEditProfile() {
    document.getElementById('editProfileModal').style.display = 'none';
}

// Обработка переключения между разделами
document.querySelectorAll('.personal-menu a').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();

        // Удаляем активный класс у всех пунктов меню
        document.querySelectorAll('.personal-menu li').forEach(item => {
            item.classList.remove('active');
        });

        // Добавляем активный класс к текущему пункту
        this.parentElement.classList.add('active');

        // Скрываем все секции
        document.querySelectorAll('.personal-content section').forEach(section => {
            section.style.display = 'none';
        });

        // Показываем нужную секцию
        const sectionId = this.getAttribute('href').substring(1) + '-section';
        document.getElementById(sectionId).style.display = 'block';
    });
});