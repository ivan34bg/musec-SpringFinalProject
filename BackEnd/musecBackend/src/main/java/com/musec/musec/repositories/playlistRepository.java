package com.musec.musec.repositories;

import com.musec.musec.data.playlistEntity;
import com.musec.musec.data.songEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface playlistRepository extends JpaRepository<playlistEntity, Long> {
    Optional<Set<playlistEntity>> findAllByPlaylistCreator_Username(String username);
    Optional<Set<playlistEntity>> findAllByPlaylistNameContains(String parameter);
    Set<playlistEntity> getAllBySongsContains(songEntity song);
}
