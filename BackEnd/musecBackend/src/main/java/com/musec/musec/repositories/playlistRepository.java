package com.musec.musec.repositories;

import com.musec.musec.data.playlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface playlistRepository extends JpaRepository<playlistEntity, Long> {
}
