package com.musec.musec.controllers;

import com.musec.musec.data.models.viewModels.genre.genreExpandedInfoViewModel;
import com.musec.musec.data.models.viewModels.genre.genreShortInfoViewModel;
import com.musec.musec.services.implementations.genreServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/genre")
public class genreController {
    private final genreServiceImpl genreService;

    public genreController(genreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/short")
    public ResponseEntity<Set<genreShortInfoViewModel>> loadNineGenres(){
        return ResponseEntity.ok(genreService.loadNineGenres());
    }

    @GetMapping("/short/all")
    public ResponseEntity<Set<genreShortInfoViewModel>> loadAllGenres(){
        return ResponseEntity.ok(genreService.loadShortAllGenres());
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<genreExpandedInfoViewModel> returnGenreAndSongsById(@PathVariable Long genreId){
        genreExpandedInfoViewModel genreToReturn;
        try {
            genreToReturn = genreService.loadSongsByGenreId(genreId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genreToReturn);
    }
}
