package com.musec.musec.data.models.viewModels.genre;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenreExpandedInfoViewModel {
    private String genreName;
    private List<GenreSongViewModel> songs;
}
