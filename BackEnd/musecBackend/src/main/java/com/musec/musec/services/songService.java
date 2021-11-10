package com.musec.musec.services;

import com.musec.musec.entities.albumEntity;
import com.musec.musec.entities.models.songBindingModel;
import com.musec.musec.entities.songEntity;
import javassist.NotFoundException;

public interface songService {
    songEntity saveSongWithAlbum(albumEntity album, songBindingModel songBindingModel) throws Exception;
}
