package com.psevdo00.RestAPiICallboard.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.psevdo00.RestAPiICallboard.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    private String firstName;
    private String familyName;
    private String middleName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String numPhone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AdvertisementEntity> advts;

    private UserRoleEnum role;

}

