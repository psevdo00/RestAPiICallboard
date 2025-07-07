package com.psevdo00.RestAPiICallboard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Base64;

@Entity
@Data
@Table(name = "advt_entity")
public class AdvertisementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String info;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    private int cost;
    private Boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    public String getPhoto() {

        if (this.photo != null){

            return Base64.getEncoder().encodeToString(this.photo);

        }

        return null;

    }

    public Long getIdUser(){
        return user.getId();
    }
}
