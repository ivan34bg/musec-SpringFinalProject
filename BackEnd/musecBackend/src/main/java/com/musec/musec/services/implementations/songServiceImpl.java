package com.musec.musec.services.implementations;

import com.musec.musec.data.albumEntity;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.models.viewModels.album.albumSongViewModel;
import com.musec.musec.data.models.viewModels.songViewModel;
import com.musec.musec.data.singleEntity;
import com.musec.musec.data.songEntity;
import com.musec.musec.repositories.songRepository;
import com.musec.musec.services.songService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class songServiceImpl implements songService {
    private final songRepository songRepo;
    private final cloudServiceImpl cloudService;
    private final genreServiceImpl genreService;
    private userServiceImpl userService;
    private final ModelMapper modelMapper;

    public songServiceImpl(
            songRepository songRepo,
            cloudServiceImpl cloudService,
            genreServiceImpl genreService,
            ModelMapper modelMapper) {
        this.songRepo = songRepo;
        this.cloudService = cloudService;
        this.genreService = genreService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveSongWithAlbum(
            albumEntity album,
            songBindingModel songBindingModel,
            String username)
            throws
            RuntimeException,
            NotFoundException {
        //TODO: Unrepeat this code
        songEntity songToSave = new songEntity();
        songToSave.setSongName(songBindingModel.getSongName());
        String songLocation = cloudService.uploadSong(songBindingModel.getSongFile());
        songToSave.setSongLocation(songLocation);
        songToSave.setSongGenre(genreService.findGenreByName(songBindingModel.getGenre()));
        songToSave.setAlbum(album);
        songToSave.setUploader(this.userService.returnExistingUserByUsername(username));
        songRepo.save(songToSave);
    }

    @Override
    public void saveSongWithSingle(
            singleEntity single,
            songBindingModel songBindingModel,
            String username)
            throws
            RuntimeException,
            NotFoundException {
        //TODO: Unrepeat this code
        songEntity songToSave = new songEntity();
        songToSave.setSongName(songBindingModel.getSongName());
        String songLocation = cloudService.uploadSong(songBindingModel.getSongFile());
        songToSave.setSongLocation(songLocation);
        songToSave.setSongGenre(genreService.findGenreByName(songBindingModel.getGenre()));
        songToSave.setSingle(single);
        songToSave.setUploader(this.userService.returnExistingUserByUsername(username));
        songRepo.save(songToSave);
    }

    @Override
    public songEntity returnSongById(Long id) throws NotFoundException {
        Optional<songEntity> songOrNull = songRepo.findById(id);
        if(songOrNull.isPresent())
            return songOrNull.get();
        else
            throw new NotFoundException("Song cannot be find");
    }

    @Override
    public songViewModel returnSongViewModelFromEntity(songEntity song) {
        songViewModel modelToReturn = new songViewModel();
        modelMapper.map(song, modelToReturn);
        if(song.getAlbum() != null)
            modelToReturn.setAlbumOrSingleName(song.getAlbum().getAlbumName());
        else
            modelToReturn.setAlbumOrSingleName(song.getSingle().getSingleName());
        return modelToReturn;
    }

    @Override
    public Set<albumSongViewModel> returnSongViewModelSetFromFullSongSet(Set<songEntity> songs) {
        Set<albumSongViewModel> setToReturn = new HashSet<>();
        for (songEntity song :
                songs) {
            albumSongViewModel mappedSong = new albumSongViewModel();
            modelMapper.map(song, mappedSong);
            mappedSong.setSongGenreColor(song.getSongGenre().getColor());
            setToReturn.add(mappedSong);
        }
        return setToReturn;
    }
}
