package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.albumBindingModel;
import com.musec.musec.data.models.viewModels.album.albumViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.models.viewModels.search.albumSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.albumShortInfoViewModel;
import javassist.NotFoundException;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface albumService {
    Long createAlbum(albumBindingModel albumBindingModel, String currentUserUsername) throws DbxException, RoleNotFoundException;
    void addSongToAlbum(Long id, songBindingModel songBindingModel, String username) throws Exception;
    void publicDeleteAlbum(Long albumId, String username) throws NotFoundException, DbxException;
    void adminDeleteAlbum(Long albumId, String username) throws NotFoundException, DbxException;
    albumViewModel returnAlbum(Long id) throws NotFoundException;
    List<albumShortInfoViewModel> returnShortInfoOfAllAlbumsOfUserByUsername(String username);
    List<albumShortInfoViewModel> returnShortInfoOfAllAlbumsOfUserById(Long userId) throws NotFoundException;
    List<albumSearchViewModel> searchAlbumByName(String parameter);
    void addAlbumToQueue(Long albumId, String username) throws NotFoundException;
}
