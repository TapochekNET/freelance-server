package com.example.freelance.dto;

import org.springframework.web.multipart.MultipartFile;

public class OrderDTO {
    private Long orderId;
    private Long workerId;
    private int statusId;
    private MultipartFile file;

    public OrderDTO() {
    }

    public OrderDTO(Long orderId, Long workerId, int statusId, MultipartFile file) {
        this.orderId = orderId;
        this.workerId = workerId;
        this.statusId = statusId;
        this.file = file;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
