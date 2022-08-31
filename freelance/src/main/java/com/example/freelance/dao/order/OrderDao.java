package com.example.freelance.dao.order;

import com.example.freelance.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;


public class OrderDao {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean save(Long userId, Long serviceId) throws SQLException{
        jdbcTemplate.update("CALL addorder(?,?)", userId, serviceId);
        return true;
    }

    public Order getOrderById(Long id){
        return jdbcTemplate.query("SELECT * FROM selectorderbyid(?)", new OrderExtractor(), id).stream().findAny().orElse(null);
    }

    public List<Order> getOrdersByAuthorId(Long id){
        return jdbcTemplate.query("SELECT * FROM selectordersbyauthor(?)", new OrderExtractor(), id);
    }

    public List<Order> getOrdersByUserId(Long id){
        return jdbcTemplate.query("SELECT * FROM selectordersbyuser(?)", new OrderExtractor(), id);
    }

    public boolean delete(Long id) throws SQLException {
        jdbcTemplate.update("CALL deleteorder(?)", id);
        return true;
    }

    public boolean changeStatus(Long id, int statusId, String filePath, Long workerId) throws SQLException{
        if(statusId==3){
            jdbcTemplate.update("CALL uodateUserRating(?)", workerId);
        }
        jdbcTemplate.update("CALL changeorderstatus(?,?,?)", id, (short)statusId, filePath);
        return true;
    }

}
