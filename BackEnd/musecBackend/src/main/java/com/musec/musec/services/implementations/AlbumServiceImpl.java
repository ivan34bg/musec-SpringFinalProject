package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.AlbumEntity;
import com.musec.musec.data.enums.RoleEnum;
import com.musec.musec.data.models.bindingModels.AlbumBindingModel;
import com.musec.musec.data.models.viewModels.album.AlbumViewModel;
import com.musec.musec.data.models.bindingModels.SongBindingModel;
import com.musec.musec.data.models.viewModels.search.AlbumSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.AlbumShortInfoViewModel;
import com.musec.musec.data.SongEntity;
import com.musec.musec.data.UserEntity;
import com.musec.musec.repositories.AlbumRepository;
import com.musec.musec.services.AlbumService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepo;
    private final CloudServiceImpl cloudService;
    private final SongServiceImpl songService;
    private final UserServiceImpl userService;
    private final QueueServiceImpl queueService;
    private final PlaylistServiceImpl playlistService;
    private final ModelMapper modelMapper;


    public AlbumServiceImpl(
            AlbumRepository albumRepo,
            CloudServiceImpl cloudService,
            SongServiceImpl songService,
            UserServiceImpl userService, QueueServiceImpl queueService, PlaylistServiceImpl playlistService, ModelMapper modelMapper
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
    public Long createAlbum(AlbumBindingModel albumBindingModel, String currentUserUsername) throws
            DbxException,
            RoleNotFoundException {
        UserEntity user = userService.returnExistingUserByUsername(currentUserUsername);
        if(user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(RoleEnum.ARTIST))){
            AlbumEntity newAlbum = new AlbumEntity();
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
    public void addSongToAlbum(Long albumId, SongBindingModel songBindingModel, String username) throws
            NotFoundException,
            DbxException {
        AlbumEntity album = isAlbumPresent(albumId);
        songService.saveSongWithAlbum(album, songBindingModel, username);
    }

    @Override
    public void publicDeleteAlbum(Long albumId, String username) throws
            NotFoundException,
            DbxException,
            IllegalCallerException {
        AlbumEntity albumToDelete = isAlbumPresent(albumId);
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
    public AlbumViewModel returnAlbum(Long albumId) throws NotFoundException {
        AlbumEntity album = isAlbumPresent(albumId);
        AlbumViewModel albumToReturn = new AlbumViewModel();
        modelMapper.map(album, albumToReturn);
        albumToReturn.setSongs(songService.returnSongViewModelSetFromFullSongSet(album.getSongs()));
        return albumToReturn;
    }

    @Override
    public List<AlbumShortInfoViewModel> returnShortInfoOfAllAlbumsOfUserByUsername(String username) {
        Optional<List<AlbumEntity>> albumsOrNull = albumRepo.findAllByUploader_Username(username);
        List<AlbumShortInfoViewModel> setToReturn = new ArrayList<>();
        for (AlbumEntity album:albumsOrNull.get()
             ) {
            AlbumShortInfoViewModel mappedAlbum = new AlbumShortInfoViewModel();
            modelMapper.map(album, mappedAlbum);
            setToReturn.add(mappedAlbum);
        }
        return setToReturn;
    }

    @Override
    public List<AlbumShortInfoViewModel> returnShortInfoOfAllAlbumsOfUserById(Long userId) throws NotFoundException {
        UserEntity user = userService.returnUserById(userId);
        return  returnShortInfoOfAllAlbumsOfUserByUsername(user.getUsername());
    }

    @Override
    public List<AlbumSearchViewModel> searchAlbumByName(String parameter) {
        List<AlbumSearchViewModel> setToReturn = new ArrayList<>();
        if(!parameter.trim().equals("")){
            Optional<List<AlbumEntity>> albumsOrNull = albumRepo.findAllByAlbumNameContains(parameter);
            if(!albumsOrNull.get().isEmpty()){
                for (AlbumEntity album:albumsOrNull.get()
                     ) {
                    AlbumSearchViewModel mappedAlbum = new AlbumSearchViewModel();
                    modelMapper.map(album, mappedAlbum);
                    setToReturn.add(mappedAlbum);
                }
            }
        }
        return setToReturn;
    }

    @Override
    public void addAlbumToQueue(Long albumId, String username) throws NotFoundException {
        Optional<AlbumEntity> albumOrNull = albumRepo.findById(albumId);
        if (albumOrNull.isPresent()){
            queueService.addCollectionToQueue(albumOrNull.get().getSongs(), username);
        }
        else throw new NotFoundException("Album cannot be found");

    }

    private AlbumEntity isAlbumPresent(Long albumId) throws NotFoundException {
        Optional<AlbumEntity> albumOrNull = albumRepo.findById(albumId);
        if(albumOrNull.isPresent()) {
            return albumOrNull.get();
        }
        throw new NotFoundException("Album cannot be found.");
    }

    protected void deleteAlbum(Long albumId) throws NotFoundException, DbxException {
        AlbumEntity albumToDelete = isAlbumPresent(albumId);
        for (SongEntity song:albumToDelete.getSongs()
        ) {
            playlistService.removeSongFromEveryPlaylist(song);
            queueService.removeSongFromEveryQueue(song);
            songService.deleteSongById(song.getId());
        }
        cloudService.deleteFile(albumToDelete.getAlbumPicFilePath());
        albumRepo.delete(albumToDelete);
    }
}
