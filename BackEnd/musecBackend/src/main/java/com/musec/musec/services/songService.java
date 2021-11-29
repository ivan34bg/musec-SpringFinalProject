package com.musec.musec.services;

import com.musec.musec.data.albumEntity;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.models.viewModels.album.albumSongViewModel;
import com.musec.musec.data.models.viewModels.songViewModel;
import com.musec.musec.data.singleEntity;
import com.musec.musec.data.songEntity;
import javassist.NotFoundException;

import java.util.Set;

public interface songService {
    void saveSongWithAlbum(albumEntity album, songBindingModel songBindingModel, String username) throws Exception;
    void saveSongWithSingle(singleEntity single, songBindingModel songBindingModel, String username) throws Exception;
    songEntity returnSongById(Long id) throws NotFoundException;
    songViewModel returnSongViewModelFromEntity(songEntity song);
    Set<albumSongViewModel> returnSongViewModelSetFromFullSongSet(Set<songEntity> songs);
}
