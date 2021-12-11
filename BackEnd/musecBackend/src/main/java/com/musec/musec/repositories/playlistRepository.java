package com.musec.musec.repositories;

import com.musec.musec.data.playlistEntity;
import com.musec.musec.data.songEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface playlistRepository extends JpaRepository<playlistEntity, Long> {
    Optional<List<playlistEntity>> findAllByPlaylistCreator_Username(String username);
    Optional<List<playlistEntity>> findAllByPlaylistNameContains(String parameter);
    List<playlistEntity> getAllBySongsContains(songEntity song);
}
