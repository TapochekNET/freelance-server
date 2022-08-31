package com.example.freelance.controllers;


import com.example.freelance.dao.products.ProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {

    private final ProductsDao productsDao;

    @Autowired
    public MainController(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }


    @GetMapping("")
    public ResponseEntity<?> getPopular(@RequestParam Integer page){
        return new ResponseEntity<>(productsDao.getPopularBy8(page), HttpStatus.OK);
    }
}
