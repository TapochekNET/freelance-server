package com.example.freelance.services;

import com.example.freelance.dao.order.OrderDao;
import com.example.freelance.dao.products.ProductsDao;
import com.example.freelance.dao.user.UserDao;
import com.example.freelance.models.Order;
import com.example.freelance.models.Product;
import com.example.freelance.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final ProductsDao productsDao;

    @Autowired
    public UserService(UserDao userDao, OrderDao orderDao, ProductsDao productsDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.productsDao = productsDao;
    }
    public Map<String, Object> getUserAndProducts (Long id){
        User userDTO = userDao.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
        List<Product> productsDTO = productsDao.getProductsByUserId(id);
        userDTO.setProducts(productsDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("user", userDTO);
        return response;
    }
    public User getUserAndProductsByEmail (String email){
        User userDTO = userDao.findByEmail(email).orElse(null);
        if(userDTO!=null) {
            List<Product> productsDTO = productsDao.getProductsByUserId(userDTO.getId());
            userDTO.setProducts(productsDTO);
            List<Order> purchases = orderDao.getOrdersByUserId(userDTO.getId());
            userDTO.setPurchases(purchases);
            List<Order> orders = orderDao.getOrdersByAuthorId(userDTO.getId());
            userDTO.setOrders(orders);
        }
        return userDTO;
    }
    public ResponseEntity<?> deleteProfile(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null){
            return new ResponseEntity<>("Доступ запрещён", HttpStatus.FORBIDDEN);
        }
        try {
            userDao.delete(user.getId());
            return new ResponseEntity<>("Пользователь удалён", HttpStatus.OK);
        } catch (SQLException throwables) {
            return new ResponseEntity<>("Доступ запрещён", HttpStatus.FORBIDDEN);
        }
    }
}
