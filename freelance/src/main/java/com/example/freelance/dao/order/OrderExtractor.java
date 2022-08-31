package com.example.freelance.dao.order;

import com.example.freelance.models.Order;
import com.example.freelance.models.Product;
import com.example.freelance.models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderExtractor implements ResultSetExtractor<List<Order>> {

    @Override
    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Order> orderList = new ArrayList<>();
        while (rs.next()){
            Long id = rs.getLong("id");
            Order order = orderList.stream().filter(o->o.getId()==id).findAny().orElse(null);
            if(order == null){
                order = new Order();
                order.setId(id);
                order.setCreationDate(rs.getDate("creation_date").toLocalDate());
                order.setStatus(rs.getString("order_status"));
                String path = rs.getString("content");
                if(path!=null){
                    order.setContent(path);
                }
                Product product = new Product();
                product.setId(rs.getLong("service_id"));
                product.setName(rs.getString("service_name"));
                Array a = rs.getArray("photo_links");
                List<String> photoList = Arrays.asList((String[]) a.getArray());
                product.setPhotoLinks(photoList);
                order.setProduct(product);
                User orderer = new User();
                orderer.setId(rs.getLong("orderer_id"));
                orderer.setFirstName(rs.getString("orderer_firstname"));
                orderer.setLastName(rs.getString("orderer_lastname"));
                orderer.setAvatarURL(rs.getString("orderer_avatar"));
                order.setOrderer(orderer);
                orderList.add(order);
            }
        }
        return orderList;
    }
}
