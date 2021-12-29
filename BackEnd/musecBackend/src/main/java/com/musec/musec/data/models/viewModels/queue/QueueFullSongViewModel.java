package com.musec.musec.data.models.viewModels.queue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueueFullSongViewModel {
    private Long id;
    private String songName;
    private QueueFullSongUploaderViewModel uploader;
    private QueueFullSongAlbumViewModel album;
    private QueueFullSongSingleViewModel single;
}
