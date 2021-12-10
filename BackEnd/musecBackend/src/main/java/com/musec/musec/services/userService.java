package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.changeCredentialsModels.*;
import com.musec.musec.data.models.bindingModels.userRegisterBindingModel;
import com.musec.musec.data.models.viewModels.profile.userProfileViewModel;
import com.musec.musec.data.models.viewModels.search.userSearchViewModel;
import com.musec.musec.data.userEntity;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public interface userService {
    void registerUser(userRegisterBindingModel bindingModel) throws Exception;
    userEntity returnExistingUserByUsername(String username);
    userEntity returnUserById(Long userId) throws NotFoundException;
    userProfileViewModel returnUserOrArtistProfileViewByUsername(String username, Boolean showPrivate) throws NotFoundException;
    userProfileViewModel returnUserOrArtistProfileViewById(Long userId) throws NotFoundException;
    void logoutUser(HttpServletRequest request, HttpServletResponse response);
    void changeProfilePic(MultipartFile newProfilePic, String username) throws DbxException;
    void changeUsernameOfLoggedUser(changeUsernameBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changeEmailOfLoggedUser(changeEmailBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changePasswordOfLoggedUser(changePasswordBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changeFullNameOfLoggedUser(changeFullNameBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changeBirthdayOfLoggedUser(changeBirthdayBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    Set<userSearchViewModel> searchUsersByFullName(String parameter);
    boolean isUserArtist(String username) throws NotFoundException;
    boolean isUserArtistById(Long userId) throws NotFoundException;
    boolean isUserAdmin(String username) throws NotFoundException;
    boolean isUserAdminById(Long userId) throws NotFoundException, RoleNotFoundException;

    void addRoleToUser(Long userId, String roleName) throws Exception;
    void removeRoleOfUser(Long userId, String roleName) throws Exception;
}
