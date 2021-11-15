package com.musec.musec.services;

import com.musec.musec.data.genreEntity;
import javassist.NotFoundException;

public interface genreService {
    genreEntity findGenreByName(String genreName) throws NotFoundException;
}
