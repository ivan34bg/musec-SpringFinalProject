package com.musec.musec.web;

import com.musec.musec.data.models.viewModels.genre.GenreExpandedInfoViewModel;
import com.musec.musec.data.models.viewModels.genre.GenreShortInfoViewModel;
import com.musec.musec.services.implementations.GenreServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/genre")
public class GenreController {
    private final GenreServiceImpl genreService;

    public GenreController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/short")
    public ResponseEntity<List<GenreShortInfoViewModel>> loadNineGenres(){
        return ResponseEntity.ok(genreService.loadNineGenres());
    }

    @GetMapping("/short/all")
    public ResponseEntity<List<GenreShortInfoViewModel>> loadAllGenres(){
        return ResponseEntity.ok(genreService.loadShortAllGenres());
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<GenreExpandedInfoViewModel> returnGenreAndSongsById(@PathVariable Long genreId){
        GenreExpandedInfoViewModel genreToReturn;
        try {
            genreToReturn = genreService.loadSongsByGenreId(genreId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genreToReturn);
    }
}
