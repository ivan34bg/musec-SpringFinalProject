package com.musec.musec.services;

import com.musec.musec.data.models.bindingModels.playlistBindingModel;
import com.musec.musec.data.models.viewModels.search.playlistSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.playlistShortInfoViewModel;
import com.musec.musec.data.models.viewModels.playlist.playlistViewModel;
import com.musec.musec.data.songEntity;
import javassist.NotFoundException;

import java.util.List;

public interface playlistService {
    Long createPlaylist(playlistBindingModel playlistBindingModel, String usernameOfCurrentUser);
    void addSongToPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException, CloneNotSupportedException;
    void removeSongFromPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException, CloneNotSupportedException;
    void deletePlaylist(Long playlistId, String usernameOfUser) throws NotFoundException;
    playlistViewModel returnPlaylistById(Long playlistId, String usernameOfUser) throws NotFoundException;
    void doesUserHavePlaylists(String usernameOfUser) throws NotFoundException;
    List<playlistShortInfoViewModel> returnShortInfoOfLoggedUserPlaylists(String usernameOfUser);
    List<playlistSearchViewModel> searchPlaylistByName(String parameters);
    void removeSongFromEveryPlaylist(songEntity song);
    void addPlaylistToQueue(Long playlistId, String username) throws NotFoundException;
}
