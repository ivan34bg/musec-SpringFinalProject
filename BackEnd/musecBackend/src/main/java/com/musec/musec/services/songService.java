package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.albumEntity;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.models.viewModels.album.albumSongViewModel;
import com.musec.musec.data.models.viewModels.search.songSearchViewModel;
import com.musec.musec.data.models.viewModels.songViewModel;
import com.musec.musec.data.models.viewModels.top10songs.songTopTenViewModel;
import com.musec.musec.data.singleEntity;
import com.musec.musec.data.songEntity;
import javassist.NotFoundException;

import java.util.Set;

public interface songService {
    void saveSongWithAlbum(albumEntity album, songBindingModel songBindingModel, String username) throws Exception;
    void saveSongWithSingle(singleEntity single, songBindingModel songBindingModel, String username) throws Exception;
    songEntity returnSongById(Long id) throws NotFoundException;
    Set<albumSongViewModel> returnSongViewModelSetFromFullSongSet(Set<songEntity> songs);
    void deleteSongById(Long songId) throws DbxException;
    Set<songSearchViewModel> searchSongBySongName(String parameters);
    Set<songTopTenViewModel> loadTopTenSongs();
}
