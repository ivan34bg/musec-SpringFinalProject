package com.musec.musec.controllers;

import com.musec.musec.entities.models.albumBindingModel;
import com.musec.musec.entities.models.albumViewModel;
import com.musec.musec.entities.models.songBindingModel;
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
        try {
            Long albumId = albumService.createAlbum(bindingModel, principal.getName());
            return ResponseEntity.ok(albumId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/add-song/{id}")
    public ResponseEntity<String> addSongToAlbum(@PathVariable Long id, songBindingModel bindingModel){
        try {
            albumService.addSongToAlbum(id, bindingModel);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<albumViewModel> returnAlbum(@PathVariable Long id){
        try {
            return ResponseEntity.ok(albumService.returnAlbum(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
