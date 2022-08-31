package com.example.freelance.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

public class User implements UserDetails {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private String avatarURL;
    private String status;
    private List<Order> orders = new ArrayList<>();
    private List<Order> purchases = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private Set<Role> roles = new HashSet<>();
    private String activationCode;
    private int ordersCount;
    private int balance;
    private LocalDate date;
    private String about;
    private String instLink;
    private String vkLink;
    @JsonIgnore
    private int complaintCount;

    public User(String firstName, String lastName, String email, String password, String avatarURL, String status, List<Order> orders, List<Product> products, Set<Role> roles,List<Order> purchases, String activationCode, int balance, LocalDate date, int complaintCount,String about,String instLink, String vkLink, int ordersCount) {
        this.ordersCount = ordersCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.avatarURL = avatarURL;
        this.purchases = purchases;
        this.status = status;
        this.orders = orders;
        this.products = products;
        this.roles = roles;
        this.activationCode = activationCode;
        this.balance = balance;
        this.date = date;
        this.complaintCount = complaintCount;
        this.about = about;
        this.instLink = instLink;
        this.vkLink = vkLink;
    }

    public User(){

    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return !status.equals("BANNED");
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String  getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> role) {
        this.roles=role;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getComplaintCount() {
        return complaintCount;
    }

    public void setComplaintCount(int complaintCount) {
        this.complaintCount = complaintCount;
    }

    @Override
    public String toString() {
        return "name: "+this.firstName+" email: "+ this.email+ " password: "+this.password ;
    }

    public List<Order> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Order> purchases) {
        this.purchases = purchases;
    }
}
