package com.example.freelance.dao.user;

import com.example.freelance.models.Role;
import com.example.freelance.models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserExtractor implements ResultSetExtractor {
    @Override
    public Optional<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        while (rs.next()){
            User user = new User();
            Long id = rs.getLong("id");
                user.setId(id);
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setOrdersCount(rs.getInt("order_count"));
                user.setStatus(rs.getString("status_name"));
                user.setPassword(rs.getString("password"));
                user.setAbout(rs.getString("description"));
                user.setInstLink(rs.getString("inst_link"));
                user.setVkLink(rs.getString("vk_link"));
                user.setAvatarURL(rs.getString("avatar_link"));
                user.setActivationCode(rs.getString("activation_code"));
                user.setBalance(rs.getInt("balance"));
                user.setDate(rs.getDate("date").toLocalDate());
                user.setComplaintCount(rs.getInt("complaint_count"));
            Set<Role> roles = user.getRoles();
            if(roles == null){
                roles = new HashSet<>();
                user.setRoles(roles);
            }
            Role role = Role.valueOf(rs.getString("role"));
            roles.add(role);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}


