package com.musec.musec.repositories;

import com.musec.musec.data.singleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface singleRepository extends JpaRepository<singleEntity, Long> {
    Optional<List<singleEntity>> findAllByUploader_Username(String username);
    Optional<List<singleEntity>> findAllBySingleNameContains(String parameter);
}
