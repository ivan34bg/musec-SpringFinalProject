package com.musec.musec.controllers;

import com.musec.musec.entities.models.singleBindingModel;
import com.musec.musec.entities.models.singleViewModel;
import com.musec.musec.entities.models.songBindingModel;
import com.musec.musec.services.implementations.singleServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/single")
public class singleController {
    private final singleServiceImpl singleService;

    public singleController(singleServiceImpl singleService) {
        this.singleService = singleService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createNewSingle(singleBindingModel singleBindingModel, Principal principal) {
        try {
            Long id = singleService.createSingle(singleBindingModel, principal.getName());
            return ResponseEntity.ok(id);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/add-song/{id}")
    public ResponseEntity<String> addSongToSingle(@PathVariable Long id, songBindingModel songBindingModel) {
        try {
            singleService.addSongToSingle(songBindingModel, id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<singleViewModel> returnSingleById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(singleService.returnSingle(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
