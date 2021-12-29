package com.musec.musec.data.models.viewModels.profile;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArtistProfileViewModel extends UserProfileViewModel {
    private List<ArtistProfileAlbumViewModel> albums;
    private List<ArtistProfileSingleViewModel> singles;
}
