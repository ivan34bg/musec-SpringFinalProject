package com.musec.musec.services;

import com.musec.musec.data.models.viewModels.queue.queueFullSongViewModel;
import com.musec.musec.data.models.viewModels.queue.queueSongViewModel;
import com.musec.musec.data.songEntity;
import com.musec.musec.data.userEntity;
import javassist.NotFoundException;

import java.util.Set;

public interface queueService {
    Set<queueSongViewModel> returnQueueOfUser(String username);
    Set<queueFullSongViewModel> returnFullSongInfo(String username);
    void createQueue(userEntity user);
    void addSongToQueue(Long songId, String username) throws Exception;
    void addCollectionToQueue(Set<songEntity> songs, String username);
    void removeSongFromQueue(Long songId, String username) throws NotFoundException;
    void removeSongFromEveryQueue(songEntity song);
    void emptyQueue(String username);
}
