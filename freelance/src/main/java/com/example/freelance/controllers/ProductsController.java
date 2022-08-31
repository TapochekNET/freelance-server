package com.example.freelance.controllers;


import com.example.freelance.dto.ProductDTO;
import com.example.freelance.models.Product;
import com.example.freelance.models.User;
import com.example.freelance.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/service")
public class ProductsController {
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping(value = "/add",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addProduct(@ModelAttribute ProductDTO productDTO, @AuthenticationPrincipal User user){
        if(user==null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        try {
            productsService.addProduct(productDTO, user.getId());
            return new ResponseEntity<>("Продукт создан",HttpStatus.CREATED);
        }catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("")
    public ResponseEntity<?> findProducts(@RequestParam String name, @RequestParam short filter){
        List<Product> response = productsService.getProductsByFilter(name, filter);
        if(response.size()==0){
            return new ResponseEntity<>("Ничего не найдено", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        return productsService.getProduct(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam Long id){
        return productsService.deleteProduct(id);
    }

}
