document.addEventListener('DOMContentLoaded', function() {
    checkAuthState();
});

function checkAuthState() {
    const userData = sessionStorage.getItem("user");

    const nav = document.getElementById("nav");
    const authUser = document.getElementById("auth_user");
    const noAuthUser = document.getElementById("no_auth_user");

    if (userData) {
        const user = JSON.parse(userData);
        if (user.role === "USER" || user.role === "ADMIN") {
            nav.style.display = "flex";
            authUser.style.display = "block";
            noAuthUser.style.display = "none";
            document.getElementById("username_p").textContent = user.username || "";
            return;
        }
    }

    nav.style.display = "none";
    authUser.style.display = "none";
    noAuthUser.style.display = "flex";
}

function logout(){

    sessionStorage.clear();
    location.reload(true);

}

