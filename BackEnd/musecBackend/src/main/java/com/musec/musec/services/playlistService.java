package com.musec.musec.services;

import com.musec.musec.data.models.bindingModels.playlistBindingModel;
import com.musec.musec.data.models.viewModels.playlistViewModel;
import javassist.NotFoundException;

public interface playlistService {
    Long createPlaylist(playlistBindingModel playlistBindingModel, String usernameOfCurrentUser);
    void addSongToPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException;
    void removeSongFromPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException;
    void deletePlaylist(Long playlistId, String usernameOfUser) throws NotFoundException;
    playlistViewModel returnPlaylistById(Long playlistId, String usernameOfUser) throws NotFoundException;
}
