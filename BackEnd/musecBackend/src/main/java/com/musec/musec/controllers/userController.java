package com.musec.musec.controllers;

import com.musec.musec.data.models.bindingModels.userRegisterBindingModel;
import com.musec.musec.services.implementations.userServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

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

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().invalidate();
        for (Cookie cookie:
                request.getCookies()) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logged-in-test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/self-profile")
    public ResponseEntity<?> returnLoggedInUserProfileDetails(Principal principal){

        try {
            return ResponseEntity.ok(userService.returnUserOrArtistProfileViewByUsername(principal.getName()));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
