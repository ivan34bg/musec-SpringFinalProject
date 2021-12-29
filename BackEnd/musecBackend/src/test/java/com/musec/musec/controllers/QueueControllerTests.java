package com.musec.musec.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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
public class QueueControllerTests {

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

        SongEntity song = new SongEntity();
        song.setSongName("testSong");
        song.setUploader(user);
        song.setAlbum(album);
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
        albumRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    @WithMockUser("test")
    void testReturnQueueOfLoggedUserWithSong() throws Exception {
        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    @WithMockUser("test")
    void testEmptyQueue() throws Exception {
        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
        mockMvc.perform(delete("/queue")).andExpect(status().isOk());
        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testFullInfoOfQueue() throws Exception {
        mockMvc.perform(get("/queue/song"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].album",Matchers.hasEntry("albumName", "testAlbum")));
    }

    @Test
    @WithMockUser("test")
    void testAddSongToQueueInvalidSong() throws Exception {
        mockMvc.perform(post("/queue/song/2"))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testAddSongToQueueThatIsAlreadyInQueue() throws Exception {
        mockMvc.perform(post("/queue/song/1"))
                .andExpect(status().is(400));
    }

    @Test
    @WithMockUser("test")
    void testAddValidSongToQueue() throws Exception{
        AlbumEntity album = new AlbumEntity();
        album.setAlbumName("testAlbum2");
        album.setUploader(userRepo.findByUsername("test").get());
        albumRepo.save(album);

        SongEntity song = new SongEntity();
        song.setSongName("testSong2");
        song.setUploader(userRepo.findByUsername("test").get());
        song.setAlbum(album);
        songRepo.save(song);

        mockMvc.perform(post("/queue/song/2"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    @WithMockUser("test")
    void testDeleteNonExistentSongOfQueue() throws Exception {
        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
        mockMvc.perform(delete("/queue/song/2")).andExpect(status().is(404));
        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    @WithMockUser("test")
    void testDeleteValidSongOfQueue() throws Exception {
        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
        mockMvc.perform(delete("/queue/song/1")).andExpect(status().isOk());
        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

}
