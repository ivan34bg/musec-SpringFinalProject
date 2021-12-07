package com.musec.musec.services.implementations;

import com.musec.musec.data.enums.roleEnum;
import com.musec.musec.data.roleEntity;
import com.musec.musec.repositories.roleRepository;
import com.musec.musec.services.roleService;
import org.springframework.stereotype.Service;

@Service
public class roleServiceImpl implements roleService {
    private final roleRepository roleRepo;

    public roleServiceImpl(roleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public roleEntity returnRoleByName(String roleName) {
        return roleRepo.getByRoleName(roleEnum.valueOf(roleName));
    }
}
