package com.musec.musec.services.implementations;

import com.musec.musec.data.models.bindingModels.playlistBindingModel;
import com.musec.musec.data.models.viewModels.search.playlistSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.playlistShortInfoViewModel;
import com.musec.musec.data.models.viewModels.playlist.playlistViewModel;
import com.musec.musec.data.playlistEntity;
import com.musec.musec.data.songEntity;
import com.musec.musec.repositories.playlistRepository;
import com.musec.musec.services.playlistService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class playlistServiceImpl implements playlistService {
    private final playlistRepository playlistRepo;
    private final userServiceImpl userService;
    private final songServiceImpl songService;
    private final queueServiceImpl queueService;
    private final ModelMapper modelMapper;

    public playlistServiceImpl(playlistRepository playlistRepo, userServiceImpl userService, songServiceImpl songService, queueServiceImpl queueService, ModelMapper modelMapper) {
        this.playlistRepo = playlistRepo;
        this.userService = userService;
        this.songService = songService;
        this.queueService = queueService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createPlaylist(playlistBindingModel playlistBindingModel, String usernameOfCurrentUser) {
        playlistEntity newPlaylist = new playlistEntity();
        modelMapper.map(playlistBindingModel, newPlaylist);
        newPlaylist.setPlaylistCreator(userService.returnExistingUserByUsername(usernameOfCurrentUser));
        return playlistRepo.save(newPlaylist).getId();
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException, AccessDeniedException, CloneNotSupportedException {
        playlistEntity playlist = isPlaylistPresent(playlistId);
        if(isCurrentPlaylistEditableByCurrentUser(playlist, usernameOfUser)){
            songEntity songToAdd = songService.returnSongById(songId);
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
        playlistEntity playlist = isPlaylistPresent(playlistId);
        if(isCurrentPlaylistEditableByCurrentUser(playlist, usernameOfUser)){
            Optional<songEntity> songToRemove = playlist.getSongs().stream().filter(s -> s.getId().equals(songId)).findFirst();
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
        playlistEntity playlist = isPlaylistPresent(playlistId);
        if(playlist.getPlaylistCreator().getUsername().equals(usernameOfUser)){
            playlistRepo.delete(playlist);
        }
        else throw new AccessDeniedException("You can't delete this playlist");
    }

    @Override
    public playlistViewModel returnPlaylistById(Long playlistId, String usernameOfUser) throws NotFoundException {
        playlistEntity playlist = isPlaylistPresent(playlistId);
        if(playlist.isPublic()){
            playlistViewModel playlistToReturn = new playlistViewModel();
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
                playlistViewModel playlistToReturn = new playlistViewModel();
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
    public List<playlistShortInfoViewModel> returnShortInfoOfLoggedUserPlaylists(String usernameOfUser) {
        Optional<List<playlistEntity>> playlistsOrNull = playlistRepo.findAllByPlaylistCreator_Username(usernameOfUser);
        List<playlistShortInfoViewModel> playlistSetToReturn = new ArrayList<>();
        for (playlistEntity playlist:playlistsOrNull.get()
        ) {
            playlistShortInfoViewModel mappedPlaylist = new playlistShortInfoViewModel();
            modelMapper.map(playlist, mappedPlaylist);
            playlistSetToReturn.add(mappedPlaylist);
        }
        return playlistSetToReturn;
    }

    @Override
    public List<playlistSearchViewModel> searchPlaylistByName(String parameters) {
        List<playlistSearchViewModel> setToReturn = new ArrayList<>();
        if(!parameters.trim().equals("")){
            Optional<List<playlistEntity>> playlistsOrNull = playlistRepo.findAllByPlaylistNameContains(parameters);
            if(!playlistsOrNull.get().isEmpty()) {
                for (playlistEntity playlist : playlistsOrNull.get()
                ) {
                    if(playlist.isPublic()){
                        playlistSearchViewModel mappedPlaylist = new playlistSearchViewModel();
                        modelMapper.map(playlist, mappedPlaylist);
                        setToReturn.add(mappedPlaylist);
                    }
                }
            }
        }
        return setToReturn;
    }

    @Override
    public void removeSongFromEveryPlaylist(songEntity song) {
        List<playlistEntity> playlists = playlistRepo.getAllBySongsContains(song);
        for (playlistEntity playlist:
                playlists) {
            playlist.getSongs().remove(song);
        }
    }

    @Override
    public void addPlaylistToQueue(Long playlistId, String username) throws NotFoundException {
        Optional<playlistEntity> playlistOrNull = playlistRepo.findById(playlistId);
        if(playlistOrNull.isPresent()){
            queueService.addCollectionToQueue(playlistOrNull.get().getSongs(), username);
        }
        else throw new NotFoundException("Playlist cannot be found");

    }

    private boolean isCurrentPlaylistEditableByCurrentUser(playlistEntity playlist, String usernameOfUser)
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

    private playlistEntity isPlaylistPresent(Long playlistId) throws NotFoundException {
        Optional<playlistEntity> playlistOrNull = playlistRepo.findById(playlistId);
        if(playlistOrNull.isPresent()){
            return playlistOrNull.get();
        }
        throw new NotFoundException("Cannot find this playlist");
    }
}
