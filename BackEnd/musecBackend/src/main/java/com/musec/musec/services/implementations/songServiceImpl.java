package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.albumEntity;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.models.viewModels.album.albumSongViewModel;
import com.musec.musec.data.models.viewModels.search.songSearchViewModel;
import com.musec.musec.data.models.viewModels.songViewModel;
import com.musec.musec.data.singleEntity;
import com.musec.musec.data.songEntity;
import com.musec.musec.repositories.songRepository;
import com.musec.musec.services.songService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
            NotFoundException, DbxException {
        //TODO: Unrepeat this code
        songEntity songToSave = new songEntity();
        songToSave.setSongName(songBindingModel.getSongName());
        String songFilePath = cloudService.uploadSong(songBindingModel.getSongFile());
        songToSave.setSongFilePath(songFilePath);
        songToSave.setSongLocation(cloudService.returnDirectLinkOfFile(songFilePath));
        songToSave.setSongGenre(genreService.findGenreByName(songBindingModel.getGenre()));
        songToSave.setAlbum(album);
        songToSave.setUploader(userService.returnExistingUserByUsername(username));
        songRepo.save(songToSave);
    }

    @Override
    public void saveSongWithSingle(
            singleEntity single,
            songBindingModel songBindingModel,
            String username)
            throws
            RuntimeException,
            NotFoundException, DbxException {
        //TODO: Unrepeat this code
        songEntity songToSave = new songEntity();
        songToSave.setSongName(songBindingModel.getSongName());
        String songFilePath = cloudService.uploadSong(songBindingModel.getSongFile());
        songToSave.setSongFilePath(songFilePath);
        songToSave.setSongLocation(cloudService.returnDirectLinkOfFile(songFilePath));
        songToSave.setSongGenre(genreService.findGenreByName(songBindingModel.getGenre()));
        songToSave.setSingle(single);
        songToSave.setUploader(userService.returnExistingUserByUsername(username));
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

    @Override
    public void deleteSongById(Long songId) throws DbxException {
        songEntity song = songRepo.findById(songId).get();
        cloudService.deleteFile(song.getSongFilePath());
        songRepo.delete(song);
    }

    @Override
    public Set<songSearchViewModel> searchSongBySongName(String parameters) {
        Set<songSearchViewModel> setToReturn = new HashSet<>();
        if(!parameters.trim().equals("")){
            Optional<Set<songEntity>> songsOrNull = songRepo.findAllBySongNameContains(parameters);
            if(!songsOrNull.get().isEmpty()){
                for (songEntity song:songsOrNull.get()
                     ) {
                    songSearchViewModel mappedSong = new songSearchViewModel();
                    modelMapper.map(song, mappedSong);
                    if(song.getAlbum() != null){
                        mappedSong.setAlbumId(song.getAlbum().getId());
                    }
                    if(song.getSingle() != null){
                        mappedSong.setSingleId(song.getSingle().getId());
                    }
                    setToReturn.add(mappedSong);
                }
            }
        }
        return setToReturn;
    }

    @Autowired
    public void setUserService(@Lazy userServiceImpl userService){
        this.userService = userService;
    }
}
