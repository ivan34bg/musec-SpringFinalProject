package com.musec.musec.data.models.viewModels.top10songs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class songNewestTenViewModel {
    private Long id;
    private String songName;
    private songNewestTenUploaderViewModel uploader;
    private songNewestTenAlbumViewModel album;
    private songNewestTenSingleViewModel single;
}
