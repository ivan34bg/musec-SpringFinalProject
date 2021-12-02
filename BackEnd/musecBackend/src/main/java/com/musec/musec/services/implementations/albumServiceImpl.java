package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.albumEntity;
import com.musec.musec.data.models.bindingModels.albumBindingModel;
import com.musec.musec.data.models.viewModels.album.albumViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.models.viewModels.shortInfo.albumShortInfoViewModel;
import com.musec.musec.data.songEntity;
import com.musec.musec.repositories.albumRepository;
import com.musec.musec.services.albumService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class albumServiceImpl implements albumService {
    private final albumRepository albumRepo;
    private final cloudServiceImpl cloudService;
    private final songServiceImpl songService;
    private final userServiceImpl userService;
    private final queueServiceImpl queueService;
    private final ModelMapper modelMapper;


    public albumServiceImpl(
            albumRepository albumRepo,
            cloudServiceImpl cloudService,
            songServiceImpl songService,
            userServiceImpl userService, queueServiceImpl queueService, ModelMapper modelMapper
    ) {
        this.albumRepo = albumRepo;
        this.cloudService = cloudService;
        this.songService = songService;
        this.userService = userService;
        this.queueService = queueService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createAlbum(albumBindingModel albumBindingModel, String currentUserUsername) throws DbxException {
        albumEntity newAlbum = new albumEntity();
        newAlbum.setAlbumName(albumBindingModel.getAlbumName());
        String albumPicFilePath = cloudService.uploadAlbumPic(albumBindingModel.getAlbumPic());
        newAlbum.setAlbumPicFilePath(albumPicFilePath);
        newAlbum.setAlbumPicLocation(cloudService.returnDirectLinkOfFile(albumPicFilePath));
        newAlbum.setUploader(userService.returnExistingUserByUsername(currentUserUsername));
        return albumRepo.save(newAlbum).getId();
    }

    @Override
    public void addSongToAlbum(Long albumId, songBindingModel songBindingModel, String username) throws
            NotFoundException,
            DbxException {
        albumEntity album = isAlbumPresent(albumId);
        songService.saveSongWithAlbum(album, songBindingModel, username);
    }

    @Override
    public void deleteAlbum(Long albumId) throws NotFoundException, DbxException {
        albumEntity albumToDelete = isAlbumPresent(albumId);
        for (songEntity song:albumToDelete.getSongs()
             ) {
            queueService.removeSongFromEveryQueue(song);
            songService.deleteSongById(song.getId());
        }
        cloudService.deleteFile(albumToDelete.getAlbumPicFilePath());
        albumRepo.delete(albumToDelete);
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
    public Set<albumShortInfoViewModel> returnShortInfoOfAllAlbumsOfLoggedUser(String username) {
        Optional<Set<albumEntity>> albumsOrNull = albumRepo.findAllByUploader_Username(username);
        Set<albumShortInfoViewModel> setToReturn = new HashSet<>();
        for (albumEntity album:albumsOrNull.get()
             ) {
            albumShortInfoViewModel mappedAlbum = new albumShortInfoViewModel();
            modelMapper.map(album, mappedAlbum);
            setToReturn.add(mappedAlbum);
        }
        return setToReturn;
    }

    private albumEntity isAlbumPresent(Long albumId) throws NotFoundException {
        Optional<albumEntity> albumOrNull = albumRepo.findById(albumId);
        if(albumOrNull.isPresent()) {
            return albumOrNull.get();
        }
        throw new NotFoundException("Album cannot be found.");
    }
}
