package com.musec.musec.web;

import com.dropbox.core.DbxException;
import com.musec.musec.services.implementations.AlbumServiceImpl;
import com.musec.musec.services.implementations.SingleServiceImpl;
import com.musec.musec.services.implementations.UserServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.rmi.AccessException;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final AlbumServiceImpl albumService;
    private final SingleServiceImpl singleService;

    public AdminController(UserServiceImpl userService, AlbumServiceImpl albumService, SingleServiceImpl singleService) {
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
