package com.musec.musec.repositories;

import com.musec.musec.data.enums.RoleEnum;
import com.musec.musec.data.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity getByRoleName(RoleEnum name);
}
