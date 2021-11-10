package com.musec.musec.services.implementations;

import com.musec.musec.entities.genreEntity;
import com.musec.musec.repositories.genreRepository;
import com.musec.musec.services.genreService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class genreServiceImpl implements genreService {
    private final genreRepository genreRepo;

    public genreServiceImpl(genreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    @Override
    public genreEntity findGenreByName(String genreName) throws NotFoundException {
        Optional<genreEntity> genreEntityOrNull = genreRepo.findByName(genreName);
        if(genreEntityOrNull.isPresent()){
            return genreEntityOrNull.get();
        }
        throw new NotFoundException("Genre doesn't exist!");
    }
}
