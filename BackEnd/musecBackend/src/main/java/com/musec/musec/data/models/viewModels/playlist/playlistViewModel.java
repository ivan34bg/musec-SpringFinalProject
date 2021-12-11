package com.musec.musec.data.models.viewModels.playlist;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class playlistViewModel {
    private String playlistName;
    private boolean canEdit;
    private playlistCreatorViewModel playlistCreator;
    private List<playlistSongViewModel> songs;
}
