package com.musec.musec.data.models.viewModels.top10songs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class songTopTenViewModel {
    private Long id;
    private String songName;
    private songTopTenUploaderViewModel uploader;
    private songTopTenAlbumViewModel album;
    private songTopTenSingleViewModel single;
}
