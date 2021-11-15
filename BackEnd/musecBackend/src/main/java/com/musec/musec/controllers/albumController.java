package com.musec.musec.controllers;

import com.musec.musec.data.models.bindingModels.albumBindingModel;
import com.musec.musec.data.models.viewModels.albumViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.services.implementations.albumServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/album")
public class albumController {
    private final albumServiceImpl albumService;

    public albumController(albumServiceImpl albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createAlbum(albumBindingModel bindingModel, Principal principal) {
        Long albumId;
        try {
            albumId = albumService.createAlbum(bindingModel, principal.getName());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(albumId);
    }

    @PostMapping("/song/{id}")
    public ResponseEntity<String> addSongToAlbum(@PathVariable Long id, songBindingModel bindingModel){
        try {
            albumService.addSongToAlbum(id, bindingModel);
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<albumViewModel> returnAlbum(@PathVariable Long id){
        albumViewModel albumToReturn;
        try {
            albumToReturn = albumService.returnAlbum(id);
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(albumToReturn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable("id") Long albumId){
        try {
            albumService.deleteAlbum(albumId);
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
