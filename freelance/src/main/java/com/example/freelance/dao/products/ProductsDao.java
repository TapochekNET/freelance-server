package com.example.freelance.dao.products;

import com.example.freelance.dto.ProductDTO;
import com.example.freelance.models.Product;
import com.example.freelance.models.ProductContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ProductsDao{

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductsDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean save(ProductDTO productDTO, String[] photos, Long authorId){
        try {
            jdbcTemplate.update("CALL addservice(?,?,?,?,?,?);", productDTO.getName(), productDTO.getType(), authorId, productDTO.getDescription(), photos, productDTO.getCost());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Product getById(Long id){
        return jdbcTemplate.query("SELECT * FROM selectservicebyid(?)", new ProductsExtractor(), id).stream().findAny().orElse(null);
    }

    public List<Product> getPopularBy8(Integer page){
        return jdbcTemplate.query("SELECT * FROM selecttopserviceslimit8(?)", new ProductsExtractor(), page);
    }

    public List<Product> getProductsByUserId(Long id) {
        return jdbcTemplate.query("SELECT * FROM selectservicesbyuserid(?);",  new ProductsExtractor(), id);
    }

    public List<Product> getProductsByfilter(String value, short filter){
        switch (filter){
            case 1:
                return jdbcTemplate.query("SELECT * FROM selectservicesbyname(?)", new ProductsExtractor(), value);
            case 2:
                return jdbcTemplate.query("SELECT * FROM selectservicesbycategory(?)", new ProductsExtractor(), value.toUpperCase());
            default:
                return jdbcTemplate.query("SELECT * FROM selectservicesbyauthorname(?)", new ProductsExtractor(), value);
        }
    }

    public boolean delete(Long id) throws SQLException{
        jdbcTemplate.update("CALL deleteservice(?)", id);
        return true;
    }



}
