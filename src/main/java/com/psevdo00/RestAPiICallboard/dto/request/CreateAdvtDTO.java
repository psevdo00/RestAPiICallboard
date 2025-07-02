package com.psevdo00.RestAPiICallboard.dto.request;

import lombok.Data;

import java.util.Base64;

@Data
public class CreateAdvtDTO {

    private String title;
    private String info;
    private String photoBase64;
    private int cost;
    private Long categoryId;

    public byte[] getPhotoBase64() {

        this.photoBase64 = photoBase64;

        if (photoBase64 != null){

            byte[] photoByte = Base64.getDecoder().decode(photoBase64);
            return photoByte;

        }

        return null;

    }

}
