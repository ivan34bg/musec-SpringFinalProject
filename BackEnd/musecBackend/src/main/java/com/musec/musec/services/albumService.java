package com.musec.musec.services;

import com.musec.musec.entities.models.albumBindingModel;
import com.musec.musec.entities.models.songBindingModel;
import com.musec.musec.entities.models.songViewModel;
import javassist.NotFoundException;

import java.util.List;

public interface albumService {
    Long createAlbum(albumBindingModel albumBindingModel) throws Exception;
    void addSongToAlbum(Long id, songBindingModel songBindingModel) throws Exception;
    List<songViewModel> returnAllSongsFromAnAlbum(Long id) throws NotFoundException;
}
