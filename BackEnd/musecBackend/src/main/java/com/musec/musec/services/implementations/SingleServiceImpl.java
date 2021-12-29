package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.enums.RoleEnum;
import com.musec.musec.data.models.bindingModels.SingleBindingModel;
import com.musec.musec.data.models.viewModels.search.SingleSearchViewModel;
import com.musec.musec.data.models.viewModels.shortInfo.SingleShortInfoViewModel;
import com.musec.musec.data.models.viewModels.single.SingleViewModel;
import com.musec.musec.data.models.bindingModels.SongBindingModel;
import com.musec.musec.data.SingleEntity;
import com.musec.musec.data.UserEntity;
import com.musec.musec.repositories.SingleRepository;
import com.musec.musec.services.SingleService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

@Service
public class SingleServiceImpl implements SingleService {
    private final CloudServiceImpl cloudService;
    private final SongServiceImpl songService;
    private final UserServiceImpl userService;
    private final QueueServiceImpl queueService;
    private final PlaylistServiceImpl playlistService;
    private final SingleRepository singleRepo;
    private final ModelMapper modelMapper;

    public SingleServiceImpl(
            CloudServiceImpl cloudService,
            SingleRepository singleRepo,
            SongServiceImpl songService,
            UserServiceImpl userService,
            QueueServiceImpl queueService,
            PlaylistServiceImpl playlistService, ModelMapper modelMapper) {
        this.singleRepo = singleRepo;
        this.cloudService = cloudService;
        this.songService = songService;
        this.userService = userService;
        this.queueService = queueService;
        this.playlistService = playlistService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createSingle(SingleBindingModel singleBindingModel, String currentUserUsername) throws
            RuntimeException,
            DbxException,
            RoleNotFoundException {
        UserEntity user = userService.returnExistingUserByUsername(currentUserUsername);
        if(user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(RoleEnum.ARTIST))) {
            SingleEntity single = new SingleEntity();
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
    public void addSongToSingle(SongBindingModel songBindingModel, Long singleId, String username) throws
            NotFoundException,
            DbxException {
        SingleEntity single = isSinglePresent(singleId);
        songService.saveSongWithSingle(single, songBindingModel, username);
    }

    @Override
    public void publicDeleteSingle(Long singleId, String username) throws
            NotFoundException,
            DbxException,
            IllegalCallerException {
        SingleEntity singleToDelete = isSinglePresent(singleId);
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
    public SingleViewModel returnSingle(Long singleId) throws NotFoundException {
        SingleEntity single = isSinglePresent(singleId);
        SingleViewModel singleToReturn = new SingleViewModel();
        modelMapper.map(single, singleToReturn);
        return singleToReturn;
    }

    @Override
    public List<SingleShortInfoViewModel> returnShortInfoOfSinglesOfUserByUsername(String username) {
        Optional<List<SingleEntity>> singlesOrNull = singleRepo.findAllByUploader_Username(username);
        List<SingleShortInfoViewModel> setToReturn = new ArrayList<>();
        for (SingleEntity single:singlesOrNull.get()
             ) {
            SingleShortInfoViewModel mappedSingle = new SingleShortInfoViewModel();
            modelMapper.map(single, mappedSingle);
            setToReturn.add(mappedSingle);
        }
        return setToReturn;
    }

    @Override
    public List<SingleShortInfoViewModel> returnShortInfoOfSinglesOfUserById(Long userId) throws NotFoundException {
        return returnShortInfoOfSinglesOfUserByUsername(userService.returnUserById(userId).getUsername());
    }

    @Override
    public List<SingleSearchViewModel> searchSingleByName(String parameter) {
        List<SingleSearchViewModel> setToReturn = new ArrayList<>();
        if(!parameter.trim().equals("")){
            Optional<List<SingleEntity>> singlesOrNull = singleRepo.findAllBySingleNameContains(parameter);
            if(!singlesOrNull.get().isEmpty()){
                for (SingleEntity single:singlesOrNull.get()
                     ) {
                    SingleSearchViewModel mappedSingle = new SingleSearchViewModel();
                    modelMapper.map(single, mappedSingle);
                    setToReturn.add(mappedSingle);
                }
            }
        }
        return setToReturn;
    }

    private SingleEntity isSinglePresent(Long singleId) throws NotFoundException {
        Optional<SingleEntity> singleOrNull = singleRepo.findById(singleId);
        if(singleOrNull.isPresent()){
            return singleOrNull.get();
        }
        else throw new NotFoundException("Cannot find this single");
    }

    protected void deleteSingle(Long singleId) throws NotFoundException, DbxException {
        SingleEntity singleToDelete = isSinglePresent(singleId);
        queueService.removeSongFromEveryQueue(singleToDelete.getSong());
        playlistService.removeSongFromEveryPlaylist(singleToDelete.getSong());
        songService.deleteSongById(singleToDelete.getSong().getId());
        cloudService.deleteFile(singleToDelete.getSinglePicFilePath());
        singleRepo.delete(singleToDelete);
    }
}
