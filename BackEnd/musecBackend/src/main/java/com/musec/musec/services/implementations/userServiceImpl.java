package com.musec.musec.services.implementations;

import com.dropbox.core.DbxException;
import com.musec.musec.data.enums.roleEnum;
import com.musec.musec.data.models.bindingModels.changeCredentialsModels.*;
import com.musec.musec.data.models.bindingModels.userRegisterBindingModel;
import com.musec.musec.data.models.viewModels.profile.artistProfileViewModel;
import com.musec.musec.data.models.viewModels.profile.userProfilePlaylistViewModel;
import com.musec.musec.data.models.viewModels.profile.userProfileViewModel;
import com.musec.musec.data.models.viewModels.search.userSearchViewModel;
import com.musec.musec.data.playlistEntity;
import com.musec.musec.data.roleEntity;
import com.musec.musec.data.userEntity;
import com.musec.musec.repositories.userRepository;
import com.musec.musec.services.userService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class userServiceImpl implements userService {
    private final queueServiceImpl queueService;
    private final cloudServiceImpl cloudService;
    private final roleServiceImpl roleService;
    private final userRepository userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public userServiceImpl(
            queueServiceImpl queueService,
            cloudServiceImpl cloudService,
            userRepository userRepo,
            roleServiceImpl roleService,
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
    public void registerUser(userRegisterBindingModel bindingModel) throws Exception {
        if(userRepo.findByUsername(bindingModel.getUsername()).isEmpty()){
            if(userRepo.findByEmail(bindingModel.getEmail()).isEmpty()){
                userEntity newUser = new userEntity();
                modelMapper.map(bindingModel, newUser);
                newUser.setPassword(passwordEncoder.encode(bindingModel.getPassword()));
                newUser.setRoles(Set.of(roleService.returnRoleByName("USER")));
                newUser.setBirthday(LocalDate.parse(bindingModel.getBirthday()));
                newUser.setProfilePicLink(
                        "https://www.dropbox.com/s/fv5ctkjbntsaubt/1200px-Question_Mark.svg.png?raw=1"
                );
                userRepo.save(newUser);
                queueService.createQueue(newUser);
            }
            else
                throw new Exception("Email has already been taken");
        }
        else
            throw new Exception("Username has already been taken");
    }

    @Override
    public userEntity returnExistingUserByUsername(String username) {
        return userRepo.findByUsername(username).get();
    }

    @Override
    public userEntity returnUserById(Long userId) throws NotFoundException {
        Optional<userEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new NotFoundException("User cannot be found");
    }

    @Override
    public userProfileViewModel returnUserOrArtistProfileViewByUsername(String username, Boolean showPrivate) {
        userEntity user = userRepo.findByUsername(username).get();
        if(user.getRoles().stream().anyMatch(r -> r.getRoleName() == roleEnum.ARTIST)){
            artistProfileViewModel userToReturn = new artistProfileViewModel();
            modelMapper.map(user, userToReturn);
            userToReturn.setRoleNames(new LinkedHashSet<>());
            for (roleEntity role:user.getRoles()
            ) {
                userToReturn.getRoleNames().add(role.getRoleName().name());
            }
            if(!showPrivate) {
                userToReturn.setPlaylists(privatePlaylistChecker(user.getPlaylists()));
            }
            return userToReturn;
        }
        else {
            userProfileViewModel userToReturn = new userProfileViewModel();
            modelMapper.map(user, userToReturn);
            userToReturn.setRoleNames(new LinkedHashSet<>());
            for (roleEntity role:user.getRoles()
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
    public userProfileViewModel returnUserOrArtistProfileViewById(Long userId) throws NotFoundException {
        Optional<userEntity> userOrNull = userRepo.findById(userId);
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
        userEntity user = this.userRepo.findByUsername(username).get();
        if(user.getProfilePicFilePath() != null){
            cloudService.deleteFile(user.getProfilePicFilePath());
        }
        user.setProfilePicFilePath(cloudService.uploadProfilePic(newProfilePic));
        user.setProfilePicLink(cloudService.returnDirectLinkOfFile(user.getProfilePicFilePath()));
        userRepo.save(user);
    }

    @Override
    public void changeUsernameOfLoggedUser(changeUsernameBindingModel bindingModel, String usernameOfLoggedUser)
            throws Exception {
        if(userRepo.findByUsername(bindingModel.getNewUsername()).isEmpty()){
            userEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
            user.setUsername(bindingModel.getNewUsername());
            userRepo.save(user);
        }
        else throw new Exception("Username is already taken");
    }

    @Override
    public void changeEmailOfLoggedUser(changeEmailBindingModel bindingModel, String usernameOfLoggedUser)
            throws Exception {
        if(userRepo.findByEmail(bindingModel.getNewEmail()).isEmpty()) {
            userEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
            user.setEmail(bindingModel.getNewEmail());
            userRepo.save(user);
        }
        else throw new Exception("Email is already taken");
    }

    @Override
    public void changePasswordOfLoggedUser(changePasswordBindingModel bindingModel, String usernameOfLoggedUser) throws Exception {
        userEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
        user.setPassword(passwordEncoder.encode(bindingModel.getNewPassword()));
        userRepo.save(user);
    }

    @Override
    public void changeFullNameOfLoggedUser(changeFullNameBindingModel bindingModel, String usernameOfLoggedUser) throws Exception {
        userEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
        user.setFullName(bindingModel.getNewFullName());
        userRepo.save(user);
    }

    @Override
    public void changeBirthdayOfLoggedUser(changeBirthdayBindingModel bindingModel, String usernameOfLoggedUser) throws Exception {
        userEntity user = fetchUserAndCheckIfPasswordIsValid(usernameOfLoggedUser, bindingModel.getOldPassword());
        user.setBirthday(LocalDate.parse(bindingModel.getNewBirthday()));
        userRepo.save(user);
    }

    @Override
    public Set<userSearchViewModel> searchUsersByFullName(String parameter) {
        Set<userSearchViewModel> setToReturn = new LinkedHashSet<>();
        if(!parameter.trim().equals("")){
            Optional<Set<userEntity>> usersOrNull = userRepo.findAllByFullNameContains(parameter);
            if(!usersOrNull.get().isEmpty()){
                for (userEntity user:usersOrNull.get()
                ) {
                    userSearchViewModel mappedUser = new userSearchViewModel();
                    modelMapper.map(user, mappedUser);
                    setToReturn.add(mappedUser);
                }
            }
        }
        return setToReturn;
    }

    @Override
    public boolean isUserArtist(String username) throws NotFoundException {
        userEntity user = userRepo.findByUsername(username).get();
        return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(roleEnum.ARTIST));
    }

    @Override
    public boolean isUserArtistById(Long userId) throws NotFoundException {
        Optional<userEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            return isUserArtist(user.get().getUsername());
        }
        else throw new NotFoundException("User with this id cannot be found");
    }

    @Override
    public boolean isUserAdmin(String username) {
        userEntity user = userRepo.findByUsername(username).get();
        return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(roleEnum.ADMIN));
    }

    @Override
    public boolean isUserAdminById(Long userId) throws NotFoundException{
        Optional<userEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            return isUserAdmin(user.get().getUsername());
        }
        else throw new NotFoundException("User with this id cannot be found");

    }

    //Admin methods

    @Override
    public void addRoleToUser(Long userId, String roleName) throws Exception {
        Optional<userEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            roleEntity role = roleService.returnRoleByName(roleName);
            if (user.get().getRoles().stream().noneMatch(r -> r.getRoleName().name().equals("ADMIN"))){
                if(!user.get().getRoles().contains(role)){
                    user.get().getRoles().add(role);
                    userRepo.save(user.get());
                }
                else throw new Exception("User already has this role");
            }
            else throw new Exception("Cannot change roles of other admins");
        }
        else throw new NotFoundException("User not found");
    }

    @Override
    public void removeRoleOfUser(Long userId, String roleName) throws Exception {
        Optional<userEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            roleEntity role = roleService.returnRoleByName(roleName);
            if (user.get().getRoles().stream().noneMatch(r -> r.getRoleName().name().equals("ADMIN"))) {
                if (user.get().getRoles().contains(role)) {
                    user.get().getRoles().remove(role);
                    userRepo.save(user.get());
                } else throw new Exception("User does not have this role");
            }
            else throw new Exception("Cannot change roles of other admins");
        }
        else throw new NotFoundException("User not found");
    }

    private Set<userProfilePlaylistViewModel> privatePlaylistChecker(Set<playlistEntity> playlists){
        Set<userProfilePlaylistViewModel> mappedPlaylists = new LinkedHashSet<>();
        for (playlistEntity playlist:playlists
        ) {
            if(playlist.isPublic()){
                userProfilePlaylistViewModel mappedPlaylist = new userProfilePlaylistViewModel();
                modelMapper.map(playlist, mappedPlaylist);
                mappedPlaylists.add(mappedPlaylist);
            }
        }
        return mappedPlaylists;
    }
    private userEntity fetchUserAndCheckIfPasswordIsValid(String usernameOfLoggedUser, String password) throws Exception {
        Optional<userEntity> userOrNull = userRepo.findByUsername(usernameOfLoggedUser);
        if(userOrNull.isPresent()){
            if(passwordEncoder.matches(password, userOrNull.get().getPassword())){
                return userOrNull.get();
            }
            throw new Exception("Password is wrong");
        }
        throw new NotFoundException("User not found");
    }
}
