package com.musec.musec.services;

import com.musec.musec.data.models.bindingModels.userRegisterBindingModel;
import com.musec.musec.data.models.viewModels.profile.userProfileViewModel;
import com.musec.musec.data.userEntity;
import javassist.NotFoundException;

public interface userService {
    void registerUser(userRegisterBindingModel bindingModel) throws Exception;
    userEntity returnExistingUserByUsername(String username);
    userProfileViewModel returnUserOrArtistProfileViewByUsername(String username) throws NotFoundException;
}
