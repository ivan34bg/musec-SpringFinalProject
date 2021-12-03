package com.musec.musec.repositories;

import com.musec.musec.data.singleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface singleRepository extends JpaRepository<singleEntity, Long> {
    Optional<Set<singleEntity>> findAllByUploader_Username(String username);
    Optional<Set<singleEntity>> findAllBySingleNameContains(String parameter);
}
