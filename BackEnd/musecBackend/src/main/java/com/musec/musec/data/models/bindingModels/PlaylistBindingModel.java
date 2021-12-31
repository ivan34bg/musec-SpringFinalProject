package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PlaylistBindingModel {
    @Length(min = 2, max = 20)
    @NotBlank
    private String playlistName;
    @NotNull
    private String isPublic;
    @NotNull
    private String openToPublicEditsOrNot;
}
