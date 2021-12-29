package com.musec.musec.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.musec.musec.data.enums.RoleEnum;
import com.musec.musec.data.RoleEntity;
import com.musec.musec.data.UserEntity;
import com.musec.musec.repositories.QueueRepository;
import com.musec.musec.repositories.RoleRepository;
import com.musec.musec.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestsWithTheSameDb {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private QueueRepository queueRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init(){
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.bg");
        user.setFullName("test testov");
        userRepo.save(user);
    }

    @AfterEach
    void destroyer(){
        queueRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    @WithMockUser("test")
    void testChangeUsername() throws Exception {
        MultiValueMap<String, String> loginOld = new LinkedMultiValueMap<>();
        loginOld.add("username", "test");
        loginOld.add("password", "test");

        MultiValueMap<String, String> newUsername = new LinkedMultiValueMap<>();
        newUsername.add("newUsername", "test2");
        newUsername.add("oldPassword", "test");

        MultiValueMap<String, String> loginNew = new LinkedMultiValueMap<>();
        loginNew.add("username", "test2");
        loginNew.add("password", "test");

        mockMvc.perform(post("/login").params(loginOld)).andExpect(status().isOk());
        mockMvc.perform(post("/user/username").params(newUsername)).andExpect(status().isOk());
        mockMvc.perform(post("/login").params(loginNew)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser("test")
    void testChangePassword() throws Exception {
        MultiValueMap<String, String> loginOld = new LinkedMultiValueMap<>();
        loginOld.add("username", "test");
        loginOld.add("password", "test");

        MultiValueMap<String, String> newUsername = new LinkedMultiValueMap<>();
        newUsername.add("newPassword", "test2");
        newUsername.add("oldPassword", "test");

        MultiValueMap<String, String> loginNew = new LinkedMultiValueMap<>();
        loginNew.add("username", "test");
        loginNew.add("password", "test2");

        mockMvc.perform(post("/login").params(loginOld)).andExpect(status().isOk());
        mockMvc.perform(post("/user/password").params(newUsername)).andExpect(status().isOk());
        mockMvc.perform(post("/login").params(loginNew)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser("test")
    void testChangeFullName() throws Exception{
        MultiValueMap<String, String> newFullName = new LinkedMultiValueMap<>();
        newFullName.add("newFullName", "test test");
        newFullName.add("oldPassword", "test");

        mockMvc.perform(get("/user/self-profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasEntry("fullName", "test testov")));
        mockMvc.perform(post("/user/full-name").params(newFullName)).andExpect(status().isOk());
        mockMvc.perform(get("/user/self-profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasEntry("fullName", "test test")));
    }

    @Test
    @WithMockUser("test")
    void testIsArtistFail() throws Exception{
        mockMvc.perform(get("/user/artist"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @WithMockUser("test")
    void testIsArtist() throws Exception{
        List<RoleEntity> roles = List.of(roleRepo.getByRoleName(RoleEnum.USER), roleRepo.getByRoleName(RoleEnum.ARTIST));
        UserEntity user = userRepo.findByUsername("test").get();
        user.setRoles(roles);
        userRepo.save(user);
        mockMvc.perform(get("/user/artist"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser("test")
    void testIsAdminFail() throws Exception{
        mockMvc.perform(get("/user/admin"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(value = "test", roles = {"USER", "ADMIN"})
    void testIsAdmin() throws Exception{
        List<RoleEntity> roles = List.of(roleRepo.getByRoleName(RoleEnum.USER), roleRepo.getByRoleName(RoleEnum.ADMIN));
        UserEntity user = userRepo.findByUsername("test").get();
        user.setRoles(roles);
        userRepo.save(user);
        mockMvc.perform(get("/user/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser("test")
    void testSearch() throws Exception{
        mockMvc.perform(get("/user/search").queryParam("param", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("fullName", "test testov")));
    }
}
