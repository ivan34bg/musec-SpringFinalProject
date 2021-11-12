package com.musec.musec.repositories;

import com.musec.musec.entities.enums.roleEnum;
import com.musec.musec.entities.roleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roleRepository extends JpaRepository<roleEntity, Long> {
    roleEntity getByName(roleEnum name);
}
