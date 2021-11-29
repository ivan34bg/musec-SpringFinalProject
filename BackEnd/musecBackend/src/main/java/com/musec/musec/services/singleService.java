package com.musec.musec.services;

import com.musec.musec.data.models.bindingModels.singleBindingModel;
import com.musec.musec.data.models.viewModels.single.singleViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import javassist.NotFoundException;

public interface singleService {
    Long createSingle(singleBindingModel singleBindingModel, String currentUserUsername) throws Exception;
    void addSongToSingle(songBindingModel songBindingModel, Long id, String username) throws Exception;
    void deleteSingle(Long singleId) throws NotFoundException;
    singleViewModel returnSingle(Long singleId) throws NotFoundException;
}
