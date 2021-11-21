package com.musec.musec;

import com.musec.musec.data.enums.genreEnum;
import com.musec.musec.data.enums.roleEnum;
import com.musec.musec.data.genreEntity;
import com.musec.musec.data.models.bindingModels.userRegisterBindingModel;
import com.musec.musec.data.roleEntity;
import com.musec.musec.repositories.genreRepository;
import com.musec.musec.repositories.roleRepository;
import com.musec.musec.repositories.userRepository;
import com.musec.musec.services.implementations.userServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataInit implements CommandLineRunner {
    private final genreRepository genreRepo;
    private final roleRepository roleRepo;
    private final userRepository userRepo;
    private final userServiceImpl userService;

    public DataInit(
            genreRepository genreRepo,
            roleRepository roleRepo,
            userRepository userRepo,
            userServiceImpl userService) {

        this.genreRepo = genreRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.userService = userService;

    }

    @Override
    public void run(String... args) throws Exception {
        genreInit();
    }

    private void genreInit() throws Exception {
        if(genreRepo.count() == 0){
            for (genreEnum genre:genreEnum.values()
                 ) {
                genreEntity genreToSave = new genreEntity(
                        genre.name(),
                        new Color(
                                ThreadLocalRandom.current().nextInt(0,256),
                                ThreadLocalRandom.current().nextInt(0,256),
                                ThreadLocalRandom.current().nextInt(0,256)
                        ));
                genreRepo.save(genreToSave);
            }
        }
        if(roleRepo.count() == 0){
            for (roleEnum role:roleEnum.values()
                 ) {
                roleEntity newRole = new roleEntity();
                newRole.setName(role);
                roleRepo.save(newRole);
            }
        }
        if(userRepo.count() == 0){
            userRegisterBindingModel newUser = new userRegisterBindingModel();
            newUser.setUsername("admin");
            newUser.setPassword("test");
            newUser.setEmail("admin@test.com");
            newUser.setFullName("admin testov");
            newUser.setBirthday("2016-08-06");
            userService.registerUser(newUser);
        }
    }
}
