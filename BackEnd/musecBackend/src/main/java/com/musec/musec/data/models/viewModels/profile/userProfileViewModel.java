package com.musec.musec.data.models.viewModels.profile;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class userProfileViewModel {
    private String profilePicLink;
    private String fullName;
    private Set<String> roleNames;
    private Set<userProfilePlaylistViewModel> playlists;
}
