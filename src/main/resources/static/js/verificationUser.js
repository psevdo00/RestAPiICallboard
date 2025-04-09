AuthVerification();

async function AuthVerification(){

    const result = await getCurrRoleUser();

    const buttonCreateAdvt = document.getElementById("createAdvt");
    const buttonLogOut = document.getElementById("logOut");
    const buttonSingIn = document.getElementById("singIn");
    const buttonSingUp = document.getElementById("singUp");

    if (result.role != "ADMIN" && result.role != "USER"){

        buttonCreateAdvt.style = "display: none;";
        buttonLogOut.style = "display: none;";
        buttonSingIn.style = "display: inline-block;";
        buttonSingUp.style = "display: inline-block;";

    } else {

        buttonCreateAdvt.style = "display: inline-block;";
        buttonLogOut.style = "display: inline-block;";
        buttonSingIn.style = "display: none;";
        buttonSingUp.style = "display: none;";

    }

}