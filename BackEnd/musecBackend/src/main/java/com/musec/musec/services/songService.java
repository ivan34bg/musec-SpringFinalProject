package com.musec.musec.services;

import com.musec.musec.entities.albumEntity;
import com.musec.musec.entities.models.songBindingModel;
import com.musec.musec.entities.singleEntity;

public interface songService {
    void saveSongWithAlbum(albumEntity album, songBindingModel songBindingModel) throws Exception;
    void saveSongWithSingle(singleEntity single, songBindingModel songBindingModel) throws Exception;
}
