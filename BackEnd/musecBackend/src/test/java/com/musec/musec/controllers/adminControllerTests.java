package com.musec.musec.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.musec.musec.data.enums.roleEnum;
import com.musec.musec.data.userEntity;
import com.musec.musec.repositories.queueRepository;
import com.musec.musec.repositories.roleRepository;
import com.musec.musec.repositories.userRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class adminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private userRepository userRepo;

    @Autowired
    private roleRepository roleRepo;

    @Autowired
    private queueRepository queueRepo;

    @BeforeEach
    void init(){
        userEntity user = new userEntity();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@test.bg");
        user.setRoles(List.of(roleRepo.getByRoleName(roleEnum.USER)));
        user.setFullName("test testov");
        userRepo.save(user);

        userEntity admin = new userEntity();
        admin.setUsername("testAdmin");
        admin.setPassword("testAdmin");
        admin.setEmail("testAdmin@test.bg");
        admin.setRoles(List.of(roleRepo.getByRoleName(roleEnum.ADMIN)));
        admin.setFullName("admin testov");
        userRepo.save(admin);
    }

    @AfterEach
    void destroy(){
        queueRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    @WithMockUser(value = "testAdmin", roles = {"ADMIN"})
    void testAddRoleToAdmin() throws Exception {
        mockMvc.perform(post("/admin/role/" + userRepo.findByUsername("testAdmin").get().getId())
                .queryParam("role", "ARTIST"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(value = "testAdmin", roles = {"ADMIN"})
    void testAddRoleToNonExistentUser() throws Exception {
        mockMvc.perform(post("/admin/role/7")
                .queryParam("role", "ARTIST"))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(value = "testAdmin", roles = {"ADMIN"})
    void testAddAlreadyExistingRole() throws Exception {
        userEntity user = userRepo.findByUsername("test").get();
        user.setRoles(List.of(roleRepo.getByRoleName(roleEnum.ARTIST)));
        userRepo.save(user);

        mockMvc.perform(post("/admin/role/2")
                .queryParam("role", "ARTIST"))
                .andExpect(status().is(400));
    }

    @Test
    @WithMockUser(value = "testAdmin", roles = {"ADMIN"})
    void testAddRoleToUserValid() throws Exception {
        mockMvc.perform(get("/user/artist/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
        mockMvc.perform(post("/admin/role/2")
                .queryParam("role", "ARTIST"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user/artist/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser(value = "testAdmin", roles = {"ADMIN"})
    void testDeleteRoleOfAdmin() throws Exception {
        mockMvc.perform(delete("/admin/role/" + userRepo.findByUsername("testAdmin").get().getId())
                .queryParam("role", "ARTIST"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(value = "testAdmin", roles = {"ADMIN"})
    void testDeleteRoleOfNonExistentUser() throws Exception {
        mockMvc.perform(delete("/admin/role/7")
                .queryParam("role", "ARTIST"))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(value = "testAdmin", roles = {"ADMIN"})
    void testDeleteRoleOfUserWhoDoesntHaveIt() throws Exception {
        mockMvc.perform(delete("/admin/role/2")
                .queryParam("role", "ARTIST"))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(value = "testAdmin", roles = {"ADMIN"})
    void testDeleteRoleOfUserValid() throws Exception {
        userEntity user = userRepo.findByUsername("test").get();
        user.setRoles(List.of(roleRepo.getByRoleName(roleEnum.ARTIST)));
        userRepo.save(user);

        mockMvc.perform(get("/user/artist/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        mockMvc.perform(delete("/admin/role/2")
                .queryParam("role", "ARTIST"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user/artist/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
