package com.psevdo00.RestAPiICallboard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Base64;

@Entity
public class AdvtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String info;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;
    private String cost;
    private Boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhoto() {

        if (this.photo != null){

            return Base64.getEncoder().encodeToString(this.photo);

        }

        return null;

    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public CategoryEntity getCategory() { return category; }

    public void setCategory(CategoryEntity category) { this.category = category; }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getIdUser(){
        return user.getId();
    }
}
