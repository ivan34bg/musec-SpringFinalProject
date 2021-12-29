package com.musec.musec;

import com.musec.musec.data.enums.GenreEnum;
import com.musec.musec.data.enums.RoleEnum;
import com.musec.musec.data.GenreEntity;
import com.musec.musec.data.RoleEntity;
import com.musec.musec.data.UserEntity;
import com.musec.musec.repositories.GenreRepository;
import com.musec.musec.repositories.RoleRepository;
import com.musec.musec.repositories.UserRepository;
import com.musec.musec.services.implementations.QueueServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInit implements CommandLineRunner {
    private final GenreRepository genreRepo;
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final QueueServiceImpl queueService;

    public DataInit(
            GenreRepository genreRepo,
            RoleRepository roleRepo,
            UserRepository userRepo,
            PasswordEncoder passwordEncoder,
            QueueServiceImpl queueService) {

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
            for (GenreEnum genre: GenreEnum.values()
                 ) {
                GenreEntity genreToSave = new GenreEntity();
                genreToSave.setGenreName(genre);
                genreToSave.setProperName(genre.getProperName());
                genreRepo.save(genreToSave);
            }
        }
        if(roleRepo.count() == 0){
            for (RoleEnum role: RoleEnum.values()
                 ) {
                RoleEntity newRole = new RoleEntity();
                newRole.setRoleName(role);
                roleRepo.save(newRole);
            }
        }
    }
    private void userInit() {
        if(userRepo.count() == 0){
            UserEntity user = new UserEntity();
            user.setUsername("admin");
            //test
            user.setPassword(
                    passwordEncoder
                            .encode("ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff")
            );
            user.setFullName("admin adminov");
            user.setEmail("admin@admin.bg");
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(roleRepo.getByRoleName(RoleEnum.USER));
            roles.add(roleRepo.getByRoleName(RoleEnum.ADMIN));
            roles.add(roleRepo.getByRoleName(RoleEnum.ARTIST));
            user.setRoles(roles);
            user.setProfilePicLink("https://www.dropbox.com/s/fv5ctkjbntsaubt/1200px-Question_Mark.svg.png?raw=1");
            userRepo.save(user);
            queueService.createQueue(user);
        }
    }
}
