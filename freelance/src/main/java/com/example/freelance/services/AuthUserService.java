package com.example.freelance.services;

import com.example.freelance.dao.user.UserDao;
import com.example.freelance.dto.RegDTO;
import com.example.freelance.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class AuthUserService implements UserDetailsService {

    UserService userService;

    @Autowired
    public AuthUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUserAndProductsByEmail(s);
        if(user==null){
            throw new UsernameNotFoundException("User does'nt exist");
        }
        return user;
    }
}
