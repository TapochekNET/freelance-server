package com.example.freelance.configs;

import com.example.freelance.dao.order.OrderDao;
import com.example.freelance.dao.products.ProductsDao;
import com.example.freelance.dao.user.UserDao;
import com.example.freelance.seccurity.JwtTokenProvider;
import com.example.freelance.services.AuthUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class DataConfig implements WebMvcConfigurer {

    @Value("${jdbc.driverClassName}")
    private String jdbcDriver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public UserDao userDao(){
        return new UserDao(jdbcTemplate());
    }

    @Bean
    public ProductsDao productsDao() {return new ProductsDao(jdbcTemplate());}


    @Bean
    public OrderDao ordersDao() {return new OrderDao(jdbcTemplate());}


    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

}
