package com.musec.musec.repositories;

import com.musec.musec.data.SingleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SingleRepository extends JpaRepository<SingleEntity, Long> {
    Optional<List<SingleEntity>> findAllByUploader_Username(String username);
    Optional<List<SingleEntity>> findAllBySingleNameContains(String parameter);
}
