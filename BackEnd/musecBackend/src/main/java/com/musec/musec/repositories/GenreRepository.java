package com.musec.musec.repositories;

import com.musec.musec.data.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    Optional<GenreEntity> findByProperName(String genreName);
}
