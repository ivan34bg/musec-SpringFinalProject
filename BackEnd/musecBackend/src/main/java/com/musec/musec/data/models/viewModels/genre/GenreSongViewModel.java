package com.musec.musec.data.models.viewModels.genre;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreSongViewModel {
    private Long id;
    private String songName;
    private GenreUserViewModel uploader;
}
