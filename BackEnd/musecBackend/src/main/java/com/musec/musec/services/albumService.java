package com.musec.musec.services;

import com.musec.musec.entities.models.albumBindingModel;
import com.musec.musec.entities.models.albumViewModel;
import com.musec.musec.entities.models.songBindingModel;
import javassist.NotFoundException;

public interface albumService {
    Long createAlbum(albumBindingModel albumBindingModel, String currentUserUsername) throws Exception;
    void addSongToAlbum(Long id, songBindingModel songBindingModel) throws Exception;
    albumViewModel returnAlbum(Long id) throws NotFoundException;
}
