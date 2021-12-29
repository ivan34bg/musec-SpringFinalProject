package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.AlbumBindingModel;
import com.musec.musec.data.models.viewModels.album.AlbumViewModel;
import com.musec.musec.data.models.bindingModels.SongBindingModel;
import com.musec.musec.data.models.viewModels.search.AlbumSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.AlbumShortInfoViewModel;
import javassist.NotFoundException;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface AlbumService {
    Long createAlbum(AlbumBindingModel albumBindingModel, String currentUserUsername) throws DbxException, RoleNotFoundException;
    void addSongToAlbum(Long id, SongBindingModel songBindingModel, String username) throws Exception;
    void publicDeleteAlbum(Long albumId, String username) throws NotFoundException, DbxException;
    void adminDeleteAlbum(Long albumId, String username) throws NotFoundException, DbxException;
    AlbumViewModel returnAlbum(Long id) throws NotFoundException;
    List<AlbumShortInfoViewModel> returnShortInfoOfAllAlbumsOfUserByUsername(String username);
    List<AlbumShortInfoViewModel> returnShortInfoOfAllAlbumsOfUserById(Long userId) throws NotFoundException;
    List<AlbumSearchViewModel> searchAlbumByName(String parameter);
    void addAlbumToQueue(Long albumId, String username) throws NotFoundException;
}
