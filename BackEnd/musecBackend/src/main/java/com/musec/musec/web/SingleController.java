package com.musec.musec.web;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.SingleBindingModel;
import com.musec.musec.data.models.viewModels.search.SingleSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.SingleShortInfoViewModel;
import com.musec.musec.data.models.viewModels.single.SingleViewModel;
import com.musec.musec.data.models.bindingModels.SongBindingModel;
import com.musec.musec.services.implementations.SingleServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/single")
public class SingleController {
    private final SingleServiceImpl singleService;

    public SingleController(SingleServiceImpl singleService) {
        this.singleService = singleService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createNewSingle(SingleBindingModel singleBindingModel, Principal principal) {
        Long singleId;
        try {
            if (singleBindingModel.getSingleName().trim().length() > 0)
            singleId = singleService.createSingle(singleBindingModel, principal.getName());
            else return ResponseEntity.badRequest().build();
        } catch (RuntimeException | DbxException e) {
            return ResponseEntity.internalServerError().build();
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.created(URI.create("/single/" + singleId)).build();
    }

    @PostMapping("/{singleId}/song")
    public ResponseEntity<String> addSongToSingle(@PathVariable Long singleId, SongBindingModel songBindingModel, Principal principal) {
        try {
            singleService.addSongToSingle(songBindingModel, singleId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{singleId}")
    public ResponseEntity<SingleViewModel> returnSingleById(@PathVariable Long singleId){
        SingleViewModel singleToReturn;
        try {
            singleToReturn = singleService.returnSingle(singleId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(singleToReturn);
    }

    @GetMapping("/user")
    public ResponseEntity<List<SingleShortInfoViewModel>> returnShortInfoOfAllSingles(Principal principal){
        return ResponseEntity.ok(singleService.returnShortInfoOfSinglesOfUserByUsername(principal.getName()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SingleShortInfoViewModel>> returnShortInfoOfSinglesOfUserById(@PathVariable Long userId){
        List<SingleShortInfoViewModel> singles;
        try {
            singles = singleService.returnShortInfoOfSinglesOfUserById(userId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(singles);
    }

    @DeleteMapping("/{singleId}")
    public ResponseEntity<?> deleteSingleById(@PathVariable Long singleId, Principal principal){
        try {
            singleService.publicDeleteSingle(singleId, principal.getName());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DbxException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SingleSearchViewModel>> searchSingleByName(@RequestParam("param") String parameters){
        return ResponseEntity.ok(singleService.searchSingleByName(parameters));
    }
}
