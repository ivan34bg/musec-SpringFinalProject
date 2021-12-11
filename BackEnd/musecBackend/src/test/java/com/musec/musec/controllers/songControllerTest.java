package com.musec.musec.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class songControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private queueRepository queueRepo;

    @Autowired
    private userRepository userRepo;

    @Autowired
    private songRepository songRepo;

    @Autowired
    private albumRepository albumRepo;

    @Autowired
    private singleRepository singleRepo;

    @BeforeEach
    void init(){
        userEntity user = new userEntity();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@test.bg");
        user.setFullName("test testov");
        userRepo.save(user);

        albumEntity album = new albumEntity();
        album.setAlbumName("testAlbum");
        album.setUploader(user);
        albumRepo.save(album);

        songEntity albumSong = new songEntity();
        albumSong.setSongName("testAlbumSong");
        albumSong.setUploader(user);
        albumSong.setAlbum(album);
        songRepo.save(albumSong);

        singleEntity single = new singleEntity();
        single.setSingleName("testSingle");
        single.setUploader(user);
        singleRepo.save(single);

        songEntity singleSong = new songEntity();
        singleSong.setSongName("testSingleSong");
        singleSong.setUploader(user);
        singleSong.setSingle(single);
        songRepo.save(singleSong);

        queueEntity queue = new queueEntity();
        queue.setUser(user);
        queueRepo.save(queue);
    }

    @AfterEach
    void destroy(){
        queueRepo.deleteAll();
        songRepo.deleteAll();
        albumRepo.deleteAll();
        singleRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    @WithMockUser("test")
    void testSearchNonExistentSong() throws Exception {
        mockMvc.perform(get("/song/search")
                .queryParam("param", "invalidSong"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testSearchSongInAlbum() throws Exception {
        mockMvc.perform(get("/song/search")
                .queryParam("param", "testAlbumSong"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("songName", "testAlbumSong")))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("albumName", "testAlbum")));
    }

    @Test
    @WithMockUser("test")
    void testSearchSongInSingle() throws Exception {
        mockMvc.perform(get("/song/search")
                .queryParam("param", "testSingleSong"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("songName", "testSingleSong")))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("singleName", "testSingle")));
    }

    @Test
    @WithMockUser("test")
    void testNewestTenSongsWithZeroSongs() throws Exception{
        queueRepo.deleteAll();
        songRepo.deleteAll();
        mockMvc.perform(get("/song/newest-ten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testNewestTenSongsWithLessThanTenSongs() throws Exception{
        mockMvc.perform(get("/song/newest-ten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("id", 2)))
                .andExpect(jsonPath("$[1]", Matchers.hasEntry("id", 1)));
    }

    @Test
    @WithMockUser("test")
    void testNewestTenSongsWithMoreThanTenSongs() throws Exception {
        for (int i = 2; i < 12; i++){
            songEntity singleSong = new songEntity();
            singleSong.setSongName("testSingleSong" + i);
            singleSong.setUploader(userRepo.getById(2L));
            singleSong.setSingle(singleRepo.getById(1L));
            songRepo.save(singleSong);
        }

        mockMvc.perform(get("/song/newest-ten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(10)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("id", 12)))
                .andExpect(jsonPath("$[9]", Matchers.hasEntry("id", 3)));
    }
}
