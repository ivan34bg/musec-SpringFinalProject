package com.musec.musec.data.models.viewModels;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class playlistViewModel {
    private String playlistName;
    private String creatorFullName;
    private Set<songViewModel> songs;
}
