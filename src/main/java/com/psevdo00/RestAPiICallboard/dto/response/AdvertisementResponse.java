package com.psevdo00.RestAPiICallboard.dto.response;

import lombok.Data;

@Data
public class AdvertisementResponse {

    private Long id;
    private String title;
    private String info;
    private String photoBase64;
    private int cost;
    private Boolean completed;
    private String category;
    private Long user_id;

}
