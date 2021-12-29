package com.musec.musec.web;

import com.musec.musec.data.models.bindingModels.PlaylistBindingModel;
import com.musec.musec.data.models.viewModels.search.PlaylistSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.PlaylistShortInfoViewModel;
import com.musec.musec.data.models.viewModels.playlist.PlaylistViewModel;
import com.musec.musec.services.implementations.PlaylistServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {
    private final PlaylistServiceImpl playlistService;

    public PlaylistController(PlaylistServiceImpl playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createPlaylist(PlaylistBindingModel playlistBindingModel, Principal principal){
        if (playlistBindingModel.getPlaylistName().trim().length() > 0) {
            Long playlistId = playlistService.createPlaylist(playlistBindingModel, principal.getName());
            return ResponseEntity.ok(playlistId);
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistViewModel> returnPlaylistById(@PathVariable Long playlistId, Principal principal){
        PlaylistViewModel playlistToReturn;
        try {
            playlistToReturn = playlistService.returnPlaylistById(playlistId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playlistToReturn);
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long playlistId, Principal principal){
        try {
            playlistService.deletePlaylist(playlistId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/song/{playlistId}")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable Long playlistId, @RequestBody String songId, Principal principal){
        try {
            playlistService.addSongToPlaylist(playlistId, Long.parseLong(songId), principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RequestRejectedException e){
            return ResponseEntity.status(403).build();
        } catch (CloneNotSupportedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/song/{playlistId}")
    public ResponseEntity<?> removeSongFromPlaylist(@PathVariable Long playlistId, @RequestBody String songId, Principal principal){
        try {
            playlistService.removeSongFromPlaylist(playlistId, Long.parseLong(songId), principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RequestRejectedException e) {
            return ResponseEntity.status(403).build();
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
    public ResponseEntity<List<PlaylistShortInfoViewModel>> returnPlaylistsOfUser(Principal principal){
        return ResponseEntity.ok(playlistService.returnShortInfoOfLoggedUserPlaylists(principal.getName()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlaylistSearchViewModel>> searchPlaylistByName(@RequestParam(name = "param") String parameters){
        return ResponseEntity.ok(playlistService.searchPlaylistByName(parameters));
    }

    @PostMapping("/{playlistId}/queue")
    public ResponseEntity<?> addWholePlaylistToQueue(@PathVariable Long playlistId, Principal principal){
        try {
            playlistService.addPlaylistToQueue(playlistId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
