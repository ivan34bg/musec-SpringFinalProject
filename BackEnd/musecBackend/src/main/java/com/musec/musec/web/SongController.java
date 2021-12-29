package com.musec.musec.web;

import com.musec.musec.data.models.viewModels.search.SongSearchViewModel;
import com.musec.musec.data.models.viewModels.top10songs.SongNewestTenViewModel;
import com.musec.musec.services.implementations.SongServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/song")
public class SongController {
    private final SongServiceImpl songService;

    public SongController(SongServiceImpl songService) {
        this.songService = songService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<SongSearchViewModel>> searchSongByName(@RequestParam(name = "param") String parameter){
        return ResponseEntity.ok(songService.searchSongBySongName(parameter));
    }

    @GetMapping("/newest-ten")
    public ResponseEntity<List<SongNewestTenViewModel>> returnNewestTenSongs(){
        return ResponseEntity.ok(songService.loadNewestTenSongs());
    }
}
