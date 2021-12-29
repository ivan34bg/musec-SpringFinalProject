package com.musec.musec.repositories;

import com.musec.musec.data.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Optional<List<AlbumEntity>> findAllByUploader_Username(String username);
    Optional<List<AlbumEntity>> findAllByAlbumNameContains(String parameter);
}
