package com.musec.musec.repositories;

import com.musec.musec.data.songEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface songRepository extends JpaRepository<songEntity, Long> {
    Optional<Set<songEntity>> findAllBySongNameContains(String parameter);
    Set<songEntity> getTopByOrderByPlaysAsc();
}
