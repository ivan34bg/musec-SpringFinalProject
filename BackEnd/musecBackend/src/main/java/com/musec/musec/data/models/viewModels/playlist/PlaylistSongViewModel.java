package com.musec.musec.data.models.viewModels.playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistSongViewModel {
    private Long id;
    private String songName;
    private PlaylistSongArtistViewModel uploader;
    private PlaylistSongAlbumViewModel album;
    private PlaylistSongSingleViewModel single;
}
