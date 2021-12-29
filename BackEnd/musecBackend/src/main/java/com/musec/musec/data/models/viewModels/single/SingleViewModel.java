package com.musec.musec.data.models.viewModels.single;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleViewModel {
    private String singleName;
    private String singlePicLocation;
    private SingleSongViewModel song;
    private SingleArtistViewModel uploader;
}
