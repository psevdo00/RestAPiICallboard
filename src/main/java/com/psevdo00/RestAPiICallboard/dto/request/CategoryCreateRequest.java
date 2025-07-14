package com.psevdo00.RestAPiICallboard.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryCreateRequest {

    @NotBlank(message = "Это поле не может быть пустым!")
    private String name;
    private Long id_parent;

}
