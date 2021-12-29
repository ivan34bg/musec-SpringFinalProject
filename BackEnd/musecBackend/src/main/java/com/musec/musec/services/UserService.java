package com.musec.musec.services;

import com.dropbox.core.DbxException;
import com.musec.musec.data.models.bindingModels.changeCredentialsModels.*;
import com.musec.musec.data.models.bindingModels.UserRegisterBindingModel;
import com.musec.musec.data.models.viewModels.profile.UserProfileViewModel;
import com.musec.musec.data.models.viewModels.search.UserSearchViewModel;
import com.musec.musec.data.UserEntity;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    void registerUser(UserRegisterBindingModel bindingModel) throws Exception;
    UserEntity returnExistingUserByUsername(String username);
    UserEntity returnUserById(Long userId) throws NotFoundException;
    UserProfileViewModel returnUserOrArtistProfileViewByUsername(String username, Boolean showPrivate) throws NotFoundException;
    UserProfileViewModel returnUserOrArtistProfileViewById(Long userId) throws NotFoundException;
    void logoutUser(HttpServletRequest request, HttpServletResponse response);
    void changeProfilePic(MultipartFile newProfilePic, String username) throws DbxException;
    void changeUsernameOfLoggedUser(ChangeUsernameBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changeEmailOfLoggedUser(ChangeEmailBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changePasswordOfLoggedUser(ChangePasswordBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changeFullNameOfLoggedUser(ChangeFullNameBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    void changeBirthdayOfLoggedUser(ChangeBirthdayBindingModel bindingModel, String usernameOfLoggedUser) throws Exception;
    List<UserSearchViewModel> searchUsersByFullName(String parameter);
    boolean isUserArtist(String username) throws NotFoundException;
    boolean isUserArtistById(Long userId) throws NotFoundException;
    boolean isUserAdmin(String username) throws NotFoundException;
    boolean isUserAdminById(Long userId) throws NotFoundException, RoleNotFoundException;

    void addRoleToUser(Long userId, String roleName) throws Exception;
    void removeRoleOfUser(Long userId, String roleName) throws Exception;
}
