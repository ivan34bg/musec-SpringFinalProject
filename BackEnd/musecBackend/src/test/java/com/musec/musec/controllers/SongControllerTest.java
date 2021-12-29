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
public class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QueueRepository queueRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SongRepository songRepo;

    @Autowired
    private AlbumRepository albumRepo;

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

        AlbumEntity album = new AlbumEntity();
        album.setAlbumName("testAlbum");
        album.setUploader(user);
        albumRepo.save(album);

        SongEntity albumSong = new SongEntity();
        albumSong.setSongName("testAlbumSong");
        albumSong.setUploader(user);
        albumSong.setAlbum(album);
        songRepo.save(albumSong);

        SingleEntity single = new SingleEntity();
        single.setSingleName("testSingle");
        single.setUploader(user);
        singleRepo.save(single);

        SongEntity singleSong = new SongEntity();
        singleSong.setSongName("testSingleSong");
        singleSong.setUploader(user);
        singleSong.setSingle(single);
        songRepo.save(singleSong);

        QueueEntity queue = new QueueEntity();
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
            SongEntity singleSong = new SongEntity();
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
