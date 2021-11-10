package com.musec.musec;

import com.musec.musec.entities.enums.genreEnum;
import com.musec.musec.entities.genreEntity;
import com.musec.musec.repositories.genreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataInit implements CommandLineRunner {
    private final genreRepository genreRepo;

    public DataInit(genreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        genreInit();
    }

    private void genreInit(){
        if(genreRepo.count() == 0){
            for (genreEnum genre:genreEnum.values()
                 ) {
                genreEntity genreToSave = new genreEntity(
                        genre.name(),
                        new Color(
                                ThreadLocalRandom.current().nextInt(0,256),
                                ThreadLocalRandom.current().nextInt(0,256),
                                ThreadLocalRandom.current().nextInt(0,256)
                        ));
                genreRepo.save(genreToSave);
            }
        }
    }
}
