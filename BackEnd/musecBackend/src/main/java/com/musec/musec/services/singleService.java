package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.singleBindingModel;
import com.musec.musec.data.models.viewModels.search.singleSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.singleShortInfoViewModel;
import com.musec.musec.data.models.viewModels.single.singleViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import javassist.NotFoundException;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface singleService {
    Long createSingle(singleBindingModel singleBindingModel, String currentUserUsername) throws
            RuntimeException,
            DbxException,
            RoleNotFoundException;
    void addSongToSingle(songBindingModel songBindingModel, Long id, String username) throws Exception;
    void publicDeleteSingle(Long singleId, String username) throws NotFoundException, DbxException;
    void adminDeleteSingle(Long singleId, String username) throws NotFoundException, DbxException;
    singleViewModel returnSingle(Long singleId) throws NotFoundException;
    List<singleShortInfoViewModel> returnShortInfoOfSinglesOfUserByUsername(String username);
    List<singleShortInfoViewModel> returnShortInfoOfSinglesOfUserById(Long userId) throws NotFoundException;
    List<singleSearchViewModel> searchSingleByName(String parameter);
}
