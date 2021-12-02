package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.changeCredentialsModels.*;
import com.musec.musec.data.models.bindingModels.userRegisterBindingModel;
import com.musec.musec.data.models.viewModels.profile.userProfileViewModel;
import com.musec.musec.data.userEntity;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface userService {
    void registerUser(userRegisterBindingModel bindingModel) throws Exception;
    userEntity returnExistingUserByUsername(String username);
    userProfileViewModel returnUserOrArtistProfileViewByUsername(String username, Boolean showPrivate) throws NotFoundException;
    userProfileViewModel returnUserOrArtistProfileViewById(Long userId) throws NotFoundException;
    void logoutUser(HttpServletRequest request, HttpServletResponse response);
    void changeProfilePic(MultipartFile newProfilePic, String username) throws DbxException;
    void changeUsernameOfLoggedUser(changeUsernameBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changeEmailOfLoggedUser(changeEmailBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changePasswordOfLoggedUser(changePasswordBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changeFullNameOfLoggedUser(changeFullNameBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changeBirthdayOfLoggedUser(changeBirthdayBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
}
