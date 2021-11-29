package com.musec.musec.services.implementations;

import com.musec.musec.data.models.bindingModels.singleBindingModel;
import com.musec.musec.data.models.viewModels.single.singleViewModel;
import com.musec.musec.data.models.bindingModels.songBindingModel;
import com.musec.musec.data.singleEntity;
import com.musec.musec.repositories.singleRepository;
import com.musec.musec.services.singleService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class singleServiceImpl implements singleService {
    private final cloudServiceImpl cloudService;
    private final songServiceImpl songService;
    private final userServiceImpl userService;
    private final singleRepository singleRepo;
    private final ModelMapper modelMapper;

    public singleServiceImpl(cloudServiceImpl cloudService, singleRepository singleRepo, songServiceImpl songService, userServiceImpl userService, ModelMapper modelMapper) {
        this.cloudService = cloudService;
        this.singleRepo = singleRepo;
        this.songService = songService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createSingle(singleBindingModel singleBindingModel, String currentUserUsername) throws RuntimeException{
        singleEntity single = new singleEntity();
        single.setSingleName(singleBindingModel.getSingleName());
        String singlePicLink = cloudService.uploadAlbumPic(singleBindingModel.getSinglePic());
        single.setSinglePicLocation(singlePicLink);
        single.setUploader(userService.returnExistingUserByUsername(currentUserUsername));
        return singleRepo.save(single).getId();
    }

    @Override
    public void addSongToSingle(songBindingModel songBindingModel, Long singleId, String username) throws NotFoundException {
        singleEntity single = isSinglePresent(singleId);
        songService.saveSongWithSingle(single, songBindingModel, username);
    }

    @Override
    public void deleteSingle(Long singleId) throws NotFoundException {
        singleEntity singleToDelete = isSinglePresent(singleId);
        singleRepo.delete(singleToDelete);
    }

    @Override
    public singleViewModel returnSingle(Long singleId) throws NotFoundException {
        singleEntity single = isSinglePresent(singleId);
        singleViewModel singleToReturn = new singleViewModel();
        modelMapper.map(single, singleToReturn);
        return singleToReturn;
    }

    private singleEntity isSinglePresent(Long singleId) throws NotFoundException {
        Optional<singleEntity> singleOrNull = singleRepo.findById(singleId);
        if(singleOrNull.isPresent()){
            return singleOrNull.get();
        }
        else throw new NotFoundException("Cannot find this single");
    }
}
