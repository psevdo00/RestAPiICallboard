AuthVerification();

async function AuthVerification(){

    const result = await getCurrRoleUser();

    const auth_user = document.getElementById("auth_user");
    const no_auth_user = document.getElementById("no_auth_user");
    const p_username = document.getElementById("username_p");

    if (result.role !== "ADMIN" && result.role !== "USER"){

        auth_user.style = "display: none;";
        no_auth_user.style = "display: inline-block;";

    } else {

        auth_user.style = "display: inline-block;";
        no_auth_user.style = "display: none;";
        p_username.textContent = "Пользователь: " + result.username;

    }

}