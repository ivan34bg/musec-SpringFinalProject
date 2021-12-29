package com.musec.musec.repositories;

import com.musec.musec.data.PlaylistEntity;
import com.musec.musec.data.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
    Optional<List<PlaylistEntity>> findAllByPlaylistCreator_Username(String username);
    Optional<List<PlaylistEntity>> findAllByPlaylistNameContains(String parameter);
    List<PlaylistEntity> getAllBySongsContains(SongEntity song);
}
