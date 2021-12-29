package com.musec.musec.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.musec.musec.data.*;
import com.musec.musec.repositories.*;
import org.hamcrest.Matchers;
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

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SingleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QueueRepository queueRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SongRepository songRepo;

    @Autowired
    private SingleRepository singleRepo;

    @BeforeEach
    void init(){
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@test.bg");
        user.setFullName("test testov");
        userRepo.save(user);

        SingleEntity single = new SingleEntity();
        single.setSingleName("testSingle");
        single.setUploader(user);
        singleRepo.save(single);

        SongEntity song = new SongEntity();
        song.setSongName("testSong");
        song.setUploader(user);
        song.setSingle(single);
        songRepo.save(song);

        QueueEntity queue = new QueueEntity();
        queue.setUser(user);
        queue.setSongs(List.of(song));
        queueRepo.save(queue);
    }

    @AfterEach
    void destroy(){
        queueRepo.deleteAll();
        songRepo.deleteAll();
        singleRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    @WithMockUser("test")
    void testReturnInvalidSingleById() throws Exception {
        mockMvc.perform(get("/single/2")).andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testReturnValidSingleById() throws Exception {
        mockMvc.perform(get("/single/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasEntry("singleName", "testSingle")));
    }

    @Test
    @WithMockUser("test")
    void testSinglesByLoggedUser() throws Exception {
        mockMvc.perform(get("/single/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("singleName", "testSingle")));
    }

    @Test
    @WithMockUser("test")
    void testReturnSinglesOfNonExistentUser() throws Exception {
        mockMvc.perform(get("/single/user/7")).andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testReturnSinglesOfValidUser() throws Exception {
        mockMvc.perform(get("/single/user/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("singleName", "testSingle")));
    }

    @Test
    @WithMockUser("test")
    void testSearchInvalidParam() throws Exception {
        mockMvc.perform(get("/single/search").queryParam("param", "invalidSingle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testSearchValidParam() throws Exception {
        mockMvc.perform(get("/single/search").queryParam("param", "testSingle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("singleName", "testSingle")));
    }
}
