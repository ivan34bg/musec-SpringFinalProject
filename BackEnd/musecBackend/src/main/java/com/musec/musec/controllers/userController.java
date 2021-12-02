package com.musec.musec.controllers;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.changeCredentialsModels.*;
import com.musec.musec.data.models.bindingModels.profilePicBindingModel;
import com.musec.musec.data.models.bindingModels.userRegisterBindingModel;
import com.musec.musec.data.models.viewModels.profile.userProfileViewModel;
import com.musec.musec.services.implementations.userServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        userService.logoutUser(request, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logged-in-test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/self-profile")
    public ResponseEntity<userProfileViewModel> returnLoggedInUserProfileDetails(Principal principal){
        try {
            return ResponseEntity.ok(userService.returnUserOrArtistProfileViewByUsername(principal.getName(), true));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<userProfileViewModel> returnUserProfileDetailsById(@PathVariable Long userId){
        try {
            return ResponseEntity.ok(userService.returnUserOrArtistProfileViewById(userId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/profile-pic")
    public ResponseEntity<?> changeProfilePicOfLoggedUser(profilePicBindingModel bindingModel, Principal principal){
        try {
            userService.changeProfilePic(bindingModel.getNewProfilePic(), principal.getName());
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/username")
    public ResponseEntity<?> changeUsernameOfLoggedUser(changeUsernameBindingModel bindingModel, Principal principal){
        try {
            userService.changeUsernameOfLoggedUser(bindingModel, principal.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePasswordOfLoggedUser(changePasswordBindingModel bindingModel, Principal principal){
        try {
            userService.changePasswordOfLoggedUser(bindingModel, principal.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/full-name")
    public ResponseEntity<?> changeFullNameOfLoggedUser(changeFullNameBindingModel bindingModel, Principal principal){
        try {
            userService.changeFullNameOfLoggedUser(bindingModel, principal.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email")
    public ResponseEntity<?> changeEmailOfLoggedUser(changeEmailBindingModel bindingModel, Principal principal){
        try {
            userService.changeEmailOfLoggedUser(bindingModel, principal.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/birthday")
    public ResponseEntity<?> changeBirthdayOfLoggedUser(changeBirthdayBindingModel bindingModel, Principal principal){
        try {
            userService.changeBirthdayOfLoggedUser(bindingModel, principal.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
