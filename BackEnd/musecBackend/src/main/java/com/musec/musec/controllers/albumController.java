package com.musec.musec.controllers;

import com.musec.musec.entities.models.albumBindingModel;
import com.musec.musec.entities.models.songBindingModel;
import com.musec.musec.entities.models.songViewModel;
import com.musec.musec.services.implementations.albumServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/album")
public class albumController {
    private final albumServiceImpl albumService;

    public albumController(albumServiceImpl albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/create-album")
    public ResponseEntity<Long> createAlbum(albumBindingModel bindingModel) {
        try {
            Long albumId = albumService.createAlbum(bindingModel);
            return ResponseEntity.ok(albumId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/add-song/{id}")
    public ResponseEntity<?> addSongToAlbum(@PathVariable Long id, songBindingModel bindingModel){
        try {
            albumService.addSongToAlbum(id, bindingModel);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/songs/{id}")
    public ResponseEntity<List<songViewModel>> returnSongsOfAnAlbum(@PathVariable Long id){
        try {
            return ResponseEntity.ok(albumService.returnAllSongsFromAnAlbum(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
