package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class playlistBindingModel {
    private String playlistName;
    private boolean isPrivate;
    private boolean openToPublicEditsOrNot;
}
