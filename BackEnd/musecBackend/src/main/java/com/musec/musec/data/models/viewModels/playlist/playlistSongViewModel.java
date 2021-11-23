package com.musec.musec.data.models.viewModels.playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class playlistSongViewModel {
    private String songName;
    private playlistSongArtistViewModel uploader;
    private playlistSongAlbumViewModel album;
    private playlistSongSingleViewModel single;
}
