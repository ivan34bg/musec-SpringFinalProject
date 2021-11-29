package com.musec.musec.services.implementations;

import com.musec.musec.data.models.viewModels.queue.queueFullSongViewModel;
import com.musec.musec.data.models.viewModels.queue.queueSongViewModel;
import com.musec.musec.data.queueEntity;
import com.musec.musec.data.songEntity;
import com.musec.musec.data.userEntity;
import com.musec.musec.repositories.queueRepository;
import com.musec.musec.services.queueService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class queueServiceImpl implements queueService {
    private final queueRepository queueRepo;
    private final ModelMapper modelMapper;
    private final songServiceImpl songService;

    public queueServiceImpl(queueRepository queueRepo, ModelMapper modelMapper, songServiceImpl songService) {
        this.queueRepo = queueRepo;
        this.modelMapper = modelMapper;
        this.songService = songService;
    }

    @Override
    public Set<queueSongViewModel> returnQueueOfUser(String username) {
        queueEntity queue = queueRepo.findByUser_Username(username).get();
        Set<queueSongViewModel> songCollection = new HashSet<>();
        if(queue.getSongs().size() > 0) {
            for (songEntity song : queue.getSongs()
            ) {
                queueSongViewModel mappedSong = new queueSongViewModel();
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
    public Set<queueFullSongViewModel> returnFullSongInfo(String username) {
        queueEntity queue = queueRepo.findByUser_Username(username).get();
        Set<queueFullSongViewModel> setToSend = new HashSet<>();
        for (songEntity song:queue.getSongs()
             ) {
            queueFullSongViewModel mappedSong = new queueFullSongViewModel();
            modelMapper.map(song, mappedSong);
            setToSend.add(mappedSong);
        }
        return setToSend;
    }

    @Override
    public void createQueue(userEntity user) {
        queueEntity newQueue = new queueEntity();
        newQueue.setUser(user);
        queueRepo.save(newQueue);
    }

    @Override
    public void addSongToQueue(Long songId, String username) throws Exception {
        songEntity song = songService.returnSongById(songId);
        queueEntity queue = queueRepo.findByUser_Username(username).get();
        if(queue.getSongs().contains(song)){
            throw new Exception("This song is already queued");
        }
        queue.getSongs().add(song);
        queueRepo.save(queue);
    }

    @Override
    public void removeSongFromQueue(Long songId, String username) throws NotFoundException {
        queueEntity queue = queueRepo.findByUser_Username(username).get();
        if(queue.getSongs().stream().anyMatch(s -> s.getId().equals(songId))){
            queue.getSongs().remove(songService.returnSongById(songId));
            queueRepo.save(queue);
        }
        else throw new NotFoundException("This song cannot be found in your queue.");
    }

    @Override
    public void emptyQueue(String username) {
        queueRepo.findByUser_Username(username).get().getSongs().clear();
    }
}
