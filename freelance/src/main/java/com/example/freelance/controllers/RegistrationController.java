package com.example.freelance.controllers;


import com.example.freelance.dto.RegDTO;
import com.example.freelance.dto.UserDTO;
import com.example.freelance.models.User;
import com.example.freelance.services.AuthUserService;
import com.example.freelance.services.RegAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/register")
public class RegistrationController {


    private final RegAuthService regAuthService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public RegistrationController(RegAuthService regAuthService) {
        this.regAuthService = regAuthService;
    }

    @GetMapping("")
    public String regist() {
        return "reg";
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> register(@ModelAttribute UserDTO userDTO) throws IOException {
        String photoPath = null;
        if(userDTO.getPhoto()!=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile+ "." + userDTO.getPhoto().getOriginalFilename();
            userDTO.getPhoto().transferTo(new File(uploadPath+"/"+resultFile));
            photoPath = resultFile;
        }
        RegDTO regDTO = new RegDTO(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPassword(), photoPath, userDTO.getAbout(), userDTO.getInstLink(), userDTO.getVkLink());
        try {
            Map<Object, Object> response = regAuthService.register(regDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED) ;
        }catch (Exception e){
            return new ResponseEntity<>(new HashMap<>(){{put("message", "Данный email уже занят");}},HttpStatus.BAD_REQUEST) ;
        }


    }

}
