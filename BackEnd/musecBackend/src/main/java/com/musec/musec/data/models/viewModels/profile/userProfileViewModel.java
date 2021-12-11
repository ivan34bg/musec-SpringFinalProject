package com.musec.musec.data.models.viewModels.profile;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class userProfileViewModel {
    private String profilePicLink;
    private String fullName;
    private List<String> roleNames;
    private List<userProfilePlaylistViewModel> playlists;
}
