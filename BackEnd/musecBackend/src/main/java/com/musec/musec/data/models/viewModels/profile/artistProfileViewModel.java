package com.musec.musec.data.models.viewModels.profile;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class artistProfileViewModel extends userProfileViewModel{
    private List<artistProfileAlbumViewModel> albums;
    private List<artistProfileSingleViewModel> singles;
}
