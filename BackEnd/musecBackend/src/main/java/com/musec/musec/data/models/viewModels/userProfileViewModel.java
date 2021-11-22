package com.musec.musec.data.models.viewModels;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class userProfileViewModel {
    private String profilePicLink;
    private String username;
    private Set<String> roleNames;
    private Set<userProfilePlaylistViewModel> playlists;
}
