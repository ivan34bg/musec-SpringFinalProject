package com.musec.musec.repositories;

import com.musec.musec.entities.genreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface genreRepository extends JpaRepository<genreEntity, Long> {
    Optional<genreEntity> findByName(String name);
}
