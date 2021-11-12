package com.musec.musec.repositories;

import com.musec.musec.entities.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<userEntity, Long> {

    Optional<userEntity> findByUsername(String username);
    Optional<userEntity> findByEmail(String email);
}
