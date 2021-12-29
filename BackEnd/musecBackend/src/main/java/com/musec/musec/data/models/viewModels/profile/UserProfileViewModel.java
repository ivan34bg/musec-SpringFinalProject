package com.musec.musec.data.models.viewModels.profile;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileViewModel {
    private String profilePicLink;
    private String fullName;
    private List<String> roleNames;
    private List<UserProfilePlaylistViewModel> playlists;
}
