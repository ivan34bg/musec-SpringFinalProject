package com.musec.musec.services;

import com.musec.musec.data.models.bindingModels.userRegisterBindingModel;
import com.musec.musec.data.userEntity;

public interface userService {
    void registerUser(userRegisterBindingModel bindingModel) throws Exception;
    userEntity returnExistingUserByUsername(String username);
}
