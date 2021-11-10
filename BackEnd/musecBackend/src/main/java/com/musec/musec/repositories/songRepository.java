package com.musec.musec.repositories;

import com.musec.musec.entities.songEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface songRepository extends JpaRepository<songEntity, Long> {
}
