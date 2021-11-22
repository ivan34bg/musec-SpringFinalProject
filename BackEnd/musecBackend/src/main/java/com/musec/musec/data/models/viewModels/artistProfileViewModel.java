package com.musec.musec.data.models.viewModels;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class artistProfileViewModel extends userProfileViewModel{
    private Set<artistProfileAlbumViewModel> albums;
    private Set<artistProfileSingleViewModel> singles;
}
