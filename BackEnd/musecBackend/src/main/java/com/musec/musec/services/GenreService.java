package com.musec.musec.services;

import com.musec.musec.data.GenreEntity;
import com.musec.musec.data.models.viewModels.genre.GenreExpandedInfoViewModel;
import com.musec.musec.data.models.viewModels.genre.GenreShortInfoViewModel;
import javassist.NotFoundException;

import java.util.List;

public interface GenreService {
    GenreEntity findGenreByName(String genreName) throws NotFoundException;
    List<GenreShortInfoViewModel> loadShortAllGenres();
    List<GenreShortInfoViewModel> loadNineGenres();
    GenreExpandedInfoViewModel loadSongsByGenreId(Long genreId) throws NotFoundException;
}
