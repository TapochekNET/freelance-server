package com.example.freelance.services;

import com.example.freelance.dao.user.UserDao;
import com.example.freelance.dto.AuthDTO;
import com.example.freelance.dto.RegDTO;
import com.example.freelance.models.Role;
import com.example.freelance.models.User;
import com.example.freelance.seccurity.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserDao userDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegAuthService(AuthenticationManager authenticationManager, UserDao userDao, UserService userService, UserDao userDao1, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userDao = userDao;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<Object, Object> register(RegDTO userDTO) throws Exception{
        User user = userService.getUserAndProductsByEmail(userDTO.getEmail());
        if(user != null){
            throw new Exception();
        }
        if(userDao.save(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getPhoto(), userDTO.getAbout(), userDTO.getInstLink(), userDTO.getVkLink())){
            String token = jwtTokenProvider.createToken(userDTO.getEmail(), Collections.singleton(Role.USER));
            Map <Object, Object> response = new HashMap<>();
            response.put("user", userService.getUserAndProductsByEmail(userDTO.getEmail()));
            response.put("token", token);
            return response;
        }
        return null;
    }

    public Map<Object, Object> auth(AuthDTO request) throws AuthenticationException, SQLException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userService.getUserAndProductsByEmail(request.getEmail());
        if(user==null){
            throw  new UsernameNotFoundException("User dose'nt exist");
        }
        if(userDao.changeStatus(user.getId(), 1)){
            user.setStatus("ACTIVE");
        }
        String token = jwtTokenProvider.createToken(request.getEmail(), user.getRoles());
        Map<Object, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", token);
        return response;
    }

    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null){
            try {
                userDao.changeStatus(user.getId(), 2);
            }catch (SQLException e){
                return new ResponseEntity<>("Произошла ошибка", HttpStatus.FORBIDDEN);
            }
        }
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
        return new ResponseEntity<>("logged out", HttpStatus.OK);
    }
}
