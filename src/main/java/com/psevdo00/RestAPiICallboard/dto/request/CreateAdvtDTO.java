package com.psevdo00.RestAPiICallboard.dto.request;

import java.util.Base64;

public class CreateAdvtDTO {

    private String title;
    private String info;
    private String photoBase64;
    private String cost;
    private String category;

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

    public byte[] getPhotoBase64() {

        this.photoBase64 = photoBase64;

        if (photoBase64 != null){

            byte[] photoByte = Base64.getDecoder().decode(photoBase64);
            return photoByte;

        }

        return null;

    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
