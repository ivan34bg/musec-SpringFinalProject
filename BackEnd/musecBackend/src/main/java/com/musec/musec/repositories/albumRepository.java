package com.musec.musec.repositories;

import com.musec.musec.entities.albumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface albumRepository extends JpaRepository<albumEntity, Long> {
}
