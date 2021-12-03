package com.musec.musec.repositories;

import com.musec.musec.data.albumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface albumRepository extends JpaRepository<albumEntity, Long> {
    Optional<Set<albumEntity>> findAllByUploader_Username(String username);
    Optional<Set<albumEntity>> findAllByAlbumNameContains(String parameter);
}
