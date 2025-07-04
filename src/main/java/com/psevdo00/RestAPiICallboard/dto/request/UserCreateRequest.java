package com.psevdo00.RestAPiICallboard.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserCreateRequest {

    @NotBlank(message = "Это поле не может быть пустым!")
    @Size(min = 4, message = "Логин должен содержать минимум 4 символа")
    private String username;

    @NotBlank(message = "Это поле не может быть пустым!")
    private String firstName;

    @NotBlank(message = "Это поле не может быть пустым!")
    private String familyName;

    private String middleName;

    @NotBlank(message = "Это поле не может быть пустым!")
    private String password;

    @NotBlank(message = "Это поле не может быть пустым!")
    @Email(message = "Введите корректную почту")
    private String email;

    @Pattern(regexp = "^$|^\\+7\\d{10}$", message = "Номер телефона должен в формате +7XXXXXXXXXX")
    private String numPhone;

    private String role;

}
