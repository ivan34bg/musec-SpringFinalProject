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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlaylistControllerTests {
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

        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(user);
        playlist.setPublic(false);
        playlist.setOpenToPublicEditsOrNot(false);
        playlistRepo.save(playlist);

        QueueEntity queue = new QueueEntity();
        queue.setUser(user);
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
    @WithMockUser("test")
    void testCreatePlaylist() throws Exception {
        MultiValueMap<String, String> playlist = new LinkedMultiValueMap<>();
        playlist.add("playlistName", "testPlaylist2");
        playlist.add("isPublic", "true");
        playlist.add("openToPublicEditsOrNot", "false");

        mockMvc.perform(post("/playlist/create").params(playlist))
                .andExpect(status().isOk());
        mockMvc.perform(get("/playlist/search").queryParam("param", "testPlaylist2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("playlistName", "testPlaylist2")));
    }

    @Test
    @WithMockUser("test")
    void testGetInvalidPlaylistById() throws Exception {
        mockMvc.perform(get("/playlist/3")).andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testGetOwnValidPlaylistById() throws Exception {
        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasEntry("playlistName", "testPlaylist")));
    }

    @Test
    @WithMockUser("admin")
    void testGetOthersPrivatePlaylistById() throws Exception {
        mockMvc.perform(get("/playlist/1")).andExpect(status().is(404));
    }

    @Test
    @WithMockUser("admin")
    void testGetOthersPublicPlaylistById() throws Exception {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setPlaylistName("testPlaylist");
        playlist.setPlaylistCreator(userRepo.findByUsername("test").get());
        playlist.setPublic(true);
        playlist.setOpenToPublicEditsOrNot(false);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasEntry("playlistName", "testPlaylist")));
    }

    @Test
    @WithMockUser("test")
    void testDeleteNonExistentPlaylist() throws Exception{
        mockMvc.perform(delete("/playlist/3")).andExpect(status().is(404));
    }

    @Test
    @WithMockUser("admin")
    void testDeletePlaylistOfOtherUser() throws Exception{
        mockMvc.perform(delete("/playlist/1")).andExpect(status().is(403));
    }

    @Test
    @WithMockUser("test")
    void testDeleteValidPlaylist() throws Exception{
        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasEntry("playlistName", "testPlaylist")));
        mockMvc.perform(delete("/playlist/1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/playlist/1"))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testAddNonExistentSongToPlaylist() throws Exception {
        mockMvc.perform(post("/playlist/song/1").content("9")).andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testAddSongToNonExistentPlaylist() throws Exception {
        mockMvc.perform(post("/playlist/song/3").content("1")).andExpect(status().is(404));
    }

    @Test
    @WithMockUser("admin")
    void testCheckIfUserHasPlaylistsWithoutPlaylists() throws Exception {
        mockMvc.perform(get("/playlist/check")).andExpect(status().is(404));
    }

    @Test
    @WithMockUser("test")
    void testCheckIfUserHasPlaylists() throws Exception {
        mockMvc.perform(get("/playlist/check")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser("test")
    void testReturnShortPlaylists() throws Exception {
        mockMvc.perform(get("/playlist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("playlistName", "testPlaylist")));
    }

    @Test
    @WithMockUser("test")
    void testSearchPlaylistWithNoMatches() throws Exception {
        mockMvc.perform(get("/playlist/search").queryParam("param", "invalidPlaylist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testSearchPlaylistWithPrivateMatches() throws Exception {
        mockMvc.perform(get("/playlist/search").queryParam("param", "testPlaylist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser("test")
    void testSearchPlaylistWithMatches() throws Exception {
        PlaylistEntity playlist = playlistRepo.findById(1L).get();
        playlist.setPublic(true);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/playlist/search").queryParam("param", "testPlaylist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("playlistName", "testPlaylist")));
    }

    @Test
    @WithMockUser("test")
    void testAddPlaylistToQueue() throws Exception {
        List<SongEntity> songs = List.of(songRepo.getById(1L));
        PlaylistEntity playlist = playlistRepo.findById(1L).get();
        playlist.setSongs(songs);
        playlistRepo.save(playlist);

        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
        mockMvc.perform(post("/playlist/1/queue")).andExpect(status().isOk());
        mockMvc.perform(get("/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0]", Matchers.hasEntry("songName", "testSong")));
    }
}
