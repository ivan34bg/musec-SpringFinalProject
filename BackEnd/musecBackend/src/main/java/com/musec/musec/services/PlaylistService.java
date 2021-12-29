package com.musec.musec.services;

import com.musec.musec.data.models.bindingModels.PlaylistBindingModel;
import com.musec.musec.data.models.viewModels.search.PlaylistSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.PlaylistShortInfoViewModel;
import com.musec.musec.data.models.viewModels.playlist.PlaylistViewModel;
import com.musec.musec.data.SongEntity;
import javassist.NotFoundException;

import java.util.List;

public interface PlaylistService {
    Long createPlaylist(PlaylistBindingModel playlistBindingModel, String usernameOfCurrentUser);
    void addSongToPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException, CloneNotSupportedException;
    void removeSongFromPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException, CloneNotSupportedException;
    void deletePlaylist(Long playlistId, String usernameOfUser) throws NotFoundException;
    PlaylistViewModel returnPlaylistById(Long playlistId, String usernameOfUser) throws NotFoundException;
    void doesUserHavePlaylists(String usernameOfUser) throws NotFoundException;
    List<PlaylistShortInfoViewModel> returnShortInfoOfLoggedUserPlaylists(String usernameOfUser);
    List<PlaylistSearchViewModel> searchPlaylistByName(String parameters);
    void removeSongFromEveryPlaylist(SongEntity song);
    void addPlaylistToQueue(Long playlistId, String username) throws NotFoundException;
}
