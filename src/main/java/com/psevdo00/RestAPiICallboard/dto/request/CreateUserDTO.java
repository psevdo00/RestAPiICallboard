package com.psevdo00.RestAPiICallboard.dto.request;


import jakarta.validation.constraints.*;

public class CreateUserDTO {

    @NotBlank(message = "Поле логина не должно быть пустым")
    @Size(min = 3, max = 100, message = "Ваше имя должно быть не менее 3 символов")
    private String username;

    @NotBlank(message = "Поле почты не должно быть пустым")
    @Email(message = "Некорректный адрес электронной почты")
    private String email;

    @NotBlank(message = "Поле пароля не должно быть пустым")
    @Size(min = 6, max = 100, message = "Пароль должен быть динной не менее 6 символов")
    private String password;

    @NotBlank(message = "Поле с номером телефона не должно быть пустым")
    @Pattern(regexp = "^\\+\\d{11}$", message = "Некорректная форма записи номера телефона")
    private String phone;

    @NotBlank(message = "Введите палоль повторно!")
    private String repeatPassword;

    @AssertTrue(message = "Пароли не совпадают!")
    public boolean isPasswordMatching(){

        return password != null && password.equals(repeatPassword);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
