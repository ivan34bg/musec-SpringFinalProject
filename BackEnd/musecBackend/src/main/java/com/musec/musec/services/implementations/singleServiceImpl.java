package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.singleBindingModel;
import com.musec.musec.data.models.viewModels.search.singleSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.singleShortInfoViewModel;
import com.musec.musec.data.models.viewModels.single.singleViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.singleEntity;
import com.musec.musec.repositories.singleRepository;
import com.musec.musec.services.singleService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class singleServiceImpl implements singleService {
    private final cloudServiceImpl cloudService;
    private final songServiceImpl songService;
    private final userServiceImpl userService;
    private final queueServiceImpl queueService;
    private final singleRepository singleRepo;
    private final ModelMapper modelMapper;

    public singleServiceImpl(cloudServiceImpl cloudService, singleRepository singleRepo, songServiceImpl songService, userServiceImpl userService, queueServiceImpl queueService, ModelMapper modelMapper) {
        this.cloudService = cloudService;
        this.singleRepo = singleRepo;
        this.songService = songService;
        this.userService = userService;
        this.queueService = queueService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createSingle(singleBindingModel singleBindingModel, String currentUserUsername) throws RuntimeException, DbxException {
        singleEntity single = new singleEntity();
        single.setSingleName(singleBindingModel.getSingleName());
        String singlePicFilePath = cloudService.uploadAlbumPic(singleBindingModel.getSinglePic());
        single.setSinglePicFilePath(singlePicFilePath);
        single.setSinglePicLocation(cloudService.returnDirectLinkOfFile(singlePicFilePath));
        single.setUploader(userService.returnExistingUserByUsername(currentUserUsername));
        return singleRepo.save(single).getId();
    }

    @Override
    public void addSongToSingle(songBindingModel songBindingModel, Long singleId, String username) throws NotFoundException, DbxException {
        singleEntity single = isSinglePresent(singleId);
        songService.saveSongWithSingle(single, songBindingModel, username);
    }

    @Override
    public void deleteSingle(Long singleId) throws NotFoundException, DbxException {
        singleEntity singleToDelete = isSinglePresent(singleId);
        queueService.removeSongFromEveryQueue(singleToDelete.getSong());
        songService.deleteSongById(singleToDelete.getSong().getId());
        cloudService.deleteFile(singleToDelete.getSinglePicFilePath());
        singleRepo.delete(singleToDelete);
    }

    @Override
    public singleViewModel returnSingle(Long singleId) throws NotFoundException {
        singleEntity single = isSinglePresent(singleId);
        singleViewModel singleToReturn = new singleViewModel();
        modelMapper.map(single, singleToReturn);
        return singleToReturn;
    }

    @Override
    public Set<singleShortInfoViewModel> returnShortInfoOfSinglesOfLoggedUser(String username) {
        Optional<Set<singleEntity>> singlesOrNull = singleRepo.findAllByUploader_Username(username);
        Set<singleShortInfoViewModel> setToReturn = new HashSet<>();
        for (singleEntity single:singlesOrNull.get()
             ) {
            singleShortInfoViewModel mappedSingle = new singleShortInfoViewModel();
            modelMapper.map(single, mappedSingle);
            setToReturn.add(mappedSingle);
        }
        return setToReturn;
    }

    @Override
    public Set<singleSearchViewModel> searchSingleByName(String parameter) {
        Set<singleSearchViewModel> setToReturn = new HashSet<>();
        if(!parameter.equals("")){
            Optional<Set<singleEntity>> singlesOrNull = singleRepo.findAllBySingleNameContains(parameter);
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
}
