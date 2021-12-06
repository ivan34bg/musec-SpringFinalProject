package com.musec.musec.controllers;

import com.musec.musec.data.models.viewModels.search.songSearchViewModel;
import com.musec.musec.data.models.viewModels.top10songs.songNewestTenViewModel;
import com.musec.musec.services.implementations.songServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/song")
public class songController {
    private final songServiceImpl songService;

    public songController(songServiceImpl songService) {
        this.songService = songService;
    }

    @GetMapping("/search")
    public ResponseEntity<Set<songSearchViewModel>> searchSongByName(@RequestParam(name = "param") String parameter){
        return ResponseEntity.ok(songService.searchSongBySongName(parameter));
    }

    @GetMapping("/newest-ten")
    public ResponseEntity<Set<songNewestTenViewModel>> returnNewestTenSongs(){
        return ResponseEntity.ok(songService.loadNewestTenSongs());
    }
}
