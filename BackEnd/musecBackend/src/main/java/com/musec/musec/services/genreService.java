package com.musec.musec.services;

import com.musec.musec.data.genreEntity;
import com.musec.musec.data.models.viewModels.genre.genreExpandedInfoViewModel;
import com.musec.musec.data.models.viewModels.genre.genreShortInfoViewModel;
import javassist.NotFoundException;

import java.util.List;
import java.util.Set;

public interface genreService {
    genreEntity findGenreByName(String genreName) throws NotFoundException;
    Set<genreShortInfoViewModel> loadShortAllGenres();
    Set<genreShortInfoViewModel> loadNineGenres();
    genreExpandedInfoViewModel loadSongsByGenreId(Long genreId) throws NotFoundException;
}
