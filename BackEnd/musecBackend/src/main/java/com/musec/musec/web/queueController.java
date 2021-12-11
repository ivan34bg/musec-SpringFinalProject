package com.musec.musec.web;

import com.musec.musec.data.models.viewModels.queue.queueSongViewModel;
import com.musec.musec.services.implementations.queueServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/queue")
public class queueController {
    private final queueServiceImpl queueService;

    public queueController(queueServiceImpl queueService) {
        this.queueService = queueService;
    }

    @GetMapping("")
    public ResponseEntity<List<queueSongViewModel>> returnQueueOfLoggedUser(Principal principal){
        List<queueSongViewModel> queueToReturn = queueService.returnQueueOfUser(principal.getName());
        return ResponseEntity.ok(queueToReturn);
    }

    @DeleteMapping("")
    public ResponseEntity<?> emptyQueue(Principal principal){
        queueService.emptyQueue(principal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/song")
    public ResponseEntity<?> returnSongInfoInQueueOfLoggedUser(Principal principal){
        return ResponseEntity.ok(queueService.returnFullSongInfo(principal.getName()));
    }

    @PostMapping("/song/{songId}")
    public ResponseEntity<?> addSongToQueue(@PathVariable Long songId, Principal principal){
        try {
            queueService.addSongToQueue(songId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CloneNotSupportedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/song/{songId}")
    public ResponseEntity<?> removeSongFromQueue(@PathVariable Long songId, Principal principal){
        try {
            queueService.removeSongFromQueue(songId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
