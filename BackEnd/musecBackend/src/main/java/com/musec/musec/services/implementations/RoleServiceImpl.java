package com.musec.musec.services.implementations;

import com.musec.musec.data.enums.RoleEnum;
import com.musec.musec.data.RoleEntity;
import com.musec.musec.repositories.RoleRepository;
import com.musec.musec.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepo;

    public RoleServiceImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public RoleEntity returnRoleByName(String roleName) {
        return roleRepo.getByRoleName(RoleEnum.valueOf(roleName));
    }
}
