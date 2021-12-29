package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.AlbumEntity;
import com.musec.musec.data.models.bindingModels.SongBindingModel;
import com.musec.musec.data.models.viewModels.album.AlbumSongViewModel;
import com.musec.musec.data.models.viewModels.search.SongSearchViewModel;
import com.musec.musec.data.models.viewModels.top10songs.SongNewestTenViewModel;
import com.musec.musec.data.SingleEntity;
import com.musec.musec.data.SongEntity;
import com.musec.musec.repositories.SongRepository;
import com.musec.musec.services.SongService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepo;
    private final CloudServiceImpl cloudService;
    private final GenreServiceImpl genreService;
    private UserServiceImpl userService;
    private final ModelMapper modelMapper;

    public SongServiceImpl(
            SongRepository songRepo,
            CloudServiceImpl cloudService,
            GenreServiceImpl genreService,
            ModelMapper modelMapper) {
        this.songRepo = songRepo;
        this.cloudService = cloudService;
        this.genreService = genreService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveSongWithAlbum(
            AlbumEntity album,
            SongBindingModel songBindingModel,
            String username)
            throws
            RuntimeException,
            NotFoundException, DbxException {
        SongEntity songToSave = formSongEntity(songBindingModel);
        songToSave.setAlbum(album);
        songToSave.setUploader(userService.returnExistingUserByUsername(username));
        songRepo.save(songToSave);
    }

    @Override
    public void saveSongWithSingle(
            SingleEntity single,
            SongBindingModel songBindingModel,
            String username)
            throws
            RuntimeException,
            NotFoundException, DbxException {
        SongEntity songToSave = formSongEntity(songBindingModel);
        songToSave.setSingle(single);
        songToSave.setUploader(userService.returnExistingUserByUsername(username));
        songRepo.save(songToSave);
    }

    @Override
    public SongEntity returnSongById(Long id) throws NotFoundException {
        Optional<SongEntity> songOrNull = songRepo.findById(id);
        if(songOrNull.isPresent())
            return songOrNull.get();
        else
            throw new NotFoundException("Song cannot be find");
    }

    @Override
    public List<AlbumSongViewModel> returnSongViewModelSetFromFullSongSet(List<SongEntity> songs) {
        List<AlbumSongViewModel> setToReturn = new ArrayList<>();
        for (SongEntity song :
                songs) {
            AlbumSongViewModel mappedSong = new AlbumSongViewModel();
            modelMapper.map(song, mappedSong);
            setToReturn.add(mappedSong);
        }
        return setToReturn;
    }

    @Override
    public void deleteSongById(Long songId) throws DbxException {
        SongEntity song = songRepo.findById(songId).get();
        cloudService.deleteFile(song.getSongFilePath());
        songRepo.delete(song);
    }

    @Override
    public List<SongSearchViewModel> searchSongBySongName(String parameters) {
        List<SongSearchViewModel> setToReturn = new ArrayList<>();
        if(!parameters.trim().equals("")){
            Optional<List<SongEntity>> songsOrNull = songRepo.findAllBySongNameContains(parameters);
            if(!songsOrNull.get().isEmpty()){
                for (SongEntity song:songsOrNull.get()
                     ) {
                    SongSearchViewModel mappedSong = new SongSearchViewModel();
                    modelMapper.map(song, mappedSong);
                    if(song.getAlbum() != null){
                        mappedSong.setAlbumId(song.getAlbum().getId());
                        mappedSong.setAlbumName(song.getAlbum().getAlbumName());
                    }
                    if(song.getSingle() != null){
                        mappedSong.setSingleId(song.getSingle().getId());
                        mappedSong.setSingleName(song.getSingle().getSingleName());
                    }
                    setToReturn.add(mappedSong);
                }
            }
        }
        return setToReturn;
    }

    @Override
    public List<SongNewestTenViewModel> loadNewestTenSongs() {
        List<SongEntity> songs = songRepo.getTop10ByOrderByIdDesc();
        List<SongNewestTenViewModel> setToReturn = new ArrayList<>();
        for (SongEntity song :
                songs) {
            SongNewestTenViewModel mappedSong = new SongNewestTenViewModel();
            modelMapper.map(song, mappedSong);
            setToReturn.add(mappedSong);
        }
        return setToReturn;
    }

    @Autowired
    public void setUserService(@Lazy UserServiceImpl userService){
        this.userService = userService;
    }
    private SongEntity formSongEntity(SongBindingModel songBindingModel) throws DbxException, NotFoundException {
        SongEntity songToSave = new SongEntity();
        songToSave.setSongName(songBindingModel.getSongName());
        String songFilePath = cloudService.uploadSong(songBindingModel.getSongFile());
        songToSave.setSongFilePath(songFilePath);
        songToSave.setSongLocation(cloudService.returnDirectLinkOfFile(songFilePath));
        songToSave.setSongGenre(genreService.findGenreByName(songBindingModel.getGenre()));
        return songToSave;
    }
}
