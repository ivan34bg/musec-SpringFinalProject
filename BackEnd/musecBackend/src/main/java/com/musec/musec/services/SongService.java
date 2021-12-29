package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.AlbumEntity;
import com.musec.musec.data.models.bindingModels.SongBindingModel;
import com.musec.musec.data.models.viewModels.album.AlbumSongViewModel;
import com.musec.musec.data.models.viewModels.search.SongSearchViewModel;
import com.musec.musec.data.models.viewModels.top10songs.SongNewestTenViewModel;
import com.musec.musec.data.SingleEntity;
import com.musec.musec.data.SongEntity;
import javassist.NotFoundException;

import java.util.List;

public interface SongService {
    void saveSongWithAlbum(AlbumEntity album, SongBindingModel songBindingModel, String username) throws Exception;
    void saveSongWithSingle(SingleEntity single, SongBindingModel songBindingModel, String username) throws Exception;
    SongEntity returnSongById(Long id) throws NotFoundException;
    List<AlbumSongViewModel> returnSongViewModelSetFromFullSongSet(List<SongEntity> songs);
    void deleteSongById(Long songId) throws DbxException;
    List<SongSearchViewModel> searchSongBySongName(String parameters);
    List<SongNewestTenViewModel> loadNewestTenSongs();
}
