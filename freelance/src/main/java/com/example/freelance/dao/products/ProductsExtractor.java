package com.example.freelance.dao.products;

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

public class ProductsExtractor implements ResultSetExtractor<List<Product>> {


    @Override
    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Product> productList = new ArrayList<>();
        while (rs.next()){
            Long id = rs.getLong("id");
            Product product = productList.stream().filter(p->p.getId()==id).findAny().orElse(null);
            if(product == null){
                product = new Product();
                product.setId(id);
                product.setName(rs.getString("service_name"));
                product.setType(rs.getString("service_type"));
                product.setCost(rs.getInt("cost"));
                product.setPurchases(rs.getInt("purchases"));
                product.setDescription(rs.getString("description"));
                Array a = rs.getArray("photo_links");
                List<String> photoList = Arrays.asList((String[]) a.getArray());
                product.setPhotoLinks(photoList);
                product.setCreationDate(rs.getDate("creation_date").toLocalDate());
                product.setUpdateDate(rs.getDate("update_date").toLocalDate());
                User author = new User();
                author.setId(rs.getLong("author_id"));
                author.setFirstName(rs.getString("author_firstname"));
                author.setLastName(rs.getString("author_lastname"));
                author.setAvatarURL(rs.getString("author_avatar"));
                product.setAuthor(author);
                productList.add(product);
            }

        }
        return productList;
    }
}
