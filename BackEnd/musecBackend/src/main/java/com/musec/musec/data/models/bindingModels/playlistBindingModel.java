package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class playlistBindingModel {
    private String playlistName;
    private String isPublic;
    private String openToPublicEditsOrNot;
}
