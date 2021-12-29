package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.SingleBindingModel;
import com.musec.musec.data.models.viewModels.search.SingleSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.SingleShortInfoViewModel;
import com.musec.musec.data.models.viewModels.single.SingleViewModel;
import com.musec.musec.data.models.bindingModels.SongBindingModel;
import javassist.NotFoundException;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface SingleService {
    Long createSingle(SingleBindingModel singleBindingModel, String currentUserUsername) throws
            RuntimeException,
            DbxException,
            RoleNotFoundException;
    void addSongToSingle(SongBindingModel songBindingModel, Long id, String username) throws Exception;
    void publicDeleteSingle(Long singleId, String username) throws NotFoundException, DbxException;
    void adminDeleteSingle(Long singleId, String username) throws NotFoundException, DbxException;
    SingleViewModel returnSingle(Long singleId) throws NotFoundException;
    List<SingleShortInfoViewModel> returnShortInfoOfSinglesOfUserByUsername(String username);
    List<SingleShortInfoViewModel> returnShortInfoOfSinglesOfUserById(Long userId) throws NotFoundException;
    List<SingleSearchViewModel> searchSingleByName(String parameter);
}
