package com.musec.musec;

import com.musec.musec.data.enums.genreEnum;
import com.musec.musec.data.enums.roleEnum;
import com.musec.musec.data.genreEntity;
import com.musec.musec.data.roleEntity;
import com.musec.musec.data.userEntity;
import com.musec.musec.repositories.genreRepository;
import com.musec.musec.repositories.roleRepository;
import com.musec.musec.repositories.userRepository;
import com.musec.musec.services.implementations.queueServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInit implements CommandLineRunner {
    private final genreRepository genreRepo;
    private final roleRepository roleRepo;
    private final userRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final queueServiceImpl queueService;

    public DataInit(
            genreRepository genreRepo,
            roleRepository roleRepo,
            userRepository userRepo,
            PasswordEncoder passwordEncoder,
            queueServiceImpl queueService) {

        this.genreRepo = genreRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.queueService = queueService;
    }

    @Override
    public void run(String... args) throws Exception {
        genreInit();
        userInit();
    }

    private void genreInit() {
        if(genreRepo.count() == 0){
            for (genreEnum genre:genreEnum.values()
                 ) {
                genreEntity genreToSave = new genreEntity();
                genreToSave.setGenreName(genre);
                genreToSave.setProperName(genre.getProperName());
                genreRepo.save(genreToSave);
            }
        }
        if(roleRepo.count() == 0){
            for (roleEnum role:roleEnum.values()
                 ) {
                roleEntity newRole = new roleEntity();
                newRole.setRoleName(role);
                roleRepo.save(newRole);
            }
        }
    }
    private void userInit() {
        if(userRepo.count() == 0){
            userEntity user = new userEntity();
            user.setUsername("admin");
            //test
            user.setPassword(
                    passwordEncoder
                            .encode("ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff")
            );
            user.setFullName("admin adminov");
            user.setEmail("admin@admin.bg");
            Set<roleEntity> roles = new HashSet<>();
            roles.add(roleRepo.getByRoleName(roleEnum.USER));
            roles.add(roleRepo.getByRoleName(roleEnum.ADMIN));
            roles.add(roleRepo.getByRoleName(roleEnum.ARTIST));
            user.setRoles(roles);
            user.setProfilePicLink("https://www.dropbox.com/s/fv5ctkjbntsaubt/1200px-Question_Mark.svg.png?raw=1");
            userRepo.save(user);
            queueService.createQueue(user);
        }
    }
}
