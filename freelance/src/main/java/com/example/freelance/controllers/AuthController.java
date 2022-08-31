package com.example.freelance.controllers;

import com.example.freelance.dto.AuthDTO;
import com.example.freelance.models.User;
import com.example.freelance.services.RegAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;

@RestController
public class AuthController {

    private final RegAuthService regAuthService;

    @Autowired
    public AuthController(RegAuthService regAuthService) {
        this.regAuthService = regAuthService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody AuthDTO request){
        try {
            Map<Object, Object> response = regAuthService.auth(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid email/password", HttpStatus.FORBIDDEN);
        } catch (SQLException e){
            return new ResponseEntity<>("Произошла ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/login")
    public String log(){
        return "plog";
    }

    @RequestMapping(value = "/login", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        return regAuthService.logout(request, response);
    }
}
