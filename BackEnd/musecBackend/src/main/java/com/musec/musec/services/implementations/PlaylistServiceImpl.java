package com.musec.musec.services.implementations;

import com.musec.musec.data.models.bindingModels.PlaylistBindingModel;
import com.musec.musec.data.models.viewModels.search.PlaylistSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.PlaylistShortInfoViewModel;
import com.musec.musec.data.models.viewModels.playlist.PlaylistViewModel;
import com.musec.musec.data.PlaylistEntity;
import com.musec.musec.data.SongEntity;
import com.musec.musec.repositories.PlaylistRepository;
import com.musec.musec.services.PlaylistService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepo;
    private final UserServiceImpl userService;
    private final SongServiceImpl songService;
    private final QueueServiceImpl queueService;
    private final ModelMapper modelMapper;

    public PlaylistServiceImpl(PlaylistRepository playlistRepo, UserServiceImpl userService, SongServiceImpl songService, QueueServiceImpl queueService, ModelMapper modelMapper) {
        this.playlistRepo = playlistRepo;
        this.userService = userService;
        this.songService = songService;
        this.queueService = queueService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createPlaylist(PlaylistBindingModel playlistBindingModel, String usernameOfCurrentUser) {
        PlaylistEntity newPlaylist = new PlaylistEntity();
        modelMapper.map(playlistBindingModel, newPlaylist);
        newPlaylist.setPlaylistCreator(userService.returnExistingUserByUsername(usernameOfCurrentUser));
        return playlistRepo.save(newPlaylist).getId();
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException, AccessDeniedException, CloneNotSupportedException {
        PlaylistEntity playlist = isPlaylistPresent(playlistId);
        if(isCurrentPlaylistEditableByCurrentUser(playlist, usernameOfUser)){
            SongEntity songToAdd = songService.returnSongById(songId);
            if(!playlist.getSongs().contains(songToAdd)){
                playlist.getSongs().add(songToAdd);
                playlistRepo.save(playlist);
            }
            else throw new CloneNotSupportedException("Playlist already contains this song");
        }
        else throw new RequestRejectedException("Current user cannot edit this playlist");
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException, AccessDeniedException {
        PlaylistEntity playlist = isPlaylistPresent(playlistId);
        if(isCurrentPlaylistEditableByCurrentUser(playlist, usernameOfUser)){
            Optional<SongEntity> songToRemove = playlist.getSongs().stream().filter(s -> s.getId().equals(songId)).findFirst();
            if(songToRemove.isPresent()) {
                playlist.getSongs().remove(songService.returnSongById(songId));
                playlistRepo.save(playlist);
            }
            else throw new NotFoundException("Cannot find the song in the current playlist");
        }
        else throw new RequestRejectedException("Current user cannot edit this playlist");
    }


    @Override
    public void deletePlaylist(Long playlistId, String usernameOfUser) throws NotFoundException, AccessDeniedException {
        PlaylistEntity playlist = isPlaylistPresent(playlistId);
        if(playlist.getPlaylistCreator().getUsername().equals(usernameOfUser)){
            playlistRepo.delete(playlist);
        }
        else throw new AccessDeniedException("You can't delete this playlist");
    }

    @Override
    public PlaylistViewModel returnPlaylistById(Long playlistId, String usernameOfUser) throws NotFoundException {
        PlaylistEntity playlist = isPlaylistPresent(playlistId);
        if(playlist.isPublic()){
            PlaylistViewModel playlistToReturn = new PlaylistViewModel();
            modelMapper.map(playlist, playlistToReturn);
            if(playlist.isOpenToPublicEditsOrNot())
                playlistToReturn.setCanEdit(true);
            else if(usernameOfUser.equals(playlist.getPlaylistCreator().getUsername()))
                playlistToReturn.setCanEdit(true);
            else
                playlistToReturn.setCanEdit(false);
            return playlistToReturn;
        }
        else{
            if(playlist.getPlaylistCreator().getUsername().equals(usernameOfUser)){
                PlaylistViewModel playlistToReturn = new PlaylistViewModel();
                modelMapper.map(playlist, playlistToReturn);
                playlistToReturn.setCanEdit(true);
                return playlistToReturn;
            }
            throw new NotFoundException("Cannot find the current playlist");
        }

    }

    @Override
    public void doesUserHavePlaylists(String usernameOfUser) throws NotFoundException {
        if(playlistRepo.findAllByPlaylistCreator_Username(usernameOfUser).get().size() == 0){
            throw new NotFoundException("User has no playlists");
        }
    }

    @Override
    public List<PlaylistShortInfoViewModel> returnShortInfoOfLoggedUserPlaylists(String usernameOfUser) {
        Optional<List<PlaylistEntity>> playlistsOrNull = playlistRepo.findAllByPlaylistCreator_Username(usernameOfUser);
        List<PlaylistShortInfoViewModel> playlistSetToReturn = new ArrayList<>();
        for (PlaylistEntity playlist:playlistsOrNull.get()
        ) {
            PlaylistShortInfoViewModel mappedPlaylist = new PlaylistShortInfoViewModel();
            modelMapper.map(playlist, mappedPlaylist);
            playlistSetToReturn.add(mappedPlaylist);
        }
        return playlistSetToReturn;
    }

    @Override
    public List<PlaylistSearchViewModel> searchPlaylistByName(String parameters) {
        List<PlaylistSearchViewModel> setToReturn = new ArrayList<>();
        if(!parameters.trim().equals("")){
            Optional<List<PlaylistEntity>> playlistsOrNull = playlistRepo.findAllByPlaylistNameContains(parameters);
            if(!playlistsOrNull.get().isEmpty()) {
                for (PlaylistEntity playlist : playlistsOrNull.get()
                ) {
                    if(playlist.isPublic()){
                        PlaylistSearchViewModel mappedPlaylist = new PlaylistSearchViewModel();
                        modelMapper.map(playlist, mappedPlaylist);
                        setToReturn.add(mappedPlaylist);
                    }
                }
            }
        }
        return setToReturn;
    }

    @Override
    public void removeSongFromEveryPlaylist(SongEntity song) {
        List<PlaylistEntity> playlists = playlistRepo.getAllBySongsContains(song);
        for (PlaylistEntity playlist:
                playlists) {
            playlist.getSongs().remove(song);
            playlistRepo.save(playlist);
        }
    }

    @Override
    public void addPlaylistToQueue(Long playlistId, String username) throws NotFoundException {
        Optional<PlaylistEntity> playlistOrNull = playlistRepo.findById(playlistId);
        if(playlistOrNull.isPresent()){
            queueService.addCollectionToQueue(playlistOrNull.get().getSongs(), username);
        }
        else throw new NotFoundException("Playlist cannot be found");

    }

    private boolean isCurrentPlaylistEditableByCurrentUser(PlaylistEntity playlist, String usernameOfUser)
            throws AccessDeniedException{
        if(playlist.isPublic() || playlist.getPlaylistCreator().getUsername().equals(usernameOfUser)){
            if(!playlist.isOpenToPublicEditsOrNot()){
                if(playlist.getPlaylistCreator().getUsername().equals(usernameOfUser)){
                    return true;
                }
                throw new AccessDeniedException("You can't edit this playlist");
            }
            return true;
        }
        throw new AccessDeniedException("You can't view this playlist");
    }

    private PlaylistEntity isPlaylistPresent(Long playlistId) throws NotFoundException {
        Optional<PlaylistEntity> playlistOrNull = playlistRepo.findById(playlistId);
        if(playlistOrNull.isPresent()){
            return playlistOrNull.get();
        }
        throw new NotFoundException("Cannot find this playlist");
    }
}
