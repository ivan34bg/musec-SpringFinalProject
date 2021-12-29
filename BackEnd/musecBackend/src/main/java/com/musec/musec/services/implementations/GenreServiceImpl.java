package com.musec.musec.services.implementations;

import com.musec.musec.data.GenreEntity;
import com.musec.musec.data.models.viewModels.genre.GenreExpandedInfoViewModel;
import com.musec.musec.data.models.viewModels.genre.GenreShortInfoViewModel;
import com.musec.musec.repositories.GenreRepository;
import com.musec.musec.services.GenreService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepo;
    private final ModelMapper modelMapper;

    public GenreServiceImpl(GenreRepository genreRepo, ModelMapper modelMapper) {
        this.genreRepo = genreRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenreEntity findGenreByName(String genreName) throws NotFoundException {
        Optional<GenreEntity> genreEntityOrNull = genreRepo.findByProperName(genreName);
        if(genreEntityOrNull.isPresent()){
            return genreEntityOrNull.get();
        }
        throw new NotFoundException("Genre doesn't exist!");
    }

    @Override
        public List<GenreShortInfoViewModel> loadShortAllGenres() {
        List<GenreShortInfoViewModel> setToReturn = new ArrayList<>();
        List<GenreEntity> genres = genreRepo.findAll();
        for (GenreEntity genre:genres
             ) {
            GenreShortInfoViewModel mappedGenre = new GenreShortInfoViewModel();
            modelMapper.map(genre, mappedGenre);
            mappedGenre.setGenreName(genre.getProperName());
            setToReturn.add(mappedGenre);
        }
        return setToReturn;
    }

    @Override
    public List<GenreShortInfoViewModel> loadNineGenres() {
        List<GenreShortInfoViewModel> setToReturn = new ArrayList<>(loadShortAllGenres());
        for (int i = setToReturn.size() - 1; i > 8; i--){
            setToReturn.remove(i);
        }
        return setToReturn;
    }

    @Override
    public GenreExpandedInfoViewModel loadSongsByGenreId(Long genreId) throws NotFoundException {
        if(genreId <= genreRepo.count()){
            GenreExpandedInfoViewModel genreToReturn = new GenreExpandedInfoViewModel();
            modelMapper.map(genreRepo.findById(genreId).get(), genreToReturn);
            return genreToReturn;
        }
        throw new NotFoundException("Genre cannot be found");
    }
}
