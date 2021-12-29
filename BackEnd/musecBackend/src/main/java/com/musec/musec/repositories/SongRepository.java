package com.musec.musec.repositories;

import com.musec.musec.data.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {
    Optional<List<SongEntity>> findAllBySongNameContains(String parameter);
    List<SongEntity> getTop10ByOrderByIdDesc();
}
