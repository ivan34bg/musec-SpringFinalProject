package com.musec.musec.repositories;

import com.musec.musec.entities.singleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface singleRepository extends JpaRepository<singleEntity, Long> {
}
