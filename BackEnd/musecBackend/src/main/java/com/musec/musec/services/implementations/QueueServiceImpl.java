package com.musec.musec.services.implementations;

import com.musec.musec.data.models.viewModels.queue.QueueFullSongViewModel;
import com.musec.musec.data.models.viewModels.queue.QueueSongViewModel;
import com.musec.musec.data.QueueEntity;
import com.musec.musec.data.SongEntity;
import com.musec.musec.data.UserEntity;
import com.musec.musec.repositories.QueueRepository;
import com.musec.musec.services.QueueService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueueServiceImpl implements QueueService {
    private final QueueRepository queueRepo;
    private final ModelMapper modelMapper;
    private final SongServiceImpl songService;

    public QueueServiceImpl(QueueRepository queueRepo, ModelMapper modelMapper, SongServiceImpl songService) {
        this.queueRepo = queueRepo;
        this.modelMapper = modelMapper;
        this.songService = songService;
    }

    @Override
    public List<QueueSongViewModel> returnQueueOfUser(String username) {
        QueueEntity queue = queueRepo.findByUser_Username(username).get();
        List<QueueSongViewModel> songCollection = new ArrayList<>();
        if(queue.getSongs().size() > 0) {
            for (SongEntity song : queue.getSongs()
            ) {
                QueueSongViewModel mappedSong = new QueueSongViewModel();
                modelMapper.map(song, mappedSong);
                if (song.getAlbum() != null) {
                    mappedSong.setSongPic(song.getAlbum().getAlbumPicLocation());
                } else {
                    mappedSong.setSongPic(song.getSingle().getSinglePicLocation());
                }
                songCollection.add(mappedSong);
            }
        }
        return songCollection;
    }

    @Override
    public List<QueueFullSongViewModel> returnFullSongInfo(String username) {
        QueueEntity queue = queueRepo.findByUser_Username(username).get();
        List<QueueFullSongViewModel> setToSend = new ArrayList<>();
        for (SongEntity song:queue.getSongs()
             ) {
            QueueFullSongViewModel mappedSong = new QueueFullSongViewModel();
            modelMapper.map(song, mappedSong);
            setToSend.add(mappedSong);
        }
        return setToSend;
    }

    @Override
    public void createQueue(UserEntity user) {
        QueueEntity newQueue = new QueueEntity();
        newQueue.setUser(user);
        queueRepo.save(newQueue);
    }

    @Override
    public void addSongToQueue(Long songId, String username) throws CloneNotSupportedException, NotFoundException {
        SongEntity song = songService.returnSongById(songId);
        QueueEntity queue = queueRepo.findByUser_Username(username).get();
        if(queue.getSongs().contains(song)){
            throw new CloneNotSupportedException("This song is already queued");
        }
        queue.getSongs().add(song);
        queueRepo.save(queue);
    }

    @Override
    public void addCollectionToQueue(List<SongEntity> songs, String username) {
        this.emptyQueue(username);
        QueueEntity queue = queueRepo.findByUser_Username(username).get();
        for (SongEntity song:songs
             ) {
            queue.getSongs().add(song);
        }
        queueRepo.save(queue);
    }

    @Override
    public void removeSongFromQueue(Long songId, String username) throws NotFoundException {
        QueueEntity queue = queueRepo.findByUser_Username(username).get();
        if(queue.getSongs().stream().anyMatch(s -> s.getId().equals(songId))){
            queue.getSongs().remove(songService.returnSongById(songId));
            queueRepo.save(queue);
        }
        else throw new NotFoundException("This song cannot be found in your queue.");
    }

    @Override
    public void removeSongFromEveryQueue(SongEntity song) {
        List<QueueEntity> queuesContainingTheSong = queueRepo.findAllBySongsContains(song).get();
        for (QueueEntity queue:queuesContainingTheSong
             ) {
            queue.getSongs().remove(song);
            queueRepo.save(queue);
        }
    }

    @Override
    public void emptyQueue(String username) {
        QueueEntity queue = queueRepo.findByUser_Username(username).get();
        queue.getSongs().clear();
        queueRepo.save(queue);
    }
}
