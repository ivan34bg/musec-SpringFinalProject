package com.musec.musec.services.implementations;

import com.musec.musec.data.models.bindingModels.playlistBindingModel;
import com.musec.musec.data.models.viewModels.playlistViewModel;
import com.musec.musec.data.playlistEntity;
import com.musec.musec.data.songEntity;
import com.musec.musec.repositories.playlistRepository;
import com.musec.musec.services.playlistService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class playlistServiceImpl implements playlistService {
    private final playlistRepository playlistRepo;
    private final userServiceImpl userService;
    private final songServiceImpl songService;
    private final ModelMapper modelMapper;

    public playlistServiceImpl(playlistRepository playlistRepo, userServiceImpl userService, songServiceImpl songService, ModelMapper modelMapper) {
        this.playlistRepo = playlistRepo;
        this.userService = userService;
        this.songService = songService;
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
    public void addSongToPlaylist(Long playlistId, Long songId, String usernameOfUser) throws NotFoundException, AccessDeniedException {
        playlistEntity playlist = isPlaylistPresent(playlistId);
        if(isCurrentPlaylistEditableByCurrentUser(playlist, usernameOfUser)){
            songEntity songToAdd = songService.returnSongById(songId);
            if(!playlist.getSongs().contains(songToAdd)){
                playlist.getSongs().add(songToAdd);
                playlistRepo.save(playlist);
            }
            else
                throw new RequestRejectedException("Playlist already contains this song");
        }
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
        if(!playlist.isPrivate()){
            playlistViewModel playlistToReturn = new playlistViewModel();
            modelMapper.map(playlist, playlistToReturn);
            playlistToReturn.setSongs(songService.returnSongViewModelSetFromFullSongSet(playlist.getSongs()));
            return playlistToReturn;
        }
        else throw new NotFoundException("Cannot find the song in the current playlist");
    }

    private boolean isCurrentPlaylistEditableByCurrentUser(playlistEntity playlist, String usernameOfUser) throws AccessDeniedException{
        if(!playlist.isPrivate()){
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
