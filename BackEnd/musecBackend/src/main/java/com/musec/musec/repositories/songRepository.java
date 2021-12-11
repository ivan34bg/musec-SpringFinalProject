package com.musec.musec.repositories;

import com.musec.musec.data.songEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface songRepository extends JpaRepository<songEntity, Long> {
    Optional<List<songEntity>> findAllBySongNameContains(String parameter);
    List<songEntity> getTop10ByOrderByIdDesc();
}
