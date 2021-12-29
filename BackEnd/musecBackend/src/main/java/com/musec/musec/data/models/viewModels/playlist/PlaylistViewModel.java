package com.musec.musec.data.models.viewModels.playlist;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistViewModel {
    private String playlistName;
    private boolean canEdit;
    private PlaylistCreatorViewModel playlistCreator;
    private List<PlaylistSongViewModel> songs;
}
