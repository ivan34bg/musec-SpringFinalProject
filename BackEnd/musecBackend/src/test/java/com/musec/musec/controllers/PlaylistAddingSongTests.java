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
public class PlaylistAddingSongTests {
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
    private PlaylistRepository playlistRepo;

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
        playlistRepo.deleteAll();
        songRepo.deleteAll();
        albumRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    @WithMockUser("admin")
    void addSongToOthersPrivatePlaylist() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(false);
        playlist.setOpenToPublicEditsOrNot(false);
        playlistRepo.save(playlist);

        mockMvc.perform(post("/playlist/song/1").content("1")).andExpect(status().is(403));
    }

    @Test
    @WithMockUser("admin")
    void addSongToOthersPublicUneditablePlaylist() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(true);
        playlist.setOpenToPublicEditsOrNot(false);
        playlistRepo.save(playlist);

        mockMvc.perform(post("/playlist/song/1").content("1")).andExpect(status().is(403));
    }

    @Test
    @WithMockUser("admin")
    void addSongToOthersPrivateEditablePlaylist() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(false);
        playlist.setOpenToPublicEditsOrNot(true);
        playlistRepo.save(playlist);

        mockMvc.perform(post("/playlist/song/1").content("1")).andExpect(status().is(403));
    }

    @Test
    @WithMockUser("admin")
    void addSongToOthersPublicEditablePlaylist() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(true);
        playlist.setOpenToPublicEditsOrNot(true);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));

        mockMvc.perform(post("/playlist/song/1").content("1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)));
    }

    @Test
    @WithMockUser("test")
    void addSongToSelfPrivateUneditablePlaylist() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(false);
        playlist.setOpenToPublicEditsOrNot(false);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));

        mockMvc.perform(post("/playlist/song/1").content("1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)));
    }

    @Test
    @WithMockUser("test")
    void addSongToSelfPublicUneditablePlaylist() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(true);
        playlist.setOpenToPublicEditsOrNot(false);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));

        mockMvc.perform(post("/playlist/song/1").content("1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)));
    }

    @Test
    @WithMockUser("test")
    void addSongToSelfPrivateEditablePlaylist() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(false);
        playlist.setOpenToPublicEditsOrNot(true);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));

        mockMvc.perform(post("/playlist/song/1").content("1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)));
    }

    @Test
    @WithMockUser("test")
    void addSongToSelfPublicEditablePlaylist() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(true);
        playlist.setOpenToPublicEditsOrNot(true);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(0)));

        mockMvc.perform(post("/playlist/song/1").content("1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", Matchers.hasSize(1)));
    }

    @Test
    @WithMockUser("test")
    void addSongToPlaylistThatAlreadyHasIt() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(true);
        playlist.setOpenToPublicEditsOrNot(true);
        playlist.setSongs(List.of(songRepo.findAll().get(0)));
        playlistRepo.save(playlist);

        mockMvc.perform(post("/playlist/song/1").content(songRepo.findAll().get(0).getId().toString()))
                .andExpect(status().is(400));
    }
}
