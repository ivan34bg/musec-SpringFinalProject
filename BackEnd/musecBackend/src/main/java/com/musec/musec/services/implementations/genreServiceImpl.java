package com.musec.musec.services.implementations;

import com.musec.musec.data.genreEntity;
import com.musec.musec.data.models.viewModels.genre.genreExpandedInfoViewModel;
import com.musec.musec.data.models.viewModels.genre.genreShortInfoViewModel;
import com.musec.musec.repositories.genreRepository;
import com.musec.musec.services.genreService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class genreServiceImpl implements genreService {
    private final genreRepository genreRepo;
    private final ModelMapper modelMapper;

    public genreServiceImpl(genreRepository genreRepo, ModelMapper modelMapper) {
        this.genreRepo = genreRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public genreEntity findGenreByName(String genreName) throws NotFoundException {
        Optional<genreEntity> genreEntityOrNull = genreRepo.findByProperName(genreName);
        if(genreEntityOrNull.isPresent()){
            return genreEntityOrNull.get();
        }
        throw new NotFoundException("Genre doesn't exist!");
    }

    @Override
        public List<genreShortInfoViewModel> loadShortAllGenres() {
        List<genreShortInfoViewModel> setToReturn = new ArrayList<>();
        List<genreEntity> genres = genreRepo.findAll();
        for (genreEntity genre:genres
             ) {
            genreShortInfoViewModel mappedGenre = new genreShortInfoViewModel();
            modelMapper.map(genre, mappedGenre);
            mappedGenre.setGenreName(genre.getProperName());
            setToReturn.add(mappedGenre);
        }
        return setToReturn;
    }

    @Override
    public List<genreShortInfoViewModel> loadNineGenres() {
        List<genreShortInfoViewModel> setToReturn = new ArrayList<>(loadShortAllGenres());
        for (int i = setToReturn.size() - 1; i > 8; i--){
            setToReturn.remove(i);
        }
        return setToReturn;
    }

    @Override
    public genreExpandedInfoViewModel loadSongsByGenreId(Long genreId) throws NotFoundException {
        if(genreId <= genreRepo.count()){
            genreExpandedInfoViewModel genreToReturn = new genreExpandedInfoViewModel();
            modelMapper.map(genreRepo.findById(genreId).get(), genreToReturn);
            return genreToReturn;
        }
        throw new NotFoundException("Genre cannot be found");
    }
}
