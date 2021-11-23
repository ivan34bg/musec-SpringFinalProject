package com.musec.musec.data.models.viewModels.playlist;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class playlistViewModel {
    private String playlistName;
    private playlistCreatorViewModel playlistCreator;
    private Set<playlistSongViewModel> songs;
}
