package com.musec.musec.data.models.viewModels.album;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlbumViewModel {
    private String albumName;
    private String albumPicLocation;
    private List<AlbumSongViewModel> songs;
    private AlbumUploaderViewModel uploader;
}
