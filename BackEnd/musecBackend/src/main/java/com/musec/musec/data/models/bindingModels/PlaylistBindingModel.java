package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PlaylistBindingModel {
    @Length(min = 2, max = 20)
    @NotBlank
    private String playlistName;
    @NotBlank
    private String isPublic;
    @NotBlank
    private String openToPublicEditsOrNot;
}
