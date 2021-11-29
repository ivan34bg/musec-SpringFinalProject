package com.musec.musec.controllers;

import com.musec.musec.data.models.bindingModels.singleBindingModel;
import com.musec.musec.data.models.viewModels.single.singleViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
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
        Long id;
        try {
            id = singleService.createSingle(singleBindingModel, principal.getName());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(id);
    }

    @PostMapping("/song/{id}")
    public ResponseEntity<String> addSongToSingle(@PathVariable Long id, songBindingModel songBindingModel, Principal principal) {
        try {
            singleService.addSongToSingle(songBindingModel, id, principal.getName());
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<singleViewModel> returnSingleById(@PathVariable Long id){
        singleViewModel singleToReturn;
        try {
            singleToReturn = singleService.returnSingle(id);
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(singleToReturn);
    }
}
