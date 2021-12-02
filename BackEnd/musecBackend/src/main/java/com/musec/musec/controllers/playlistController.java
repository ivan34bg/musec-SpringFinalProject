package com.musec.musec.controllers;

import com.musec.musec.data.models.bindingModels.playlistBindingModel;
import com.musec.musec.data.models.viewModels.shortInfo.playlistShortInfoViewModel;
import com.musec.musec.data.models.viewModels.playlist.playlistViewModel;
import com.musec.musec.services.implementations.playlistServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/playlist")
public class playlistController {
    private final playlistServiceImpl playlistService;

    public playlistController(playlistServiceImpl playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createPlaylist(playlistBindingModel playlistBindingModel, Principal principal){
        Long playlistId = playlistService.createPlaylist(playlistBindingModel, principal.getName());
        return ResponseEntity.ok(playlistId);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<playlistViewModel> returnPlaylistById(@PathVariable Long playlistId, Principal principal){
        playlistViewModel playlistToReturn;
        try {
            playlistToReturn = playlistService.returnPlaylistById(playlistId, principal.getName());
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playlistToReturn);
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long playlistId, Principal principal){
        try {
            playlistService.deletePlaylist(playlistId, principal.getName());
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/song/{playlistId}")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable Long playlistId, @RequestBody String songId, Principal principal){
        try {
            playlistService.addSongToPlaylist(playlistId, Long.parseLong(songId), principal.getName());
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        } catch (RequestRejectedException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/song/{playlistId}")
    public ResponseEntity<?> removeSongFromPlaylist(@PathVariable Long playlistId, @RequestBody String songId, Principal principal){
        try {
            playlistService.removeSongFromPlaylist(playlistId, Long.parseLong(songId), principal.getName());
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkIfLoggedUserHasPlaylists(Principal principal){
        try {
            playlistService.doesUserHavePlaylists(principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<Set<playlistShortInfoViewModel>> returnPlaylistsOfUser(Principal principal){
        return ResponseEntity.ok(playlistService.returnShortInfoOfLoggedUserPlaylists(principal.getName()));
    }
}
