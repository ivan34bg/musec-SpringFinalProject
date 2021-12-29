package com.musec.musec.services;

import com.musec.musec.data.RoleEntity;

public interface RoleService {
    RoleEntity returnRoleByName(String roleName);
}
