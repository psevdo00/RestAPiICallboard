async function reg(event){

    event.preventDefault();

    // Сначала скрываем все ошибки и убираем красные рамки
    document.querySelectorAll('.error-message').forEach(el => {
        el.style.display = 'none';
    });
    document.querySelectorAll('input').forEach(input => {
        input.classList.remove('error');
    });

    const passwordValue = document.getElementById("password").value;
    const passwordRepeatValue = document.getElementById("confirm-password").value;

    if (passwordValue !== passwordRepeatValue){

        const errDivRepeatPassword = document.getElementById("err_passwordRepeat");
        errDivRepeatPassword.textContent = "Пароли не совпадают!";
        errDivRepeatPassword.style.display = 'block';
        return;

    }

    const response = await fetch("/api/user", {

        method: 'POST',
        headers: {

            "Content-Type": "application/json"

        },
        body: JSON.stringify({familyName: document.getElementById("familyName").value, firstName: document.getElementById("firstName").value,
            middleName: document.getElementById("middleName").value, email: document.getElementById("email").value, username: document.getElementById("username").value,
            numPhone: document.getElementById("numPhone").value, password: passwordValue, role: "USER"})

    });

    const result = await response.json();

    switch (response.status){

        case 200: {

            sessionStorage.setItem("user", JSON.stringify(result));
            window.location.href = "/index.html";

        }
            break;

        case 400: {

            print_array(result);

        }
            break;

    }
}

async function auth(event){

    event.preventDefault();

    // Сначала скрываем все ошибки и убираем красные рамки
    document.querySelectorAll('.error-message').forEach(el => {
        el.style.display = 'none';
    });
    document.querySelectorAll('input').forEach(input => {
        input.classList.remove('error');
    });

    const usernameValue = document.getElementById("username").value;
    const passwordValue = document.getElementById("password").value;

    const params = new URLSearchParams();
    params.append('username', usernameValue);
    params.append('password', passwordValue);

    const response = await fetch("/login", {

        method: 'POST',
        headers: {

            "Content-Type": "application/x-www-form-urlencoded"

        },
        body: params

    });

    const result = await response.json();

    switch (response.status){

        case 200: {

            sessionStorage.setItem("user", JSON.stringify(result));
            window.location.href = "/index.html";

        }
            break;

        case 400: {

            print_array(result);

        }
            break;

    }

}

function print_array(result){

    document.querySelectorAll('.error-message').forEach(el => {
        el.style.display = 'none';
    });

    for (const field in result) {
        const errorDiv = document.getElementById(`err_${field}`);
        const inputField = document.getElementById(field);
        if (errorDiv && inputField) {
            errorDiv.textContent = result[field];
            errorDiv.style.display = 'block';
            inputField.classList.add('error');
        }
    }

}

function goReg(){

    window.location.href = "register.html";

}

function goAuth(){

    window.location.href = "login.html";

}