package com.musec.musec.services.implementations;

import com.musec.musec.data.enums.GenreEnum;
import com.musec.musec.data.GenreEntity;
import com.musec.musec.data.models.viewModels.genre.GenreShortInfoViewModel;
import com.musec.musec.data.SongEntity;
import com.musec.musec.repositories.GenreRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceImplTest {

    private List<GenreEntity> genres;
    private SongEntity song;
    private GenreServiceImpl genreService;
    private ModelMapper modelMapper;

    @Mock
    private GenreRepository genreRepositoryMock;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        genreService = new GenreServiceImpl(genreRepositoryMock, modelMapper);
        genres = new ArrayList<>();

        for (GenreEnum genre :
                GenreEnum.values()) {
            song = new SongEntity();
            song.setSongName("test" + genre.getProperName());

            GenreEntity newGenre = new GenreEntity();
            newGenre.setGenreName(genre);
            newGenre.setProperName(genre.getProperName());
            newGenre.setSongs(List.of(song));
            genres.add(newGenre);
        }
    }

    @Test
    void testNotFoundGenreByName(){
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    genreService.findGenreByName("non-existent genre");
                }
                );
    }

    @Test
    void testGenreFoundByName() throws NotFoundException {
        Mockito.when(genreRepositoryMock.findByProperName(genres.get(0).getProperName()))
                .thenReturn(Optional.of(genres.get(0)));

        var result = genreService.findGenreByName(genres.get(0).getProperName());

        Assertions.assertEquals(genres.get(0).getGenreName(), result.getGenreName());
        Assertions.assertEquals(genres.get(0).getProperName(), result.getProperName());
        Assertions.assertEquals(genres.get(0).getSongs(), result.getSongs());
    }

    @Test
    void testShortOfAllGenres(){
        Mockito.when(genreRepositoryMock.findAll())
                .thenReturn(genres);

        List<GenreShortInfoViewModel> result = genreService.loadShortAllGenres();

        Assertions.assertEquals(result.size(), genres.size());
        for (GenreEntity genre :
                genres) {
            Assertions.assertTrue(result.stream().anyMatch(g -> g.getGenreName().equals(genre.getProperName())));
        }
    }

    @Test
    void testLoadNineGenres(){
        Mockito.when(genreRepositoryMock.findAll())
                .thenReturn(genres);

        List<GenreShortInfoViewModel> result = genreService.loadNineGenres();

        Assertions.assertEquals(9, result.size());
        for (GenreShortInfoViewModel genre :
                result) {
            Assertions.assertTrue(genres.stream().anyMatch(g -> g.getProperName().equals(genre.getGenreName())));
        }
    }

    @Test
    void testNotFoundLoadSongsByGenreId(){
        Assertions.assertThrows(NotFoundException.class, () -> {
            genreService.loadSongsByGenreId(1L);
        });
    }
}
