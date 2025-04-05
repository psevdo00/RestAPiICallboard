package com.psevdo00.RestAPiICallboard.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AuthUserDTO {

    @NotBlank(message = "Поле логина не должно быть пустым!")
    private String username;
    @NotBlank(message = "Поле пароля не должно быть пустым!")
    private String password;

    public AuthUserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
