package com.example.freelance.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Product {
    private Long id;
    private String name;
    private String description;
    private String type;
    private Integer purchases;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private User author;
    private Integer cost;
    private List<String> photoLinks;

    public Product() {
    }

    public Product(Long id, String name, String description, String type, Integer purchases, LocalDate creationDate, LocalDate updateDate, User author, Integer cost, List<String> photoLinks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.purchases = purchases;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.author = author;
        this.cost = cost;
        this.photoLinks = photoLinks;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPurchases() {
        return purchases;
    }

    public void setPurchases(Integer purchases) {
        this.purchases = purchases;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<String> getPhotoLinks() {
        return photoLinks;
    }

    public void setPhotoLinks(List<String> photoLinks) {
        this.photoLinks = photoLinks;
    }
}
