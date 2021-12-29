package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.enums.RoleEnum;
import com.musec.musec.data.models.bindingModels.changeCredentialsModels.*;
import com.musec.musec.data.models.bindingModels.UserRegisterBindingModel;
import com.musec.musec.data.models.viewModels.profile.ArtistProfileViewModel;
import com.musec.musec.data.models.viewModels.profile.UserProfilePlaylistViewModel;
import com.musec.musec.data.models.viewModels.profile.UserProfileViewModel;
import com.musec.musec.data.models.viewModels.search.UserSearchViewModel;
import com.musec.musec.data.PlaylistEntity;
import com.musec.musec.data.RoleEntity;
import com.musec.musec.data.UserEntity;
import com.musec.musec.repositories.UserRepository;
import com.musec.musec.services.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.rmi.AccessException;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final QueueServiceImpl queueService;
    private final CloudServiceImpl cloudService;
    private final RoleServiceImpl roleService;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            QueueServiceImpl queueService,
            CloudServiceImpl cloudService,
            UserRepository userRepo,
            RoleServiceImpl roleService,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder) {
        this.queueService = queueService;
        this.cloudService = cloudService;
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegisterBindingModel bindingModel) throws CloneNotSupportedException {
        if(userRepo.findByUsername(bindingModel.getUsername()).isEmpty()){
            if(userRepo.findByEmail(bindingModel.getEmail()).isEmpty()){
                UserEntity newUser = new UserEntity();
                modelMapper.map(bindingModel, newUser);
                newUser.setPassword(passwordEncoder.encode(bindingModel.getPassword()));
                newUser.setRoles(List.of(roleService.returnRoleByName("USER")));
                newUser.setBirthday(LocalDate.parse(bindingModel.getBirthday()));
                newUser.setProfilePicLink(
                        "https://www.dropbox.com/s/fv5ctkjbntsaubt/1200px-Question_Mark.svg.png?raw=1"
                );
                userRepo.save(newUser);
                queueService.createQueue(newUser);
            }
            else
                throw new CloneNotSupportedException("Email has already been taken");
        }
        else
            throw new CloneNotSupportedException("Username has already been taken");
    }

    @Override
    public UserEntity returnExistingUserByUsername(String username) {
        return userRepo.findByUsername(username).get();
    }

    @Override
    public UserEntity returnUserById(Long userId) throws NotFoundException {
        Optional<UserEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new NotFoundException("User cannot be found");
    }

    @Override
    public UserProfileViewModel returnUserOrArtistProfileViewByUsername(String username, Boolean showPrivate) {
        UserEntity user = userRepo.findByUsername(username).get();
        if(user.getRoles().stream().anyMatch(r -> r.getRoleName() == RoleEnum.ARTIST)){
            ArtistProfileViewModel userToReturn = new ArtistProfileViewModel();
            modelMapper.map(user, userToReturn);
            userToReturn.setRoleNames(new ArrayList<>());
            for (RoleEntity role:user.getRoles()
            ) {
                userToReturn.getRoleNames().add(role.getRoleName().name());
            }
            if(!showPrivate) {
                userToReturn.setPlaylists(privatePlaylistChecker(user.getPlaylists()));
            }
            return userToReturn;
        }
        else {
            UserProfileViewModel userToReturn = new UserProfileViewModel();
            modelMapper.map(user, userToReturn);
            userToReturn.setRoleNames(new ArrayList<>());
            for (RoleEntity role:user.getRoles()
            ) {
                userToReturn.getRoleNames().add(role.getRoleName().name());
            }
            if(!showPrivate){
                userToReturn.setPlaylists(privatePlaylistChecker(user.getPlaylists()));
            }
            return userToReturn;
        }
    }

    @Override
    public UserProfileViewModel returnUserOrArtistProfileViewById(Long userId) throws NotFoundException {
        Optional<UserEntity> userOrNull = userRepo.findById(userId);
        if(userOrNull.isPresent()){
            return this.returnUserOrArtistProfileViewByUsername(userOrNull.get().getUsername(), false);
        }
        else throw new NotFoundException("User could not be found");
    }

    @Override
    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        for (Cookie cookie:request.getCookies()
             ) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    @Override
    public void changeProfilePic(MultipartFile newProfilePic, String username) throws DbxException {
        UserEntity user = this.userRepo.findByUsername(username).get();
        if(user.getProfilePicFilePath() != null){
            cloudService.deleteFile(user.getProfilePicFilePath());
        }
        user.setProfilePicFilePath(cloudService.uploadProfilePic(newProfilePic));
        user.setProfilePicLink(cloudService.returnDirectLinkOfFile(user.getProfilePicFilePath()));
        userRepo.save(user);
    }

    @Override
    public void changeUsernameOfLoggedUser(ChangeUsernameBindingModel bindingModel, String usernameOfLoggedUser)
            throws NotFoundException, CloneNotSupportedException, AccessDeniedException {
        if(userRepo.findByUsername(bindingModel.getNewUsername()).isEmpty()){
            UserEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
            user.setUsername(bindingModel.getNewUsername());
            userRepo.save(user);
        }
        else throw new CloneNotSupportedException("Username is already taken");
    }

    @Override
    public void changeEmailOfLoggedUser(ChangeEmailBindingModel bindingModel, String usernameOfLoggedUser)
            throws NotFoundException, AccessDeniedException, CloneNotSupportedException {
        if(userRepo.findByEmail(bindingModel.getNewEmail()).isEmpty()) {
            UserEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
            user.setEmail(bindingModel.getNewEmail());
            userRepo.save(user);
        }
        else throw new CloneNotSupportedException("Email is already taken");
    }

    @Override
    public void changePasswordOfLoggedUser(ChangePasswordBindingModel bindingModel, String usernameOfLoggedUser)
            throws NotFoundException, AccessDeniedException {
        UserEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
        user.setPassword(passwordEncoder.encode(bindingModel.getNewPassword()));
        userRepo.save(user);
    }

    @Override
    public void changeFullNameOfLoggedUser(ChangeFullNameBindingModel bindingModel, String usernameOfLoggedUser)
            throws NotFoundException, AccessDeniedException {
        UserEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
        user.setFullName(bindingModel.getNewFullName());
        userRepo.save(user);
    }

    @Override
    public void changeBirthdayOfLoggedUser(ChangeBirthdayBindingModel bindingModel, String usernameOfLoggedUser)
            throws NotFoundException, AccessDeniedException {
        UserEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
        user.setBirthday(LocalDate.parse(bindingModel.getNewBirthday()));
        userRepo.save(user);
    }

    @Override
    public List<UserSearchViewModel> searchUsersByFullName(String parameter) {
        List<UserSearchViewModel> setToReturn = new ArrayList<>();
        if(!parameter.trim().equals("")){
            Optional<List<UserEntity>> usersOrNull = userRepo.findAllByFullNameContains(parameter);
            if(!usersOrNull.get().isEmpty()){
                for (UserEntity user:usersOrNull.get()
                ) {
                    UserSearchViewModel mappedUser = new UserSearchViewModel();
                    modelMapper.map(user, mappedUser);
                    setToReturn.add(mappedUser);
                }
            }
        }
        return setToReturn;
    }

    @Override
    public boolean isUserArtist(String username) throws NotFoundException {
        UserEntity user = userRepo.findByUsername(username).get();
        return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(RoleEnum.ARTIST));
    }

    @Override
    public boolean isUserArtistById(Long userId) throws NotFoundException {
        Optional<UserEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            return isUserArtist(user.get().getUsername());
        }
        else throw new NotFoundException("User with this id cannot be found");
    }

    @Override
    public boolean isUserAdmin(String username) {
        UserEntity user = userRepo.findByUsername(username).get();
        return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(RoleEnum.ADMIN));
    }

    @Override
    public boolean isUserAdminById(Long userId) throws NotFoundException{
        Optional<UserEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            return isUserAdmin(user.get().getUsername());
        }
        else throw new NotFoundException("User with this id cannot be found");

    }

    //Admin methods

    @Override
    public void addRoleToUser(Long userId, String roleName) throws CloneNotSupportedException, AccessException, NotFoundException {
        Optional<UserEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            if (user.get().getRoles().stream().noneMatch(r -> r.getRoleName().name().equals("ADMIN"))){
                RoleEntity role = roleService.returnRoleByName(roleName);
                if(!user.get().getRoles().contains(role)){
                    user.get().getRoles().add(role);
                    userRepo.save(user.get());
                }
                else throw new CloneNotSupportedException("User already has this role");
            }
            else throw new AccessException("Cannot change roles of other admins");
        }
        else throw new NotFoundException("User not found");
    }

    @Override
    public void removeRoleOfUser(Long userId, String roleName) throws NotFoundException, AccessException {
        Optional<UserEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            RoleEntity role = roleService.returnRoleByName(roleName);
            if (user.get().getRoles().stream().noneMatch(r -> r.getRoleName().name().equals("ADMIN"))) {
                if (user.get().getRoles().contains(role)) {
                    user.get().getRoles().remove(role);
                    userRepo.save(user.get());
                } else throw new NotFoundException("User does not have this role");
            }
            else throw new AccessException("Cannot change roles of other admins");
        }
        else throw new NotFoundException("User not found");
    }

    private List<UserProfilePlaylistViewModel> privatePlaylistChecker(List<PlaylistEntity> playlists){
        List<UserProfilePlaylistViewModel> mappedPlaylists = new ArrayList<>();
        for (PlaylistEntity playlist:playlists
        ) {
            if(playlist.isPublic()){
                UserProfilePlaylistViewModel mappedPlaylist = new UserProfilePlaylistViewModel();
                modelMapper.map(playlist, mappedPlaylist);
                mappedPlaylists.add(mappedPlaylist);
            }
        }
        return mappedPlaylists;
    }
    private UserEntity fetchUserAndCheckIfPasswordIsValid(String usernameOfLoggedUser, String password)
            throws NotFoundException, AccessDeniedException {
        Optional<UserEntity> userOrNull = userRepo.findByUsername(usernameOfLoggedUser);
        if(userOrNull.isPresent()){
            if(passwordEncoder.matches(password, userOrNull.get().getPassword())){
                return userOrNull.get();
            }
            else throw new AccessDeniedException("Password is wrong");
        }
        else throw new NotFoundException("User not found");
    }
}
