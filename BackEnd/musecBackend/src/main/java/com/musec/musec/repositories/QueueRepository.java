package com.musec.musec.repositories;

import com.musec.musec.data.QueueEntity;
import com.musec.musec.data.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Long> {
    Optional<QueueEntity> findByUser_Username(String username);
    Optional<List<QueueEntity>> findAllBySongsContains(SongEntity song);
}
