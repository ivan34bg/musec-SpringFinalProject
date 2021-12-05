package com.musec.musec.data.models.viewModels.genre;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class genreExpandedInfoViewModel {
    private String genreName;
    private Set<genreSongViewModel> songs;
}
