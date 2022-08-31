package com.example.freelance.controllers;

import com.example.freelance.dao.products.ProductsDao;
import com.example.freelance.dto.UserDTO;
import com.example.freelance.models.User;
import com.example.freelance.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final ProductsDao productsDao;

    @Autowired
    public ProfileController(UserService userService, ProductsDao productsDao) {
        this.userService = userService;
        this.productsDao = productsDao;
    }

    @PostMapping("/profile")
    public ResponseEntity<?> postUser (@RequestBody UserDTO userDTO){

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAuthUser(@AuthenticationPrincipal User user){

        if(user!=null){
            user.setProducts(productsDao.getProductsByUserId(user.getId()));
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Пользователь не ныйден" ,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try {
            Map<String, Object> response = userService.getUserAndProducts(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println("response");
          return new ResponseEntity<>("Пользователь не найден" ,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteProfile(){
        return userService.deleteProfile();
    }
}
