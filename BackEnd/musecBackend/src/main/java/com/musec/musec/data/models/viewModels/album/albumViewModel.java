package com.musec.musec.data.models.viewModels.album;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class albumViewModel {
    private String albumName;
    private String albumPicLocation;
    private Set<albumSongViewModel> songs;
    private albumUploaderViewModel uploader;
}