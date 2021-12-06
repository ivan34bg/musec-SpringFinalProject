package com.musec.musec.controllers;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.albumBindingModel;
import com.musec.musec.data.models.viewModels.album.albumViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.models.viewModels.search.albumSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.albumShortInfoViewModel;
import com.musec.musec.services.implementations.albumServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/album")
public class albumController {
    private final albumServiceImpl albumService;

    public albumController(albumServiceImpl albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAlbum(albumBindingModel bindingModel, Principal principal) {
        Long albumId;
        try {
            albumId = albumService.createAlbum(bindingModel, principal.getName());
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        } catch (RoleNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(albumId);
    }

    @PostMapping("/song/{id}")
    public ResponseEntity<String> addSongToAlbum(@PathVariable Long id, songBindingModel bindingModel, Principal principal){
        try {
            albumService.addSongToAlbum(id, bindingModel, principal.getName());
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }   catch (DbxException e){
            return ResponseEntity.internalServerError().build();
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
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<Set<albumShortInfoViewModel>> returnShortInfoOfAlbumsOfLoggedUser(Principal principal){
        return ResponseEntity.ok(albumService.returnShortInfoOfAllAlbumsOfLoggedUser(principal.getName()));
    }

    @GetMapping("/search")
    public ResponseEntity<Set<albumSearchViewModel>> searchAlbumByName(@RequestParam(name = "param") String parameters){
        return ResponseEntity.ok(albumService.searchAlbumByName(parameters));
    }

    @PostMapping("/{albumId}/queue")
    public ResponseEntity<?> addWholeAlbumToQueue(@PathVariable Long albumId, Principal principal){
        try {
            albumService.addAlbumToQueue(albumId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
