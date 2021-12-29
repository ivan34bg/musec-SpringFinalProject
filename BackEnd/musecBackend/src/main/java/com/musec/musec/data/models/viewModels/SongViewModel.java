package com.musec.musec.data.models.viewModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongViewModel {
    private String songName;
    private String songLocation;
    private GenreViewModel songGenre;
    private String albumOrSingleName;
}
