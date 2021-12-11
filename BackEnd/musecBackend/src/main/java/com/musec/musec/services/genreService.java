package com.musec.musec.services;

import com.musec.musec.data.genreEntity;
import com.musec.musec.data.models.viewModels.genre.genreExpandedInfoViewModel;
import com.musec.musec.data.models.viewModels.genre.genreShortInfoViewModel;
import javassist.NotFoundException;

import java.util.List;

public interface genreService {
    genreEntity findGenreByName(String genreName) throws NotFoundException;
    List<genreShortInfoViewModel> loadShortAllGenres();
    List<genreShortInfoViewModel> loadNineGenres();
    genreExpandedInfoViewModel loadSongsByGenreId(Long genreId) throws NotFoundException;
}
