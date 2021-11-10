package com.musec.musec.services.implementations;

import com.musec.musec.entities.albumEntity;
import com.musec.musec.entities.models.songBindingModel;
import com.musec.musec.entities.songEntity;
import com.musec.musec.repositories.songRepository;
import com.musec.musec.services.songService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class songServiceImpl implements songService {
    private final songRepository songRepo;
    private final cloudServiceImpl cloudService;
    private final genreServiceImpl genreService;

    public songServiceImpl(songRepository songRepo, cloudServiceImpl cloudService, genreServiceImpl genreService) {
        this.songRepo = songRepo;
        this.cloudService = cloudService;
        this.genreService = genreService;
    }

    @Override
    public songEntity saveSongWithAlbum(albumEntity album, songBindingModel songBindingModel) throws Exception {
        songEntity songToSave = new songEntity();
        songToSave.setSongName(songBindingModel.getSongName());
        String songLocation = cloudService.uploadSong(songBindingModel.getSongFile());
        songToSave.setSongLocation(songLocation);
        songToSave.setSongGenre(genreService.findGenreByName(songBindingModel.getGenre()));
        songToSave.setAlbum(album);
        return songRepo.save(songToSave);
    }
}
