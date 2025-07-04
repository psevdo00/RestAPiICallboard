package com.psevdo00.RestAPiICallboard.dto.response;

public class CategoryResponse {

    private Long id;
    private String name;
    private Long idParent;

    public CategoryResponse(Long id, String name, Long idParent) {
        this.id = id;
        this.name = name;
        this.idParent = idParent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdParent() {
        return idParent;
    }

    public void setIdParent(Long idParent) {
        this.idParent = idParent;
    }

}
