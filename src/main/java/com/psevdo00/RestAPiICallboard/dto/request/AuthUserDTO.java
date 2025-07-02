package com.psevdo00.RestAPiICallboard.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthUserDTO {

    @NotBlank(message = "Это поле не должно быть пустым!")
    private String email;
    @NotBlank(message = "Это поле не должно быть пустым!")
    private String password;

}
