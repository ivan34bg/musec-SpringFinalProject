package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.enums.roleEnum;
import com.musec.musec.data.models.bindingModels.singleBindingModel;
import com.musec.musec.data.models.viewModels.search.singleSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.singleShortInfoViewModel;
import com.musec.musec.data.models.viewModels.single.singleViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.singleEntity;
import com.musec.musec.data.userEntity;
import com.musec.musec.repositories.singleRepository;
import com.musec.musec.services.singleService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

@Service
public class singleServiceImpl implements singleService {
    private final cloudServiceImpl cloudService;
    private final songServiceImpl songService;
    private final userServiceImpl userService;
    private final queueServiceImpl queueService;
    private final playlistServiceImpl playlistService;
    private final singleRepository singleRepo;
    private final ModelMapper modelMapper;

    public singleServiceImpl(
            cloudServiceImpl cloudService,
            singleRepository singleRepo,
            songServiceImpl songService,
            userServiceImpl userService,
            queueServiceImpl queueService,
            playlistServiceImpl playlistService, ModelMapper modelMapper) {
        this.singleRepo = singleRepo;
        this.cloudService = cloudService;
        this.songService = songService;
        this.userService = userService;
        this.queueService = queueService;
        this.playlistService = playlistService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createSingle(singleBindingModel singleBindingModel, String currentUserUsername) throws
            RuntimeException,
            DbxException,
            RoleNotFoundException {
        userEntity user = userService.returnExistingUserByUsername(currentUserUsername);
        if(user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(roleEnum.ARTIST))) {
            singleEntity single = new singleEntity();
            single.setSingleName(singleBindingModel.getSingleName());
            String singlePicFilePath = cloudService.uploadAlbumPic(singleBindingModel.getSinglePic());
            single.setSinglePicFilePath(singlePicFilePath);
            single.setSinglePicLocation(cloudService.returnDirectLinkOfFile(singlePicFilePath));
            single.setUploader(userService.returnExistingUserByUsername(currentUserUsername));
            return singleRepo.save(single).getId();
        }
        throw new RoleNotFoundException("User is not an artist");
    }

    @Override
    public void addSongToSingle(songBindingModel songBindingModel, Long singleId, String username) throws
            NotFoundException,
            DbxException {
        singleEntity single = isSinglePresent(singleId);
        songService.saveSongWithSingle(single, songBindingModel, username);
    }

    @Override
    public void publicDeleteSingle(Long singleId, String username) throws
            NotFoundException,
            DbxException,
            IllegalCallerException {
        singleEntity singleToDelete = isSinglePresent(singleId);
        if(singleToDelete.getUploader().getUsername().equals(username)){
            deleteSingle(singleId);
        }
        else throw new IllegalCallerException("Only the uploader of a single can delete it");
    }

    @Override
    public void adminDeleteSingle(Long singleId, String username) throws NotFoundException, DbxException {
        if(userService.isUserAdmin(username)){
            deleteSingle(singleId);
        }
    }

    @Override
    public singleViewModel returnSingle(Long singleId) throws NotFoundException {
        singleEntity single = isSinglePresent(singleId);
        singleViewModel singleToReturn = new singleViewModel();
        modelMapper.map(single, singleToReturn);
        return singleToReturn;
    }

    @Override
    public List<singleShortInfoViewModel> returnShortInfoOfSinglesOfUserByUsername(String username) {
        Optional<List<singleEntity>> singlesOrNull = singleRepo.findAllByUploader_Username(username);
        List<singleShortInfoViewModel> setToReturn = new ArrayList<>();
        for (singleEntity single:singlesOrNull.get()
             ) {
            singleShortInfoViewModel mappedSingle = new singleShortInfoViewModel();
            modelMapper.map(single, mappedSingle);
            setToReturn.add(mappedSingle);
        }
        return setToReturn;
    }

    @Override
    public List<singleShortInfoViewModel> returnShortInfoOfSinglesOfUserById(Long userId) throws NotFoundException {
        return returnShortInfoOfSinglesOfUserByUsername(userService.returnUserById(userId).getUsername());
    }

    @Override
    public List<singleSearchViewModel> searchSingleByName(String parameter) {
        List<singleSearchViewModel> setToReturn = new ArrayList<>();
        if(!parameter.trim().equals("")){
            Optional<List<singleEntity>> singlesOrNull = singleRepo.findAllBySingleNameContains(parameter);
            if(!singlesOrNull.get().isEmpty()){
                for (singleEntity single:singlesOrNull.get()
                     ) {
                    singleSearchViewModel mappedSingle = new singleSearchViewModel();
                    modelMapper.map(single, mappedSingle);
                    setToReturn.add(mappedSingle);
                }
            }
        }
        return setToReturn;
    }

    private singleEntity isSinglePresent(Long singleId) throws NotFoundException {
        Optional<singleEntity> singleOrNull = singleRepo.findById(singleId);
        if(singleOrNull.isPresent()){
            return singleOrNull.get();
        }
        else throw new NotFoundException("Cannot find this single");
    }

    protected void deleteSingle(Long singleId) throws NotFoundException, DbxException {
        singleEntity singleToDelete = isSinglePresent(singleId);
        queueService.removeSongFromEveryQueue(singleToDelete.getSong());
        playlistService.removeSongFromEveryPlaylist(singleToDelete.getSong());
        songService.deleteSongById(singleToDelete.getSong().getId());
        cloudService.deleteFile(singleToDelete.getSinglePicFilePath());
        singleRepo.delete(singleToDelete);
    }
}
