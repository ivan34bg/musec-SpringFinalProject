package com.musec.musec.web;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.changeCredentialsModels.*;
import com.musec.musec.data.models.bindingModels.ProfilePicBindingModel;
import com.musec.musec.data.models.bindingModels.UserRegisterBindingModel;
import com.musec.musec.data.models.viewModels.profile.UserProfileViewModel;
import com.musec.musec.data.models.viewModels.search.UserSearchViewModel;
import com.musec.musec.services.implementations.UserServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(UserRegisterBindingModel bindingModel){
        try {
            if(
                    bindingModel.getUsername().trim().length() > 0 &&
                    bindingModel.getEmail().trim().length() > 0 &&
                    bindingModel.getFullName().trim().length() > 0 &&
                    bindingModel.getPassword().trim().length() > 0
            ) userService.registerUser(bindingModel);
            else return ResponseEntity.badRequest().build();
        } catch (CloneNotSupportedException e) {
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
    public ResponseEntity<UserProfileViewModel> returnLoggedInUserProfileDetails(Principal principal){
        return ResponseEntity.ok(userService.returnUserOrArtistProfileViewByUsername(principal.getName(), true));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileViewModel> returnUserProfileDetailsById(@PathVariable Long userId){
        try {
            return ResponseEntity.ok(userService.returnUserOrArtistProfileViewById(userId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/profile-pic")
    public ResponseEntity<?> changeProfilePicOfLoggedUser(ProfilePicBindingModel bindingModel, Principal principal){
        try {
            userService.changeProfilePic(bindingModel.getNewProfilePic(), principal.getName());
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/username")
    public ResponseEntity<?> changeUsernameOfLoggedUser(ChangeUsernameBindingModel bindingModel, Principal principal){
        try {
            userService.changeUsernameOfLoggedUser(bindingModel, principal.getName());
        } catch (AccessDeniedException | CloneNotSupportedException | NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePasswordOfLoggedUser(ChangePasswordBindingModel bindingModel, Principal principal){
        try {
            userService.changePasswordOfLoggedUser(bindingModel, principal.getName());
        } catch (AccessDeniedException | NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/full-name")
    public ResponseEntity<?> changeFullNameOfLoggedUser(ChangeFullNameBindingModel bindingModel, Principal principal){
        try {
            userService.changeFullNameOfLoggedUser(bindingModel, principal.getName());
        } catch (NotFoundException | AccessDeniedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email")
    public ResponseEntity<?> changeEmailOfLoggedUser(ChangeEmailBindingModel bindingModel, Principal principal){
        try {
            userService.changeEmailOfLoggedUser(bindingModel, principal.getName());
        } catch (NotFoundException | AccessDeniedException | CloneNotSupportedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/birthday")
    public ResponseEntity<?> changeBirthdayOfLoggedUser(ChangeBirthdayBindingModel bindingModel, Principal principal){
        try {
            userService.changeBirthdayOfLoggedUser(bindingModel, principal.getName());
        } catch (NotFoundException | AccessDeniedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSearchViewModel>> searchUserByFullName(@RequestParam(name = "param") String parameter){
        return ResponseEntity.ok(userService.searchUsersByFullName(parameter));
    }

    @GetMapping("/artist")
    public ResponseEntity<?> isLoggedUserArtist(Principal principal){
        boolean result;
        try {
            result = userService.isUserArtist(principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/artist/{userId}")
    public ResponseEntity<?> isUserArtist(@PathVariable Long userId){
        boolean isUserArtist;
        try {
            isUserArtist = userService.isUserArtistById(userId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(isUserArtist);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> isLoggedUserAdmin(Principal principal){
        boolean isAdmin;
        isAdmin = userService.isUserAdmin(principal.getName());
        return ResponseEntity.ok(isAdmin);
    }

    @GetMapping("/admin/{userId}")
    public ResponseEntity<?> isUserAdmin(@PathVariable Long userId){
        boolean isUserAdmin;
        try {
            isUserAdmin = userService.isUserAdminById(userId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(isUserAdmin);
    }
}
