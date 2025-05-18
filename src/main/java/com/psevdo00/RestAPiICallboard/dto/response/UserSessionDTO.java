package com.psevdo00.RestAPiICallboard.dto.response;

import com.psevdo00.RestAPiICallboard.enums.UserRoleEnum;

public class UserSessionDTO {

    private Long id;
    private String username;
    private UserRoleEnum role;

    public UserSessionDTO(Long id, String username, UserRoleEnum role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
}
