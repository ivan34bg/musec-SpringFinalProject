package com.musec.musec.services;

import com.musec.musec.entities.genreEntity;
import javassist.NotFoundException;

public interface genreService {
    genreEntity findGenreByName(String genreName) throws NotFoundException;
}
