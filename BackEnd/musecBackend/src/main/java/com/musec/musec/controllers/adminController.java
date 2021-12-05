package com.musec.musec.controllers;

import com.musec.musec.services.userService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class adminController {
    private final userService userService;

    public adminController(userService userService) {
        this.userService = userService;
    }
}
