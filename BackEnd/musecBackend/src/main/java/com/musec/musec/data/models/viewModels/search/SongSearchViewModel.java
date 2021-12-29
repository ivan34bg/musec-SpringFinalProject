package com.musec.musec.data.models.viewModels.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongSearchViewModel {
    private Long id;
    private String songName;
    private Long albumId;
    private String albumName;
    private Long singleId;
    private String singleName;
}
