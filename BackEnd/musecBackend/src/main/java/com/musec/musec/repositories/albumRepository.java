package com.musec.musec.repositories;

import com.musec.musec.data.albumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface albumRepository extends JpaRepository<albumEntity, Long> {
    Optional<List<albumEntity>> findAllByUploader_Username(String username);
    Optional<List<albumEntity>> findAllByAlbumNameContains(String parameter);
}
