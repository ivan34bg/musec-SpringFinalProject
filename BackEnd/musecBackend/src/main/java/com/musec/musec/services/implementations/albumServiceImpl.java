package com.musec.musec.services.implementations;

import com.musec.musec.entities.albumEntity;
import com.musec.musec.entities.models.albumBindingModel;
import com.musec.musec.entities.models.songBindingModel;
import com.musec.musec.entities.models.songViewModel;
import com.musec.musec.entities.songEntity;
import com.musec.musec.repositories.albumRepository;
import com.musec.musec.services.albumService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class albumServiceImpl implements albumService {
    private final albumRepository albumRepo;
    private final cloudServiceImpl cloudService;
    private final songServiceImpl songService;
    private final ModelMapper modelMapper;


    public albumServiceImpl(
            albumRepository albumRepo,
            cloudServiceImpl cloudService,
            songServiceImpl songService,
            ModelMapper modelMapper
    ) {
        this.albumRepo = albumRepo;
        this.cloudService = cloudService;
        this.songService = songService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createAlbum(albumBindingModel albumBindingModel) throws Exception {
        albumEntity newAlbum = new albumEntity();
        newAlbum.setAlbumName(albumBindingModel.getAlbumName());
        String albumPicLocation = cloudService.uploadAlbumPic(albumBindingModel.getAlbumPic());
        newAlbum.setAlbumPicLocation(albumPicLocation);
        return albumRepo.save(newAlbum).getId();
    }

    @Override
    public void addSongToAlbum(Long albumId, songBindingModel songBindingModel) throws Exception {
        Optional<albumEntity> albumToAddSongToOrNull = albumRepo.findById(albumId);
        if(albumToAddSongToOrNull.isPresent()){
            songService.saveSongWithAlbum(albumToAddSongToOrNull.get(), songBindingModel);
        }
        else {
            throw new NotFoundException("Album cannot be found.");
        }
    }

    @Override
    public List<songViewModel> returnAllSongsFromAnAlbum(Long id) throws NotFoundException {
        Optional<albumEntity> albumOrNull = albumRepo.findById(id);
        List<songViewModel> mappedSongs = new ArrayList<>();
        if(albumOrNull.isPresent()){
            for (songEntity song:albumOrNull.get().getSongs()
                 ) {
                songViewModel mappedSong = new songViewModel();
                modelMapper.map(song, mappedSong);
                mappedSongs.add(mappedSong);
            }
            return mappedSongs;
        }
        throw new NotFoundException("Album cannot be found.");
    }
}
