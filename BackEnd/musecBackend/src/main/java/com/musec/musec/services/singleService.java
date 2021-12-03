package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.singleBindingModel;
import com.musec.musec.data.models.viewModels.search.singleSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.singleShortInfoViewModel;
import com.musec.musec.data.models.viewModels.single.singleViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import javassist.NotFoundException;

import java.util.Set;

public interface singleService {
    Long createSingle(singleBindingModel singleBindingModel, String currentUserUsername) throws Exception;
    void addSongToSingle(songBindingModel songBindingModel, Long id, String username) throws Exception;
    void deleteSingle(Long singleId) throws NotFoundException, DbxException;
    singleViewModel returnSingle(Long singleId) throws NotFoundException;
    Set<singleShortInfoViewModel> returnShortInfoOfSinglesOfLoggedUser(String username);
    Set<singleSearchViewModel> searchSingleByName(String parameter);
}
