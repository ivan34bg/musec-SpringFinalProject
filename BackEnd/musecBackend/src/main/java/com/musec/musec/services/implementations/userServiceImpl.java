package com.musec.musec.services.implementations;

import com.musec.musec.entities.enums.roleEnum;
import com.musec.musec.entities.models.userRegisterBindingModel;
import com.musec.musec.entities.userEntity;
import com.musec.musec.repositories.roleRepository;
import com.musec.musec.repositories.userRepository;
import com.musec.musec.services.userService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
