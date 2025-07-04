package com.psevdo00.RestAPiICallboard.dto.response;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String firstName;
    private String familyName;
    private String middleName;
    private String email;
    private String numPhone;
    private String role;

}
