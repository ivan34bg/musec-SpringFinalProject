package com.musec.musec.web;

import com.dropbox.core.DbxException;
import com.musec.musec.services.implementations.albumServiceImpl;
import com.musec.musec.services.implementations.singleServiceImpl;
import com.musec.musec.services.implementations.userServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.rmi.AccessException;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class adminController {
    private final userServiceImpl userService;
    private final albumServiceImpl albumService;
    private final singleServiceImpl singleService;

    public adminController(userServiceImpl userService, albumServiceImpl albumService, singleServiceImpl singleService) {
        this.userService = userService;
        this.albumService = albumService;
        this.singleService = singleService;
    }

    @PostMapping("/role/{userId}")
    public ResponseEntity<?> addRoleToUserById(@RequestParam("role") String roleName, @PathVariable Long userId){
        try {
            userService.addRoleToUser(userId, roleName);
        } catch (CloneNotSupportedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AccessException e) {
            return ResponseEntity.status(403).build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/role/{userId}")
    public ResponseEntity<?> removeRoleOfUser(@RequestParam("role") String roleName, @PathVariable Long userId){
        try {
            userService.removeRoleOfUser(userId, roleName);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/album/{albumId}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long albumId, Principal principal){
        try {
            albumService.adminDeleteAlbum(albumId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/single/{singleId}")
    public ResponseEntity<?> deleteSingle(@PathVariable Long singleId, Principal principal){
        try {
            singleService.adminDeleteSingle(singleId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
