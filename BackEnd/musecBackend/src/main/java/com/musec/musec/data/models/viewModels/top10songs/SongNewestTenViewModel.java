package com.musec.musec.data.models.viewModels.top10songs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongNewestTenViewModel {
    private Long id;
    private String songName;
    private SongNewestTenUploaderViewModel uploader;
    private SongNewestTenAlbumViewModel album;
    private SongNewestTenSingleViewModel single;
}
