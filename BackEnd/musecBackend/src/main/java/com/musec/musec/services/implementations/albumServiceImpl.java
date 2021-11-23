package com.musec.musec.services.implementations;

import com.musec.musec.data.albumEntity;
import com.musec.musec.data.models.bindingModels.albumBindingModel;
import com.musec.musec.data.models.viewModels.album.albumViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.repositories.albumRepository;
import com.musec.musec.services.albumService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class albumServiceImpl implements albumService {
    private final albumRepository albumRepo;
    private final cloudServiceImpl cloudService;
    private final songServiceImpl songService;
    private final userServiceImpl userService;
    private final ModelMapper modelMapper;


    public albumServiceImpl(
            albumRepository albumRepo,
            cloudServiceImpl cloudService,
            songServiceImpl songService,
            userServiceImpl userService, ModelMapper modelMapper
    ) {
        this.albumRepo = albumRepo;
        this.cloudService = cloudService;
        this.songService = songService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createAlbum(albumBindingModel albumBindingModel, String currentUserUsername) {
        albumEntity newAlbum = new albumEntity();
        newAlbum.setAlbumName(albumBindingModel.getAlbumName());
        String albumPicLocation = cloudService.uploadAlbumPic(albumBindingModel.getAlbumPic());
        newAlbum.setAlbumPicLocation(albumPicLocation);
        newAlbum.setUploader(userService.returnExistingUserByUsername(currentUserUsername));
        return albumRepo.save(newAlbum).getId();
    }

    @Override
    public void addSongToAlbum(Long albumId, songBindingModel songBindingModel) throws NotFoundException {
        albumEntity album = isAlbumPresent(albumId);
        songService.saveSongWithAlbum(album, songBindingModel);
    }

    @Override
    public void deleteAlbum(Long albumId) throws NotFoundException {
        albumEntity albumToDelete = isAlbumPresent(albumId);
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

    private albumEntity isAlbumPresent(Long albumId) throws NotFoundException {
        Optional<albumEntity> albumOrNull = albumRepo.findById(albumId);
        if(albumOrNull.isPresent()) {
            return albumOrNull.get();
        }
        throw new NotFoundException("Album cannot be found.");
    }
}
