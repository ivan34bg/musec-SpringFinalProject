package com.musec.musec.services.implementations;

import com.musec.musec.entities.models.singleBindingModel;
import com.musec.musec.entities.models.singleViewModel;
import com.musec.musec.entities.models.songBindingModel;
import com.musec.musec.entities.singleEntity;
import com.musec.musec.repositories.singleRepository;
import com.musec.musec.services.singleService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public void addSongToSingle(songBindingModel songBindingModel, Long id) throws NotFoundException {
        Optional<singleEntity> singleOrNull = singleRepo.findById(id);
        if(singleOrNull.isPresent()){
            songService.saveSongWithSingle(singleOrNull.get(), songBindingModel);
        }
        else
            throw new NotFoundException("Cannot find this single");
    }

    @Override
    public singleViewModel returnSingle(Long singleId) throws NotFoundException {
        Optional<singleEntity> singleOrNull = singleRepo.findById(singleId);
        if(singleOrNull.isPresent()){
            singleViewModel singleToReturn = new singleViewModel();
            modelMapper.map(singleOrNull.get(), singleToReturn);
            if(singleOrNull.get().getSong() != null){
                singleToReturn.setSingleSongLink(singleOrNull.get().getSong().getSongLocation());
            }
            return singleToReturn;
        }
        throw new NotFoundException("Cannot find this single");
    }
}
