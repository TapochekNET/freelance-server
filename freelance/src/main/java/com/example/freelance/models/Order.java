package com.example.freelance.models;

import java.time.LocalDate;

public class Order {
    private Long id;
    private LocalDate creationDate;
    private Product product;
    private User orderer;
    private String content;
    private String  status;
    public Order() {
    }

    public Order(Long id, String content, LocalDate creationDate, Product product, User orderer, String status) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.product = product;
        this.orderer = orderer;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getOrderer() {
        return orderer;
    }

    public void setOrderer(User orderer) {
        this.orderer = orderer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
