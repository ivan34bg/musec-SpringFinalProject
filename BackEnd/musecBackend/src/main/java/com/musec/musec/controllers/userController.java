package com.musec.musec.controllers;

import com.musec.musec.entities.models.userRegisterBindingModel;
import com.musec.musec.services.implementations.userServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class userController {
    private final userServiceImpl userService;

    public userController(userServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(userRegisterBindingModel bindingModel){
        try {
            userService.registerUser(bindingModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
