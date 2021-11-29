package com.musec.musec.data.models.viewModels.queue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class queueFullSongViewModel {
    private Long id;
    private String songName;
    private queueFullSongUploaderViewModel uploader;
    private queueFullSongAlbumViewModel album;
    private queueFullSongSingleViewModel single;
}
