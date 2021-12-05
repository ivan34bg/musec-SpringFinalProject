package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.albumBindingModel;
import com.musec.musec.data.models.viewModels.album.albumViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.models.viewModels.search.albumSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.albumShortInfoViewModel;
import javassist.NotFoundException;

import javax.management.relation.RoleNotFoundException;
import java.util.Set;

public interface albumService {
    Long createAlbum(albumBindingModel albumBindingModel, String currentUserUsername) throws DbxException, RoleNotFoundException;
    void addSongToAlbum(Long id, songBindingModel songBindingModel, String username) throws Exception;
    void deleteAlbum(Long albumId) throws NotFoundException, DbxException;
    albumViewModel returnAlbum(Long id) throws NotFoundException;
    Set<albumShortInfoViewModel> returnShortInfoOfAllAlbumsOfLoggedUser(String username);
    Set<albumSearchViewModel> searchAlbumByName(String parameter);
}
