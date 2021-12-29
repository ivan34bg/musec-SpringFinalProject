package com.musec.musec.services;

import com.musec.musec.data.models.viewModels.queue.QueueFullSongViewModel;
import com.musec.musec.data.models.viewModels.queue.QueueSongViewModel;
import com.musec.musec.data.SongEntity;
import com.musec.musec.data.UserEntity;
import javassist.NotFoundException;

import java.util.List;

public interface QueueService {
    List<QueueSongViewModel> returnQueueOfUser(String username);
    List<QueueFullSongViewModel> returnFullSongInfo(String username);
    void createQueue(UserEntity user);
    void addSongToQueue(Long songId, String username) throws Exception;
    void addCollectionToQueue(List<SongEntity> songs, String username);
    void removeSongFromQueue(Long songId, String username) throws NotFoundException;
    void removeSongFromEveryQueue(SongEntity song);
    void emptyQueue(String username);
}
