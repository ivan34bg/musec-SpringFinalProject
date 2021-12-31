package com.musec.musec.web;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.AlbumBindingModel;
import com.musec.musec.data.models.viewModels.album.AlbumViewModel;
import com.musec.musec.data.models.bindingModels.SongBindingModel;
import com.musec.musec.data.models.viewModels.search.AlbumSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.AlbumShortInfoViewModel;
import com.musec.musec.services.implementations.AlbumServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/album")
public class AlbumController {
    private final AlbumServiceImpl albumService;

    public AlbumController(AlbumServiceImpl albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAlbum(AlbumBindingModel bindingModel, Principal principal) {
        Long albumId;
        try {
            if (bindingModel.getAlbumName().trim().length() > 0)
                albumId = albumService.createAlbum(bindingModel, principal.getName());
            else return ResponseEntity.badRequest().build();
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        } catch (RoleNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.created(URI.create("/album/" + albumId)).build();
    }

    @PostMapping("/{albumId}/song")
    public ResponseEntity<String> addSongToAlbum(@PathVariable Long albumId, SongBindingModel bindingModel, Principal principal){
        try {
            albumService.addSongToAlbum(albumId, bindingModel, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }   catch (DbxException e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumViewModel> returnAlbum(@PathVariable Long id){
        AlbumViewModel albumToReturn;
        try {
            albumToReturn = albumService.returnAlbum(id);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(albumToReturn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable("id") Long albumId, Principal principal){
        try {
            albumService.publicDeleteAlbum(albumId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<AlbumShortInfoViewModel>> returnShortInfoOfAlbumsOfLoggedUser(Principal principal){
        return ResponseEntity.ok(albumService.returnShortInfoOfAllAlbumsOfUserByUsername(principal.getName()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AlbumShortInfoViewModel>> returnShortInfoOfAlbumsOfUserById(@PathVariable Long userId){
        List<AlbumShortInfoViewModel> albums;
        try {
            albums = albumService.returnShortInfoOfAllAlbumsOfUserById(userId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AlbumSearchViewModel>> searchAlbumByName(@RequestParam(name = "param") String parameters){
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
