package com.musec.musec.data.models.viewModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class songViewModel {
    private String songName; //
    private String songLocation; //
    private genreViewModel songGenre; //?
    private String albumOrSingleName;
}
