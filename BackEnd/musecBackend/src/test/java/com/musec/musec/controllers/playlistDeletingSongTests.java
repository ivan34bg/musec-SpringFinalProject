package com.musec.musec.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class playlistDeletingSongTests {

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
    private playlistRepository playlistRepo;

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

        songEntity song = new songEntity();
        song.setSongName("testSong");
        song.setUploader(user);
        song.setAlbum(album);
        songRepo.save(song);

        queueEntity queue = new queueEntity();
        queue.setUser(user);
        queue.setSongs(List.of(song));
        queueRepo.save(queue);

        playlistEntity playlist = new playlistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(false);
        playlist.setOpenToPublicEditsOrNot(false);
        playlist.setSongs(List.of(song));
        playlistRepo.save(playlist);
    }

    @AfterEach
    void destroy(){
        queueRepo.deleteAll();
        playlistRepo.deleteAll();
        songRepo.deleteAll();
        albumRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    @WithMockUser("admin")
    void testDeleteSongFromOthersPrivateUneditablePlaylist() throws Exception {
        mockMvc.perform(delete("/playlist/song/1").content("1")).andExpect(status().is(403));
    }

    @Test
    @WithMockUser("admin")
    void testDeleteSongFromOthersPublicUneditablePlaylist() throws Exception {
        playlistEntity playlist = playlistRepo.findById(1L).get();
        playlist.setPublic(true);
        playlistRepo.save(playlist);

        mockMvc.perform(delete("/playlist/song/1").content("1")).andExpect(status().is(403));
    }

    @Test
    @WithMockUser("admin")
    void testDeleteSongFromOthersPrivateEditablePlaylist() throws Exception {
        playlistEntity playlist = playlistRepo.findById(1L).get();
        playlist.setOpenToPublicEditsOrNot(true);
        playlistRepo.save(playlist);

        mockMvc.perform(delete("/playlist/song/1").content("1")).andExpect(status().is(403));
    }

    @Test
    @WithMockUser("admin")
    void testDeleteSongFromOthersPublicEditablePlaylist() throws Exception {
        playlistEntity playlist = playlistRepo.findById(1L).get();
        playlist.setPublic(true);
        playlist.setOpenToPublicEditsOrNot(true);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.songs[0]", Matchers.hasEntry("songName", "testSong")));
        mockMvc.perform(delete("/playlist/song/1").content("1")).andExpect(status().isOk());
        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testDeleteSongFromSelfPrivateUneditablePlaylist() throws Exception {
        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.songs[0]", Matchers.hasEntry("songName", "testSong")));
        mockMvc.perform(delete("/playlist/song/1").content("1")).andExpect(status().isOk());
        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testDeleteSongFromSelfPublicUneditablePlaylist() throws Exception {
        playlistEntity playlist = playlistRepo.findById(1L).get();
        playlist.setPublic(true);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.songs[0]", Matchers.hasEntry("songName", "testSong")));
        mockMvc.perform(delete("/playlist/song/1").content("1")).andExpect(status().isOk());
        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testDeleteSongFromSelfPrivateEditablePlaylist() throws Exception {
        playlistEntity playlist = playlistRepo.findById(1L).get();
        playlist.setOpenToPublicEditsOrNot(true);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.songs[0]", Matchers.hasEntry("songName", "testSong")));
        mockMvc.perform(delete("/playlist/song/1").content("1")).andExpect(status().isOk());
        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testDeleteSongFromSelfPublicEditablePlaylist() throws Exception {
        playlistEntity playlist = playlistRepo.findById(1L).get();
        playlist.setOpenToPublicEditsOrNot(true);
        playlist.setPublic(true);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.songs[0]", Matchers.hasEntry("songName", "testSong")));
        mockMvc.perform(delete("/playlist/song/1").content("1")).andExpect(status().isOk());
        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testDeleteNonExistentSongFromPlaylist() throws Exception {
        mockMvc.perform(delete("/playlist/song/1").content("2")).andExpect(status().is(404));
    }
}
