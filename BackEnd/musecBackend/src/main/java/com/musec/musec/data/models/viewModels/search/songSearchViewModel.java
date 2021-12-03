package com.musec.musec.data.models.viewModels.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class songSearchViewModel {
    private Long id;
    private String songName;
    private Long albumId;
    private Long singleId;
}
