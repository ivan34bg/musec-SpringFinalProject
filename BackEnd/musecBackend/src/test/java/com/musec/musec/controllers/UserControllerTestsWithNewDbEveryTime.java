package com.musec.musec.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.musec.musec.data.enums.RoleEnum;
import com.musec.musec.data.RoleEntity;
import com.musec.musec.data.UserEntity;
import com.musec.musec.repositories.QueueRepository;
import com.musec.musec.repositories.RoleRepository;
import com.musec.musec.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerTestsWithNewDbEveryTime {
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
        user.setRoles(List.of(roleRepo.getByRoleName(RoleEnum.USER)));
        userRepo.save(user);
    }

    @AfterEach
    void destroyer(){
        queueRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    void testRegisterEmailAlreadyInUse() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("username", "test1");
        request.add("password", "test1");
        request.add("email", "admin@admin.bg");
        request.add("fullName", "test1");
        request.add("birthday", "2001-02-02");
        mockMvc.perform(post("/user/register").params(request)).andExpect(status().is(400));
    }

    @Test
    void testRegisterUsernameAlreadyInUse() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("username", "admin");
        request.add("password", "test1");
        request.add("email", "test1@test.bg");
        request.add("fullName", "test1");
        request.add("birthday", "2001-02-02");
        mockMvc.perform(post("/user/register").params(request)).andExpect(status().is(400));
    }

    @Test
    void testRegisterUserSuccess() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("username", "ivan");
        request.add("password", "ivan");
        request.add("email", "ivan@test.bg");
        request.add("fullName", "ivan ivanov");
        request.add("birthday", "2001-02-02");

        MultiValueMap<String, String> login = new LinkedMultiValueMap<>();
        login.add("username", "ivan");
        login.add("password", "ivan");
        mockMvc.perform(post("/user/register").params(request)).andExpect(status().isOk());
        mockMvc.perform(post("/login").params(login)).andExpect(status().isOk());
    }

    @Test
    void testIsUserLoggedInFail() throws Exception {
        mockMvc.perform(get("/user/logged-in-test")).andExpect(status().is(403));
    }

    @Test
    @WithMockUser("test")
    void testIsUserLoggedInSuccess() throws Exception {
        mockMvc.perform(get("/user/logged-in-test")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser("test")
    void testSelfProfile() throws Exception {
        mockMvc.perform(get("/user/self-profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasEntry("fullName", "test testov")));
    }

    @Test
    @WithMockUser("test")
    void testProfileByIdFail() throws Exception{
        mockMvc.perform(get("/user/3"))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testProfileById() throws Exception {
        mockMvc.perform(get("/user/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasEntry("fullName", "test testov")));
    }

    @Test
    @WithMockUser("test")
    void testIsOtherUserArtistFail() throws Exception {
        mockMvc.perform(get("/user/artist/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @WithMockUser("test")
    void testIsOtherUserArtist() throws Exception {
        List<RoleEntity> roles = List.of(roleRepo.getByRoleName(RoleEnum.USER), roleRepo.getByRoleName(RoleEnum.ARTIST));
        UserEntity user = userRepo.findByUsername("test").get();
        user.setRoles(roles);
        userRepo.save(user);
        mockMvc.perform(get("/user/artist/2"))
                .andExpect(status().is(200))
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser("test")
    void testIsOtherUserAdminFail() throws Exception {
        mockMvc.perform(get("/user/admin/2"))
                .andExpect(status().isOk()).andExpect(content().string("false"));
    }

    @Test
    @WithMockUser("test")
    void testIsOtherUserAdmin() throws Exception {
        List<RoleEntity> roles = List.of(roleRepo.getByRoleName(RoleEnum.USER), roleRepo.getByRoleName(RoleEnum.ADMIN));
        UserEntity user = userRepo.findByUsername("test").get();
        user.setRoles(roles);
        userRepo.save(user);
        mockMvc.perform(get("/user/admin/2"))
                .andExpect(status().isOk()).andExpect(content().string("true"));
    }

}
