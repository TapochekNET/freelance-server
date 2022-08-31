package com.example.freelance.dao.user;

import com.example.freelance.dto.UserDTO;
import com.example.freelance.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

public class UserDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean save(String name, String surname, String email, String password, String photo, String about, String instLink, String vkLink) {
        try{
            jdbcTemplate.update("CALL adduser (?, ?, ?, ?, ?, ?, ?, ?, ?)", name, surname, email, password, new Date(), photo, about, instLink, vkLink);
        }catch (DataAccessException e){
            return false;
        }
        return true;
    }

    public Optional<User> findByEmail(String email){
        Optional<User> user = (Optional<User>) jdbcTemplate.query("SELECT * FROM selectuserandrolesbyemail(?);", new Object[]{email}, new UserExtractor());

        return user;

    }

    public Optional<User> findById(Long id){
        return (Optional<User>) jdbcTemplate.query("SELECT * FROM selectuserbyid(?);", new UserExtractor(), new Object[]{id} );
    }

    public boolean delete(Long id) throws SQLException {
        jdbcTemplate.update("CALL deleteuser(?)", id);
        return true;
    }

    public boolean changeStatus(Long id, int statusId) throws SQLException{
        jdbcTemplate.update("CALL changeUserStatus(?,?)", id, (short)statusId);
        return true;
    }
}