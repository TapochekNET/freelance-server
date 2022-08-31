package com.example.freelance.controllers;

import com.example.freelance.dto.OrderDTO;
import com.example.freelance.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/orders")
public class OrdersController {


    private final ProductsService productsService;

    @Autowired
    public OrdersController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/forme")
    public ResponseEntity<?> getUserOrders(){
        return productsService.getOrdersByAuthor();
    }

    @GetMapping("/my")
    public ResponseEntity<?> getUserPurchases(){
        return productsService.getOrdersByUser();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody Long serviceId){
        return productsService.addOrder(serviceId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteorder(@RequestParam Long orderId){
        return productsService.deleteOrder(orderId);
    }

    @PostMapping(value = "/confirm", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> confirmOrder(@ModelAttribute OrderDTO orderDTO){
        return productsService.changeOrderStatus(orderDTO.getOrderId(), orderDTO.getWorkerId(), orderDTO.getStatusId(), orderDTO.getFile());
    }

    @PostMapping(value = "/accept")
    public ResponseEntity<?> acceptOrder(@RequestBody OrderDTO orderDTO){
        return productsService.changeOrderStatus(orderDTO.getOrderId(), orderDTO.getWorkerId(), orderDTO.getStatusId(), orderDTO.getFile());
    }

}
