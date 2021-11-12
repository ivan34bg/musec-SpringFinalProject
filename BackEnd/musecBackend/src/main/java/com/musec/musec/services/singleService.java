package com.musec.musec.services;

import com.musec.musec.entities.models.singleBindingModel;
import com.musec.musec.entities.models.singleViewModel;
import com.musec.musec.entities.models.songBindingModel;
import javassist.NotFoundException;

public interface singleService {
    Long createSingle(singleBindingModel singleBindingModel, String currentUserUsername) throws Exception;
    void addSongToSingle(songBindingModel songBindingModel, Long id) throws Exception;
    singleViewModel returnSingle(Long singleId) throws NotFoundException;
}
