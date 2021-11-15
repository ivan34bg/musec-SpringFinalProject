package com.musec.musec.services;

import com.musec.musec.data.models.bindingModels.albumBindingModel;
import com.musec.musec.data.models.viewModels.albumViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import javassist.NotFoundException;

public interface albumService {
    Long createAlbum(albumBindingModel albumBindingModel, String currentUserUsername) throws Exception;
    void addSongToAlbum(Long id, songBindingModel songBindingModel) throws Exception;
    void deleteAlbum(Long albumId) throws NotFoundException;
    albumViewModel returnAlbum(Long id) throws NotFoundException;
}
