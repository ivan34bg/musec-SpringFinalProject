package com.musec.musec.repositories;

import com.musec.musec.data.queueEntity;
import com.musec.musec.data.songEntity;
import com.musec.musec.data.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface queueRepository extends JpaRepository<queueEntity, Long> {
    Optional<queueEntity> findByUser_Username(String username);
    Optional<Set<queueEntity>> findAllBySongsContains(songEntity song);
}
