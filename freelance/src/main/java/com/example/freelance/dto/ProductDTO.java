package com.example.freelance.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductDTO {
    private String name;
    private String description;
    private Short type;
    private Integer cost;
    private List<MultipartFile> photos;

    public ProductDTO() {
    }

    public ProductDTO(String name, String description, Short type, Integer cost, List<MultipartFile> photos) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.cost = cost;
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }
}
