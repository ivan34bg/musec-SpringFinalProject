package com.musec.musec.services;

import com.musec.musec.entities.models.userRegisterBindingModel;
import com.musec.musec.entities.userEntity;

public interface userService {
    void registerUser(userRegisterBindingModel bindingModel) throws Exception;
    userEntity returnExistingUserByUsername(String username);
}
