package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.albumEntity;
import com.musec.musec.data.enums.roleEnum;
import com.musec.musec.data.models.bindingModels.albumBindingModel;
import com.musec.musec.data.models.viewModels.album.albumViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.models.viewModels.search.albumSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.albumShortInfoViewModel;
import com.musec.musec.data.songEntity;
import com.musec.musec.data.userEntity;
import com.musec.musec.repositories.albumRepository;
import com.musec.musec.services.albumService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

@Service
public class albumServiceImpl implements albumService {
    private final albumRepository albumRepo;
    private final cloudServiceImpl cloudService;
    private final songServiceImpl songService;
    private final userServiceImpl userService;
    private final queueServiceImpl queueService;
    private final playlistServiceImpl playlistService;
    private final ModelMapper modelMapper;


    public albumServiceImpl(
            albumRepository albumRepo,
            cloudServiceImpl cloudService,
            songServiceImpl songService,
            userServiceImpl userService, queueServiceImpl queueService, playlistServiceImpl playlistService, ModelMapper modelMapper
    ) {
        this.albumRepo = albumRepo;
        this.cloudService = cloudService;
        this.songService = songService;
        this.userService = userService;
        this.queueService = queueService;
        this.playlistService = playlistService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createAlbum(albumBindingModel albumBindingModel, String currentUserUsername) throws
            DbxException,
            RoleNotFoundException {
        userEntity user = userService.returnExistingUserByUsername(currentUserUsername);
        if(user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(roleEnum.ARTIST))){
            albumEntity newAlbum = new albumEntity();
            newAlbum.setAlbumName(albumBindingModel.getAlbumName());
            String albumPicFilePath = cloudService.uploadAlbumPic(albumBindingModel.getAlbumPic());
            newAlbum.setAlbumPicFilePath(albumPicFilePath);
            newAlbum.setAlbumPicLocation(cloudService.returnDirectLinkOfFile(albumPicFilePath));
            newAlbum.setUploader(userService.returnExistingUserByUsername(currentUserUsername));
            return albumRepo.save(newAlbum).getId();
        }
        throw new RoleNotFoundException("User isn't an artist");
    }

    @Override
    public void addSongToAlbum(Long albumId, songBindingModel songBindingModel, String username) throws
            NotFoundException,
            DbxException {
        albumEntity album = isAlbumPresent(albumId);
        songService.saveSongWithAlbum(album, songBindingModel, username);
    }

    @Override
    public void publicDeleteAlbum(Long albumId, String username) throws
            NotFoundException,
            DbxException,
            IllegalCallerException {
        albumEntity albumToDelete = isAlbumPresent(albumId);
        if(albumToDelete.getUploader().getUsername().equals(username)){
            deleteAlbum(albumId);
        }
        else throw new IllegalCallerException("Only the uploader of a single can delete it");
    }

    @Override
    public void adminDeleteAlbum(Long albumId, String username) throws NotFoundException, DbxException {
        if(userService.isUserAdmin(username)){
            deleteAlbum(albumId);
        }
    }

    @Override
    public albumViewModel returnAlbum(Long albumId) throws NotFoundException {
        albumEntity album = isAlbumPresent(albumId);
        albumViewModel albumToReturn = new albumViewModel();
        modelMapper.map(album, albumToReturn);
        albumToReturn.setSongs(songService.returnSongViewModelSetFromFullSongSet(album.getSongs()));
        return albumToReturn;
    }

    @Override
    public List<albumShortInfoViewModel> returnShortInfoOfAllAlbumsOfUserByUsername(String username) {
        Optional<List<albumEntity>> albumsOrNull = albumRepo.findAllByUploader_Username(username);
        List<albumShortInfoViewModel> setToReturn = new ArrayList<>();
        for (albumEntity album:albumsOrNull.get()
             ) {
            albumShortInfoViewModel mappedAlbum = new albumShortInfoViewModel();
            modelMapper.map(album, mappedAlbum);
            setToReturn.add(mappedAlbum);
        }
        return setToReturn;
    }

    @Override
    public List<albumShortInfoViewModel> returnShortInfoOfAllAlbumsOfUserById(Long userId) throws NotFoundException {
        userEntity user = userService.returnUserById(userId);
        return  returnShortInfoOfAllAlbumsOfUserByUsername(user.getUsername());
    }

    @Override
    public List<albumSearchViewModel> searchAlbumByName(String parameter) {
        List<albumSearchViewModel> setToReturn = new ArrayList<>();
        if(!parameter.trim().equals("")){
            Optional<List<albumEntity>> albumsOrNull = albumRepo.findAllByAlbumNameContains(parameter);
            if(!albumsOrNull.get().isEmpty()){
                for (albumEntity album:albumsOrNull.get()
                     ) {
                    albumSearchViewModel mappedAlbum = new albumSearchViewModel();
                    modelMapper.map(album, mappedAlbum);
                    setToReturn.add(mappedAlbum);
                }
            }
        }
        return setToReturn;
    }

    @Override
    public void addAlbumToQueue(Long albumId, String username) throws NotFoundException {
        Optional<albumEntity> albumOrNull = albumRepo.findById(albumId);
        if (albumOrNull.isPresent()){
            queueService.addCollectionToQueue(albumOrNull.get().getSongs(), username);
        }
        else throw new NotFoundException("Album cannot be found");

    }

    private albumEntity isAlbumPresent(Long albumId) throws NotFoundException {
        Optional<albumEntity> albumOrNull = albumRepo.findById(albumId);
        if(albumOrNull.isPresent()) {
            return albumOrNull.get();
        }
        throw new NotFoundException("Album cannot be found.");
    }

    protected void deleteAlbum(Long albumId) throws NotFoundException, DbxException {
        albumEntity albumToDelete = isAlbumPresent(albumId);
        for (songEntity song:albumToDelete.getSongs()
        ) {
            playlistService.removeSongFromEveryPlaylist(song);
            queueService.removeSongFromEveryQueue(song);
            songService.deleteSongById(song.getId());
        }
        cloudService.deleteFile(albumToDelete.getAlbumPicFilePath());
        albumRepo.delete(albumToDelete);
    }
}
