package com.musec.musec.services.implementations;

import com.musec.musec.data.enums.roleEnum;
import com.musec.musec.data.models.bindingModels.userRegisterBindingModel;
import com.musec.musec.data.models.viewModels.profile.artistProfileViewModel;
import com.musec.musec.data.models.viewModels.profile.userProfilePlaylistViewModel;
import com.musec.musec.data.models.viewModels.profile.userProfileViewModel;
import com.musec.musec.data.playlistEntity;
import com.musec.musec.data.roleEntity;
import com.musec.musec.data.userEntity;
import com.musec.musec.repositories.roleRepository;
import com.musec.musec.repositories.userRepository;
import com.musec.musec.services.userService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class userServiceImpl implements userService {
    private final userRepository userRepo;
    private final roleRepository roleRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public userServiceImpl(
            userRepository userRepo,
            roleRepository roleRepo,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
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
                newUser.setRoles(Set.of(roleRepo.getByName(roleEnum.USER)));
                newUser.setBirthday(LocalDate.parse(bindingModel.getBirthday()));
                newUser.setProfilePicLink(
                        "https://www.dropbox.com/s/fv5ctkjbntsaubt/1200px-Question_Mark.svg.png?raw=1"
                );
                userRepo.save(newUser);
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
    public userProfileViewModel returnUserOrArtistProfileViewByUsername(String username, Boolean showPrivate) throws NotFoundException {
        Optional<userEntity> userOrNull = userRepo.findByUsername(username);
        if(userOrNull.isPresent()){
            userEntity user = userOrNull.get();
            if(user.getRoles().stream().anyMatch(r -> r.getName() == roleEnum.ARTIST)){
                artistProfileViewModel userToReturn = new artistProfileViewModel();
                modelMapper.map(user, userToReturn);
                userToReturn.setRoleNames(new HashSet<>());
                for (roleEntity role:user.getRoles()
                     ) {
                    userToReturn.getRoleNames().add(role.getName().name());
                }
                if(!showPrivate) {
                    userToReturn.setPlaylists(privatePlaylistChecker(user.getPlaylists()));
                }
                return userToReturn;
            }
            else {
                userProfileViewModel userToReturn = new userProfileViewModel();
                modelMapper.map(user, userToReturn);
                userToReturn.setRoleNames(new HashSet<>());
                for (roleEntity role:user.getRoles()
                ) {
                    userToReturn.getRoleNames().add(role.getName().name());
                }
                if(!showPrivate){
                    userToReturn.setPlaylists(privatePlaylistChecker(user.getPlaylists()));
                }
                return userToReturn;
            }
        }
        throw new NotFoundException("Cannot find user with this username");
    }

    @Override
    public userProfileViewModel returnUserOrArtistProfileViewById(Long userId) throws NotFoundException {
        Optional<userEntity> userOrNull = userRepo.findById(userId);
        if(userOrNull.isPresent()){
            return this.returnUserOrArtistProfileViewByUsername(userOrNull.get().getUsername(), false);
        }
        else throw new NotFoundException("User could not be found");
    }

    private Set<userProfilePlaylistViewModel> privatePlaylistChecker(Set<playlistEntity> playlists){
        Set<userProfilePlaylistViewModel> mappedPlaylists = new HashSet<>();
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
}
