package com.musec.musec.data.models.viewModels.genre;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class genreSongViewModel {
    private Long id;
    private String songName;
    private genreUserViewModel uploader;
}
