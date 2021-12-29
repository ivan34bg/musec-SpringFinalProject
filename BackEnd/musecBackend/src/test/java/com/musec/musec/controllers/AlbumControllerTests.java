package com.musec.musec.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.musec.musec.data.AlbumEntity;
import com.musec.musec.data.QueueEntity;
import com.musec.musec.data.SongEntity;
import com.musec.musec.data.UserEntity;
import com.musec.musec.repositories.AlbumRepository;
import com.musec.musec.repositories.QueueRepository;
import com.musec.musec.repositories.SongRepository;
import com.musec.musec.repositories.UserRepository;
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
public class AlbumControllerTests {

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
    void testReturnNonExistentAlbum() throws Exception {
        mockMvc.perform(get("/album/2"))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testReturnValidAlbum() throws Exception {
        mockMvc.perform(get("/album/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasEntry("albumName", "testAlbum")))
                .andExpect(jsonPath("$.songs[0]", Matchers.hasEntry("songName", "testSong")));
    }

    @Test
    @WithMockUser("test")
    void testReturnShortAlbumsOfLoggedUser() throws Exception {
        mockMvc.perform(get("/album/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("albumName", "testAlbum")));
    }

    @Test
    @WithMockUser("test")
    void testReturnShortAlbumsOfInvalidUser() throws Exception {
        mockMvc.perform(get("/album/user/3")).andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testReturnShortAlbumsOfValidUserWithNoAlbums() throws Exception {
        mockMvc.perform(get("/album/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testReturnShortAlbumsOfValidUserWithOneAlbum() throws Exception {
        mockMvc.perform(get("/album/user/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("albumName", "testAlbum")));
    }

    @Test
    @WithMockUser("test")
    void testSearchNonExistentAlbum() throws Exception {
        mockMvc.perform(get("/album/search")
                .queryParam("param", "invalidAlbum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testSearchExistingAlbum() throws Exception {
        mockMvc.perform(get("/album/search")
                .queryParam("param", "testAlbum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("albumName", "testAlbum")));
    }
}
