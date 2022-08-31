package com.example.freelance.services;

import com.example.freelance.dao.order.OrderDao;
import com.example.freelance.dao.products.ProductsDao;
import com.example.freelance.dto.ProductDTO;
import com.example.freelance.models.Product;
import com.example.freelance.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductsService {

    @Value("${upload.path}")
    private String uploadPath;
    @Value("${download.path}")
    private String downloadpath;

    private final OrderDao orderDao;
    private final ProductsDao productsDao;

    @Autowired
    public ProductsService(OrderDao orderDao, ProductsDao productsDao) {
        this.orderDao = orderDao;
        this.productsDao = productsDao;
    }



    public boolean addProduct(ProductDTO productDTO, Long id) throws IOException  {
        List<MultipartFile> photos = productDTO.getPhotos();
        List<String> photoPaths = new ArrayList<>();
        if (photos!=null){
            photos.stream().forEach((p)->{
                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFile = uuidFile+ "." + p.getOriginalFilename();
                try {
                    p.transferTo(new File(uploadPath+"/"+resultFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                photoPaths.add(resultFile);
            });
        }
        productsDao.save(productDTO, photoPaths.stream().toArray(String[] ::new), id);
        return true;
    }

    public ResponseEntity<?> getProduct(Long id){
        Product product = productsDao.getById(id);
        if(product!=null){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Not Found!", HttpStatus.NOT_FOUND);
    }

    public List<Product> getProductsByFilter(String name, short filter){
        return productsDao.getProductsByfilter(name, filter);
    }

    public ResponseEntity<?> getOrdersByUser(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null){
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(orderDao.getOrdersByUserId(user.getId()), HttpStatus.OK);
    }
    public ResponseEntity<?> getOrdersByAuthor(){

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null){
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(orderDao.getOrdersByAuthorId(user.getId()), HttpStatus.OK);
    }

    public ResponseEntity<?> addOrder(Long serviceId){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null){
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }
        try {
            orderDao.save(user.getId(), serviceId);
            return new ResponseEntity<>("Заказ создан", HttpStatus.CREATED);
        }catch (SQLException e){
            return  new ResponseEntity<>("Произошла ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteOrder(Long orderId){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null){
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }
        try {
            orderDao.delete(orderId);
            return new ResponseEntity<>("Заказ удален", HttpStatus.CREATED);
        }catch (SQLException e){
            return  new ResponseEntity<>("Произошла ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> changeOrderStatus(Long orderId, Long workerId, int statusId, MultipartFile file){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null||user.getId()!=workerId){
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }

        try {
            String path = null;
            if(file!=null){
                File uploadDir = new File(downloadpath);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFile = uuidFile+ "." + file.getOriginalFilename();
                file.transferTo(new File(downloadpath+"/"+resultFile));
                path = resultFile;
            }
            orderDao.changeStatus(orderId, statusId, path, workerId);
            return new ResponseEntity<>("Заказ обработан", HttpStatus.OK);
        }catch (SQLException | IOException e){
            return  new ResponseEntity<>("Произошла ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteProduct(Long id) {
        Product product = productsDao.getById(id);
        if(product==null){
            return new ResponseEntity<>("Продукт не найден", HttpStatus.NOT_FOUND);
        }
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null||user.getId()!=product.getAuthor().getId()){
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }
        try{
            productsDao.delete(id);
            return new ResponseEntity<>("Услуга удалена", HttpStatus.CREATED);
        }catch (SQLException e){
            return new ResponseEntity<>("Произошла ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
