package com.example.freelance.dto;

import org.springframework.web.multipart.MultipartFile;

public class RegDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private String about;
    private String instLink;
    private String vkLink;

    public RegDTO() {
    }

    public RegDTO(String firstName, String lastName, String email, String password, String photo,String about,String instLink, String vkLink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.about = about;
        this.instLink = instLink;
        this.vkLink = vkLink;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getInstLink() {
        return instLink;
    }

    public void setInstLink(String instLink) {
        this.instLink = instLink;
    }

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
