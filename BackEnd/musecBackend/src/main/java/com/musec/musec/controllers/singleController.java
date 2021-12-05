package com.musec.musec.controllers;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.singleBindingModel;
import com.musec.musec.data.models.viewModels.search.singleSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.singleShortInfoViewModel;
import com.musec.musec.data.models.viewModels.single.singleViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.services.implementations.singleServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.security.Principal;
import java.util.Set;

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
        } catch (RuntimeException | DbxException e) {
            return ResponseEntity.internalServerError().build();
        } catch (RoleNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(id);
    }

    @PostMapping("/song/{singleId}")
    public ResponseEntity<String> addSongToSingle(@PathVariable Long singleId, songBindingModel songBindingModel, Principal principal) {
        try {
            singleService.addSongToSingle(songBindingModel, singleId, principal.getName());
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{singleId}")
    public ResponseEntity<singleViewModel> returnSingleById(@PathVariable Long singleId){
        singleViewModel singleToReturn;
        try {
            singleToReturn = singleService.returnSingle(singleId);
        } catch (NotFoundException e) {
            //TODO: Add exception handler and send the exception message
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(singleToReturn);
    }

    @GetMapping("")
    public ResponseEntity<Set<singleShortInfoViewModel>> returnShortInfoOfAllSingles(Principal principal){
        return ResponseEntity.ok(singleService.returnShortInfoOfSinglesOfLoggedUser(principal.getName()));
    }

    @DeleteMapping("/{singleId}")
    public ResponseEntity<?> deleteSingleById(@PathVariable Long singleId){
        try {
            singleService.deleteSingle(singleId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Set<singleSearchViewModel>> searchSingleByName(@RequestParam("param") String parameters){
        return ResponseEntity.ok(singleService.searchSingleByName(parameters));
    }
}
